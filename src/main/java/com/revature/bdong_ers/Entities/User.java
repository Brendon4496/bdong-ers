package com.revature.bdong_ers.Entities;

import org.mindrot.jbcrypt.BCrypt;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Column(name = "userId")
    @Id @GeneratedValue
    private int userId;

    @Column(name = "username", unique = true)
    private @Setter String username;

    // Need to define setter for hashing
    @Column(name = "password")
    private String password;

    @Column(name = "firstName")
    private @Setter String firstName;

    @Column(name = "lastName")
    private @Setter String lastName;

    @JoinColumn(table = "roles")
    private @Setter Integer roleId;

    public User(String username, String password, String firstName, String lastName, int roleId) {
        this.username = username;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleId = roleId;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

}
