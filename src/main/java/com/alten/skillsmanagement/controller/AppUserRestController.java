package com.alten.skillsmanagement.controller;

import com.alten.skillsmanagement.auth.UserDetailsImpl;
import com.alten.skillsmanagement.model.AppUser;
import com.alten.skillsmanagement.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppUserRestController {
    private final AccountService accountService;

    @Autowired
    public AppUserRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/me")
    public AppUser userInfo(@AuthenticationPrincipal Object principal) {
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        if (username==null || username.isEmpty()) {
            throw new RuntimeException("username is null or empty");
        }
        return accountService.findUserByUsername(username);
    }
}
