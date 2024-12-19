package com.revature.bdong_ers.Entities;

import org.mindrot.jbcrypt.BCrypt;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "users")
public class User {

    @Column(name = "userId")
    @Id @GeneratedValue
    private int userId;

    @Column(name = "username")
    private @Setter String username;

    // Need to define for hashing
    @Column(name = "password")
    private String password;

    @Column(name = "firstName")
    private @Setter String firstName;

    @Column(name = "lastName")
    private @Setter String lastName;

    @Column(name = "role")
    private @Setter String role;
//    private @Setter Role role;

    public User(String username, String password, String firstName, String lastName, String role) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

}
