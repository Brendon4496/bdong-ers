package com.revature.bdong_ers.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.bdong_ers.Entities.Role;
import com.revature.bdong_ers.Entities.User;
import com.revature.bdong_ers.Repositories.RoleRepository;
@Service
public class RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository RoleRepository) {
        this.roleRepository = RoleRepository;
    }   
    
    public boolean hasAdminPermissions(int roleId) {
        Role role = roleRepository.findById(roleId).orElse(null);
        if (role == null) {
            return false;
        }
        return role.isAdmin();
    }
    
    public boolean hasAdminPermissions(User user) {
        Role userRole = roleRepository.findById(user.getRoleId()).orElse(null);
        if (userRole == null) {
            return false;
        }
        return userRole.isAdmin();
    }
}
