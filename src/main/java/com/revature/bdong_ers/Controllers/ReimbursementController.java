package com.revature.bdong_ers.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.revature.bdong_ers.DTOs.ReimbursementStatusDTO;
import com.revature.bdong_ers.DTOs.UserIdDTO;
import com.revature.bdong_ers.Entities.Reimbursement;
import com.revature.bdong_ers.Entities.User;
import com.revature.bdong_ers.Services.AuthService;
import com.revature.bdong_ers.Services.ReimbursementService;

@RestController
public class ReimbursementController {
    
    private ReimbursementService reimbursementService;
    private AuthService authService;

    @Autowired
    public ReimbursementController(ReimbursementService reimbursementService, AuthService authService) {
        this.reimbursementService = reimbursementService;
        this.authService = authService;
    }

    @PostMapping(value="/reimbursements")
    public ResponseEntity<Reimbursement> postReimbursement(@RequestHeader("Authorization") String token,
            @RequestBody Reimbursement reimbursement) {
        
        // Check if reimbursement creator does not match token user
        if (!authService.userMatches(token, reimbursement.getUserId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok().body(reimbursementService.createReimbursement(reimbursement));
    }

    @GetMapping(value="/reimbursements")
    public ResponseEntity<List<Reimbursement>> getAllReimbursements(@RequestHeader("Authorization") String token) {

        // Check if user is not an admin
        if (!authService.hasAdminPermissions(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok().body(reimbursementService.viewAllReimbursements());
    }

    @GetMapping(value="/reimbursements/{id}")
    public ResponseEntity<List<Reimbursement>> getReimbursementsByUser(@RequestHeader("Authorization") String token,
            @PathVariable int id) {
        
        // Check if user is not an admin AND if user is not obtaining their own reimbursements
        if (!authService.hasAdminPermissionsOrUserMatches(token, id)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok().body(reimbursementService.viewReimbursementsByUserId(id));
    }

    @GetMapping(value="/reimbursements/status/{status}")
    public ResponseEntity<List<Reimbursement>> getReimbursementsByStatus(@RequestHeader("Authorization") String token,
            @PathVariable String status) {
        
        // Check if user is not an admin
        if (!authService.hasAdminPermissions(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok().body(reimbursementService.viewReimbursementsByStatus(status.toUpperCase()));
    }

    @GetMapping(value="/reimbursements/{id}/status/{status}")
    public ResponseEntity<List<Reimbursement>> getReimbursementsByUserIdAndStatus(@RequestHeader("Authorization") String token,
            @PathVariable int id, @PathVariable String status) {
        
        // Check if user is not an admin AND if user is not obtaining their own reimbursements
        if (!authService.hasAdminPermissionsOrUserMatches(token, id)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok().body(reimbursementService.viewReimbursementsByStatus(id, status.toUpperCase()));
    }

    @PatchMapping(value="/reimbursements/{id}")
    public ResponseEntity<Reimbursement> patchReimbursementById(@RequestHeader("Authorization") String token,
            @PathVariable int id, @RequestBody Reimbursement reimbursement) {
        
        // Check if user exists
        if (!authService.userExists(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // If user isn't an admin, force keep previous status
        if (!authService.hasAdminPermissions(token)) {
            String previousStatus = reimbursementService.viewReimbursement(reimbursement.getReimbursementId()).getStatus();
            reimbursement.setStatus(previousStatus);
        }
        return ResponseEntity.ok().body(reimbursementService.updateReimbursement(id, reimbursement));
    }

    @PatchMapping(value="/reimbursements")
    public ResponseEntity<Reimbursement> patchReimbursementByIdAndStatus(@RequestHeader("Authorization") String token,
            @RequestBody ReimbursementStatusDTO reimbursementStatusDTO) {

        // Check if user is not an admin. Employees cannot update their own reimbursement statuses.
        if (!authService.hasAdminPermissions(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok().body(reimbursementService
        .updateReimbursementStatus(reimbursementStatusDTO.getReimbursementId(), reimbursementStatusDTO.getStatus()));
    }

}