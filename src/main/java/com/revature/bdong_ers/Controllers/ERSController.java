package com.revature.bdong_ers.Controllers;

import com.revature.bdong_ers.Services.ReimbursementService;
import com.revature.bdong_ers.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ERSController {

    private ReimbursementService reimbursementService;
    private UserService userService;

    @Autowired
    public ERSController(ReimbursementService reimbursementService, UserService userService) {
        this.reimbursementService = reimbursementService;
        this.userService = userService;
    }
}
