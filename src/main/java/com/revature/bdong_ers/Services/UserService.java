package com.revature.bdong_ers.Services;

import com.revature.bdong_ers.Entities.User;
import com.revature.bdong_ers.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerAccount(User user) { return null; }
    public User loginAccount(User user) { return null; }
    public User updateUser(User user) { return null; }
    public List<User> getAllUsers() { return null; }
    public User deleteUser(User user) { return null;}
}
