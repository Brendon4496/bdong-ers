package com.revature.bdong_ers.Services;

import com.revature.DTOs.UserLoginDTO;
import com.revature.DTOs.UserIdDTO;
import com.revature.bdong_ers.Entities.Reimbursement;
import com.revature.bdong_ers.Entities.User;
import com.revature.bdong_ers.Repositories.UserRepository;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerAccount(User user) {
        // User cannot already exist
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        return userRepository.save(user);
    }

    public User loginAccount(UserLoginDTO userLoginDTO) {
        // User must exist
        User validUser = userRepository.findByUsername(userLoginDTO.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        System.out.println(userLoginDTO.getPassword());
        System.out.println(validUser.getPassword());

        // Passwords must match
        if (!BCrypt.checkpw(userLoginDTO.getPassword(), validUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        return validUser;
    }

    public User updateUser(int id, User user) {

        // User must already exist
        User updatedUser = userRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        // First + Last name fields must be at least one character
        if (user.getFirstName().isEmpty() || user.getLastName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }
    
    public int deleteUser(int id) {

        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return 1;
        }
        return 0;
    }
}
