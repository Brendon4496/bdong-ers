package com.revature.bdong_ers.Services;

import com.revature.bdong_ers.Entities.Reimbursement;
import com.revature.bdong_ers.Entities.User;
import com.revature.bdong_ers.Repositories.ReimbursementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReimbursementService {

    private ReimbursementRepository reimbursementRepository;

    @Autowired
    public ReimbursementService(ReimbursementRepository reimbursementRepository) {
        this.reimbursementRepository = reimbursementRepository;
    }

    public Reimbursement createReimbursement(Reimbursement reimbursement) {
        // Amount must be > 0
        if (reimbursement.getAmount() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return reimbursementRepository.save(reimbursement);
    }
    public List<Reimbursement> viewReimbursements(User user) {
        return (List<Reimbursement>) reimbursementRepository.findAll();
    }

    public List<Reimbursement> viewPendingReimbursements(User user) {
        return reimbursementRepository.findByUserId(user.getUserId()).orElse(null);
    }

    public Reimbursement updateReimbursement(int id, Reimbursement reimbursement) {

        // Reimbursement must already exist
        Reimbursement updatedReimbursement = reimbursementRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        // Amount must be > 0
        if (reimbursement.getAmount() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        updatedReimbursement.setDescription(reimbursement.getDescription());
        updatedReimbursement.setAmount(reimbursement.getAmount());
        updatedReimbursement.setStatus(reimbursement.getStatus());
        return this.reimbursementRepository.save(updatedReimbursement);
    }
}
