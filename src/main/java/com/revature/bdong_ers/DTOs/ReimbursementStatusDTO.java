package com.revature.bdong_ers.DTOs;

import com.revature.bdong_ers.Entities.Reimbursement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReimbursementStatusDTO {

    private int reimbursementId;
    private String status;

    public ReimbursementStatusDTO(Reimbursement reimbursement) {
        this.reimbursementId = reimbursement.getReimbursementId();
        this.status = reimbursement.getStatus();
    }
    
}
