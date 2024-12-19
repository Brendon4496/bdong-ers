package com.revature.DTOs;

import com.revature.bdong_ers.Entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserIdDTO {
    private int userId;
    private String username;
    private String firstName;
    private String lastName;
    private int roleId;

    public UserIdDTO(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.roleId = user.getRoleId();
    }
}
