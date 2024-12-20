package com.revature.bdong_ers.Repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.revature.bdong_ers.Entities.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByRoleId(int roleId);
    
}
