package com.revature.bdong_ers.Entities;

import com.revature.bdong_ers.Enums.ReimbursementStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

//@Entity
public class Reimbursement {

//    @Id @GeneratedValue
    private int reimbursementId;

    private @Getter @Setter String description;
    private @Getter @Setter int amount;
    private @Getter @Setter ReimbursementStatus status;
    private @Getter @Setter int userId;

}
