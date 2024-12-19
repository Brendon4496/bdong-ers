package com.revature.bdong_ers.Entities;

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

    @Column(name = "status")
    private @Setter String status;

    @Column(name = "userId")
    private @Setter int userId;

}
