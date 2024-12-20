package com.revature.bdong_ers.Entities;

import java.io.Serializable;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role implements Serializable {

   @Column(name = "roleId")
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int roleId;

   @Column(name = "name")
   private String name;

   @Column(name = "adminPermissions")
   @ColumnDefault("false")
   private boolean admin;
}
