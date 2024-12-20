package com.revature.bdong_ers.Controllers;

import com.revature.bdong_ers.DTOs.UserLoginDTO;
import com.revature.bdong_ers.DTOs.ReimbursementStatusDTO;
import com.revature.bdong_ers.DTOs.UserIdDTO;
import com.revature.bdong_ers.DTOs.UserResponseDTO;
import com.revature.bdong_ers.Entities.Reimbursement;
import com.revature.bdong_ers.Entities.User;
import com.revature.bdong_ers.Services.AuthService;
import com.revature.bdong_ers.Services.ReimbursementService;
import com.revature.bdong_ers.Services.UserService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ERSController {

    private ReimbursementService reimbursementService;
    private UserService userService;
    private AuthService authService;

    @Autowired
    public ERSController(ReimbursementService reimbursementService, UserService userService, AuthService authService) {
        this.reimbursementService = reimbursementService;
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping(value="/ping")
    public ResponseEntity<String> ping() {
        System.out.println("Ping pong!");
        return ResponseEntity.ok().body("Pong!");
    }

    @Transactional
    @PostMapping(value="/register")
    public ResponseEntity<UserIdDTO> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerAccount(user);
        String token = authService.generateToken(registeredUser);
        return ResponseEntity.ok().header("Authorization", token).body(new UserIdDTO(registeredUser));
    }

    @PostMapping(value="/login")
    public ResponseEntity<UserResponseDTO> loginUser(@RequestBody UserLoginDTO userLoginDTO) {

        User validUser = userService.loginAccount(userLoginDTO);
        String token = "";
        UserResponseDTO response = null;
        if (validUser != null) {
            token = authService.generateToken(validUser);
            response = new UserResponseDTO(validUser);
        }
        return ResponseEntity.ok().header("Authorization", token).body(response);
    }
}
