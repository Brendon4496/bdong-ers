package com.revature.bdong_ers.Controllers;

import com.revature.DTOs.UserLoginDTO;
import com.revature.DTOs.UserIdDTO;
import com.revature.DTOs.UserResponseDTO;
import com.revature.bdong_ers.Entities.Reimbursement;
import com.revature.bdong_ers.Entities.User;
import com.revature.bdong_ers.Services.ReimbursementService;
import com.revature.bdong_ers.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ERSController {

    private ReimbursementService reimbursementService;
    private UserService userService;

    @Autowired
    public ERSController(ReimbursementService reimbursementService, UserService userService) {
        this.reimbursementService = reimbursementService;
        this.userService = userService;
    }

    @GetMapping(value="/ping")
    public ResponseEntity<String> ping() {
        System.out.println("Ping pong!");
        return ResponseEntity.ok().body("Pong!");
    }

    @PostMapping(value="/register")
    public ResponseEntity<UserIdDTO> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerAccount(user);
        return ResponseEntity.ok().body(new UserIdDTO(registeredUser));
    }

    @PostMapping(value="/login")
    public ResponseEntity<UserResponseDTO> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        User user = userService.loginAccount(userLoginDTO);
        return ResponseEntity.ok().body(new UserResponseDTO(user));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserIdDTO>> getUsers() {
        List<UserIdDTO> users = userService.getAllUsers()
            .stream()
            .map(UserIdDTO::new)
            .toList();
            
        return ResponseEntity.ok().body(users);
    }

    @GetMapping(value="/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id, @RequestBody User user) {
        return ResponseEntity.ok().body(userService.getUser(id));
    }

    //TODO: Permission check
    @PatchMapping(value="/users/{id}")
    public ResponseEntity<User> patchUser(@PathVariable int id, @RequestBody User user) {
        return ResponseEntity.ok().body(userService.updateUser(id, user));
    }

    //TODO: Permission check
    @DeleteMapping(value="/users/{id}")
    public ResponseEntity<Integer> deleteUser(@PathVariable int id) {
        return ResponseEntity.ok().body(userService.deleteUser(id));
    }

    @PostMapping(value="/reimbursements")
    public ResponseEntity<Reimbursement> postReimbursement(@RequestBody Reimbursement reimbursement) {
        return ResponseEntity.ok().body(reimbursementService.createReimbursement(reimbursement));
    }

    // TODO: Session checking, properly check user to determine reimbursements to grab
    @GetMapping(value="/reimbursements")
    public ResponseEntity<List<Reimbursement>> getReimbursements(@RequestBody User user) {
        return ResponseEntity.ok().body(reimbursementService.viewReimbursements(user));
    }

    @GetMapping(value="/reimbursements/{status}")
    public ResponseEntity<List<Reimbursement>> getReimbursementsByStatus(@PathVariable String status) {
        return ResponseEntity.ok().body(reimbursementService.viewReimbursementsByStatus(status));
    }

    @PatchMapping(value="/reimbursements/{id}")
    public ResponseEntity<Reimbursement> patchReimbursementById(@PathVariable int id, @RequestBody Reimbursement reimbursement) {
        return ResponseEntity.ok().body(reimbursementService.updateReimbursement(id, reimbursement));
    }
}
