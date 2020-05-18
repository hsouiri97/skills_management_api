package com.alten.skillsmanagement.controller;

import com.alten.skillsmanagement.dto.AppUserDto;
import com.alten.skillsmanagement.model.AppUser;
import com.alten.skillsmanagement.service.AccountService;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.alten.skillsmanagement.model.AppRoleName.*;

@RestController
public class AppUserRestController {
    private final AccountService accountService;

    @Autowired
    public AppUserRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/sign-up")
    public AppUser registerUser(@Valid @RequestBody AppUserDto appUserDto) {
        String email = appUserDto.getEmail();
        //AppUser appUser = accountService.findUserByEmail(email);
        //if (appUser != null) throw new RuntimeException(String.format("Email %s already exists.", email));

        String password = appUserDto.getPassword();
        String confirmPassword = appUserDto.getConfirmPassword();
        if (!password.equals(confirmPassword)) throw new RuntimeException("You should confirm your password!");

        String firstName = appUserDto.getFirstName();
        String lastName = appUserDto.getLastName();
        String username = String.format("%c%s", firstName.charAt(0), lastName);

        Boolean usernameTaken = accountService.usernameTaken(username);
        if (usernameTaken) {
            int i = 1;
            do {
                username = String.format("%c%s%d", firstName.charAt(0), lastName, i);
                usernameTaken = accountService.usernameTaken(username);
                i++;
            } while (usernameTaken);
        }
        String address = appUserDto.getAddress();
        String cin = appUserDto.getCin();

        //mapping
        AppUser appUser = new AppUser();
        appUser.setFirstName(firstName);
        appUser.setLastName(lastName);
        appUser.setEmail(email);
        appUser.setUsername(username);
        appUser.setPassword(password);
        appUser.setCin(cin);
        appUser.setAddress(address);

        accountService.saveUser(appUser);
        appUser.setRoles(Sets.newHashSet());
        accountService.addRoleToUser(appUser.getUsername(), CONSULTANT);
        return appUser;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_CONSULTANT')")
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
