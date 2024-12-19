package com.revature.bdong_ers.Entities;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements Serializable {

   @Column(name = "roleId")
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int roleId;

   @Column(name = "name")
   private String name;

   @Column(name = "modifyOtherUsers")
   private boolean modifyOtherUsers = false;
}
