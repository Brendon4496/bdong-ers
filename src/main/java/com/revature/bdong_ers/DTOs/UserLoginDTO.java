package com.revature.bdong_ers.DTOs;

import com.revature.bdong_ers.Entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    private String username;
    private String password;

    public UserLoginDTO(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
}
