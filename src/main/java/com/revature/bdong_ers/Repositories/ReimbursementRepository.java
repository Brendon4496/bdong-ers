package com.revature.bdong_ers.Repositories;

import com.revature.bdong_ers.Entities.Reimbursement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReimbursementRepository extends CrudRepository<Reimbursement, Integer> {
    Optional<List<Reimbursement>> findByStatus(String status);
    Optional<List<Reimbursement>> findByUserId(int userId);
}
