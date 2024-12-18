package com.revature.bdong_ers.Services;

import com.revature.bdong_ers.Entities.Reimbursement;
import com.revature.bdong_ers.Entities.User;
import com.revature.bdong_ers.Repositories.ReimbursementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReimbursementService {

    private ReimbursementRepository reimbursementRepository;

    @Autowired
    public ReimbursementService(ReimbursementRepository reimbursementRepository) {
        this.reimbursementRepository = reimbursementRepository;
    }

    public Reimbursement createReimbursement(Reimbursement reimbursement) { return null; }
    public List<Reimbursement> viewReimbursements(User user) { return null; }
    public List<Reimbursement> viewPendingReimbursements(User user) { return null; }
    public Reimbursement updateReimbursement(Reimbursement reimbursement) { return null; }
}
