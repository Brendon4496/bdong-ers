package com.revature.bdong_ers.Entities;

import com.revature.bdong_ers.Enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class User {

    @Id @GeneratedValue
    private int userId;

    private @Getter @Setter String username;
    private @Getter @Setter String password;
    private @Getter @Setter String firstName;
    private @Getter @Setter String lastName;
    private @Getter @Setter Role role;

    // Default Role of Employee when filling out other information
    public User(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = Role.EMPLOYEE;
    }

    // Constructor for specifying role
    public User(String username, String password, String firstName, String lastName, Role role) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }
}
