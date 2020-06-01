package com.alten.skillsmanagement.service;

import com.alten.skillsmanagement.exception.ResourceNotFoundException;
import com.alten.skillsmanagement.model.AppRole;
import com.alten.skillsmanagement.model.AppRoleName;
import com.alten.skillsmanagement.model.AppUser;
import com.alten.skillsmanagement.repository.AppRoleRepository;
import com.alten.skillsmanagement.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AccountService {
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;

    @Autowired
    public AccountService(PasswordEncoder passwordEncoder,
                          AppUserRepository appUserRepository,
                          AppRoleRepository appRoleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
    }

    public AppUser saveUser(AppUser appUser) {
        String hashedPassword = passwordEncoder.encode(appUser.getPassword());
        appUser.setPassword(hashedPassword);
        return appUserRepository.save(appUser);
    }


    public AppUser findUserByUsername(String username) {
        return appUserRepository.findAppUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username: %s not found.", username)));
    }

    public AppUser getUserById(Long appUserId) {
        return appUserRepository.findById(appUserId)
                .orElseThrow(() -> new ResourceNotFoundException("AppUser", "id", appUserId));
    }

    public Boolean usernameTaken(String username) {
        return appUserRepository.existsByUsername(username);
    }

    public Boolean existsUserByEmail(String email) {
        return appUserRepository.existsByEmail(email);
    }

    public void addRoleToUser(String username, AppRoleName roleName) {
        AppUser appUser = findUserByUsername(username);
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        appUser.getRoles().add(appRole);
    }
}
