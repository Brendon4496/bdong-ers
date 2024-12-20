package com.revature.bdong_ers.Controllers;

import com.revature.bdong_ers.DTOs.UserLoginDTO;
import com.revature.bdong_ers.DTOs.ReimbursementStatusDTO;
import com.revature.bdong_ers.DTOs.UserIdDTO;
import com.revature.bdong_ers.DTOs.UserResponseDTO;
import com.revature.bdong_ers.DTOs.UserTokenDTO;
import com.revature.bdong_ers.Entities.Reimbursement;
import com.revature.bdong_ers.Entities.User;
import com.revature.bdong_ers.Services.JWTService;
import com.revature.bdong_ers.Services.ReimbursementService;
import com.revature.bdong_ers.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ERSController {

    private ReimbursementService reimbursementService;
    private UserService userService;
    private JWTService jwtService;

    @Autowired
    public ERSController(ReimbursementService reimbursementService, UserService userService, JWTService jwtService) {
        this.reimbursementService = reimbursementService;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping(value="/ping")
    public ResponseEntity<String> ping() {
        System.out.println("Ping pong!");
        return ResponseEntity.ok().body("Pong!");
    }

    @PostMapping(value="/register")
    public ResponseEntity<UserIdDTO> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerAccount(user);
        String token = jwtService.generateToken(registeredUser);
        return ResponseEntity.ok().header("Authorization", token).body(new UserIdDTO(registeredUser));
    }

    @PostMapping(value="/login")
    public ResponseEntity<UserResponseDTO> loginUser(@RequestBody UserLoginDTO userLoginDTO) {

        User validUser = userService.loginAccount(userLoginDTO);
        String token = "";
        UserResponseDTO response = null;
        if (validUser != null) {
            token = jwtService.generateToken(validUser);
            response = new UserResponseDTO(validUser);
        }
        return ResponseEntity.ok().header("Authorization", token).body(response);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserIdDTO>> getUsers(@RequestHeader("Authorization") String token) {

        UserTokenDTO userInfo = jwtService.decodeToken(token);

        // Check if user is not an admin
        if (userInfo.getRoleId() <= 1) {
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

        UserTokenDTO userInfo = jwtService.decodeToken(token);
        
        // Check if user is not an admin AND if user is not obtaining their own account info
        if (userInfo.getRoleId() <= 1 && userInfo.getUserId() != id) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.ok().body(userService.getUser(id));
    }

    @PatchMapping(value="/users/{id}")
    public ResponseEntity<User> patchUser(@RequestHeader("Authorization") String token,
            @PathVariable int id, @RequestBody User user) {

        UserTokenDTO userInfo = jwtService.decodeToken(token);

        // Check if user is not an admin AND if user is not updating their own account
        if (userInfo.getRoleId() <= 1 && userInfo.getUserId() != id) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok().body(userService.updateUser(id, user));
    }

    @DeleteMapping(value="/users/{id}")
    public ResponseEntity<Integer> deleteUser(@RequestHeader("Authorization") String token,
            @PathVariable int id) {
        
        UserTokenDTO userInfo = jwtService.decodeToken(token);
        
        // Check if user is not an admin AND if user is not updating their own account
        if (userInfo.getRoleId() <= 1 && userInfo.getUserId() != id) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok().body(userService.deleteUser(id));
    }

    @PostMapping(value="/reimbursements")
    public ResponseEntity<Reimbursement> postReimbursement(@RequestHeader("Authorization") String token,
            @RequestBody Reimbursement reimbursement) {
        
        UserTokenDTO userInfo = jwtService.decodeToken(token);

        // Check if reimbursement creator does not match token user
        if (userInfo.getUserId() != reimbursement.getUserId()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok().body(reimbursementService.createReimbursement(reimbursement));
    }

    @GetMapping(value="/reimbursements")
    public ResponseEntity<List<Reimbursement>> getReimbursements(@RequestHeader("Authorization") String token,
            @RequestBody User user) {

        UserTokenDTO userInfo = jwtService.decodeToken(token);

        // Check if user is not an admin AND if user is attempting to view another account's information
        if (userInfo.getRoleId() <= 1 && userInfo.getUserId() != user.getUserId()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok().body(reimbursementService.viewReimbursements(user));
    }

    @GetMapping(value="/reimbursements/{status}")
    public ResponseEntity<List<Reimbursement>> getReimbursementsByStatus(@RequestHeader("Authorization") String token,
            @PathVariable String status) {
        
        UserTokenDTO userInfo = jwtService.decodeToken(token);

        // Check if user exists
        if (userInfo.getUserId() > 0) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok().body(reimbursementService.viewReimbursementsByStatus(status));
    }

    @PatchMapping(value="/reimbursements/{id}")
    public ResponseEntity<Reimbursement> patchReimbursementById(@RequestHeader("Authorization") String token,
            @PathVariable int id, @RequestBody Reimbursement reimbursement) {
        
        UserTokenDTO userInfo = jwtService.decodeToken(token);

        // Check if user exists
        if (userInfo.getUserId() > 0) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok().body(reimbursementService.updateReimbursement(id, reimbursement));
    }

    @PatchMapping(value="/reimbursements")
    public ResponseEntity<Reimbursement> patchReimbursementByIdAndStatus(@RequestHeader("Authorization") String token,
            @RequestBody ReimbursementStatusDTO reimbursementStatusDTO) {

        UserTokenDTO userInfo = jwtService.decodeToken(token);

        // Check if user is not an admin. Employees cannot update their own reimbursement statuses.
        if (userInfo.getRoleId() <= 1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok().body(reimbursementService
        .updateReimbursementStatus(reimbursementStatusDTO.getReimbursementId(), reimbursementStatusDTO.getStatus()));
    }
}
