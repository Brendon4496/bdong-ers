package com.revature.bdong_ers.Entities;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Entity
@Table(name = "reimbursements")
public class Reimbursement {

    @Column(name = "reimbursementId")
    @Id @GeneratedValue
    private int reimbursementId;

    @Column(name = "description")
    private @Setter String description;

    @Column(name = "amount")
    private @Setter int amount;

    //TODO: These defaults don't do anything with .save(), figure out alternative
    @Column(name = "status")
    @ColumnDefault("Pending")
    private @Setter String status = "Pending";

    @Column(name = "userId")
    private @Setter int userId;

}
