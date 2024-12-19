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
public class UserResponseDTO {

    private int userId;

    public UserResponseDTO(User user) {
        this.userId = user.getUserId();
    }
}
