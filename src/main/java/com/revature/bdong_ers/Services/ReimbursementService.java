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

        if (reimbursement.getStatus() == null) {
            System.out.println("----------------------");
            System.out.println("CREATED WITHOUT STATUS");
            System.out.println("----------------------");
            System.out.println("Setting to PENDING....");
            System.out.println("----------------------");
            reimbursement.setStatus("PENDING");
        }
        return reimbursementRepository.save(reimbursement);
    }
    public List<Reimbursement> viewReimbursements(User user) {
        return (List<Reimbursement>) reimbursementRepository.findAll();
    }

    public List<Reimbursement> viewReimbursementsByStatus(String status) {
        return reimbursementRepository.findByStatus(status).orElse(null);
    }

    public List<Reimbursement> viewReimbursementsByStatus(User user, String status) {
        return reimbursementRepository.findByUserIdAndStatus(user.getUserId(), status).orElse(null);
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

    // Temporary while there is no front end - should use previous function once there is a GUI
    // to actually change all details of a reimbursement
    public Reimbursement updateReimbursementStatus(int id, String status) {

        // Reimbursement must already exist
        Reimbursement updatedReimbursement = reimbursementRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        updatedReimbursement.setStatus(status);
        return this.reimbursementRepository.save(updatedReimbursement);
    }
}
