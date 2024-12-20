package com.revature.bdong_ers.Entities;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
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
    @ColumnDefault("PENDING")
    private @Setter String status = "PENDING";

    @Column(name = "userId")
    private @Setter int userId;

}
