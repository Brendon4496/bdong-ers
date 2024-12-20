package com.revature.bdong_ers.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.revature.bdong_ers.DTOs.UserIdDTO;
import com.revature.bdong_ers.Entities.User;
import com.revature.bdong_ers.Services.AuthService;
import com.revature.bdong_ers.Services.ReimbursementService;
import com.revature.bdong_ers.Services.UserService;

import jakarta.transaction.Transactional;

@RestController
public class UserController {

    private UserService userService;
    private ReimbursementService reimbursementService;
    private AuthService authService;

    @Autowired
    public UserController(UserService userService, ReimbursementService reimbursementService, AuthService authService) {
        this.userService = userService;
        this.reimbursementService = reimbursementService;
        this.authService = authService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserIdDTO>> getUsers(@RequestHeader("Authorization") String token) {

        // Check if user is not an admin
        if (!authService.hasAdminPermissions(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        List<UserIdDTO> users = userService.getAllUsers()
            .stream()
            .map(UserIdDTO::new)
            .toList();
            
        return ResponseEntity.ok().body(users);
    }

    @GetMapping(value="/users/{id}")
    public ResponseEntity<User> getUser(@RequestHeader("Authorization") String token,
            @PathVariable int id) {

        // Check if user is not an admin AND if user is not obtaining their own account info
        if (!authService.hasAdminPermissionsOrUserMatches(token, id)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.ok().body(userService.getUser(id));
    }

    @Transactional
    @PatchMapping(value="/users/{id}")
    public ResponseEntity<User> patchUser(@RequestHeader("Authorization") String token,
            @PathVariable int id, @RequestBody User user) {

        // Check if user is not an admin AND if user is not updating their own account
        if (!authService.hasAdminPermissionsOrUserMatches(token, id)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok().body(userService.updateUser(id, user));
    }

    @Transactional
    @DeleteMapping(value="/users/{id}")
    public ResponseEntity<Integer> deleteUser(@RequestHeader("Authorization") String token,
            @PathVariable int id) {
         
        // Check if user is not an admin
        if (!authService.hasAdminPermissions(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        int rowsDeleted = reimbursementService.deleteByUserId(id); 
        userService.deleteUser(id);
        return ResponseEntity.ok().body(rowsDeleted);
    }
}