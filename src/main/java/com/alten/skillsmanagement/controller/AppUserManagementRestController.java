package com.alten.skillsmanagement.controller;

import com.alten.skillsmanagement.dto.AppUserDto;
import com.alten.skillsmanagement.dto.AppUserUpdateDto;
import com.alten.skillsmanagement.model.AppUser;
import com.alten.skillsmanagement.service.AccountService;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import java.net.URI;
import java.util.List;

import static com.alten.skillsmanagement.model.AppRoleName.CONSULTANT;

@RestController
@RequestMapping("/users-management")
public class AppUserManagementRestController {

    private final AccountService accountService;

    @Autowired
    public AppUserManagementRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<AppUser> getUser(@PathVariable Long userId) {
        AppUser appUser = accountService.getUser(userId);
        return ResponseEntity.ok(appUser);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<AppUser>> getUsers() {
        List<AppUser> appUsers = accountService.getUsers();
        return ResponseEntity.ok(appUsers);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<AppUser> createUser(@Valid @RequestBody AppUserDto appUserDto) {
        AppUser appUser = accountService.createUser(appUserDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{userId}")
                .buildAndExpand(appUser.getId()).toUri();

        return ResponseEntity.created(location).body(appUser);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_CONSULTANT')")
    @PutMapping("/{userId}")
    public ResponseEntity<AppUser> updateUser(@PathVariable Long userId,
                                              @Valid @RequestBody AppUserUpdateDto appUserUpdateDto) {
        AppUser appUser = accountService.updateUser(userId, appUserUpdateDto);
        return ResponseEntity.ok(appUser);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        accountService.deleteUser(userId);
        return ResponseEntity.ok("USER DELETED");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{positionId}/affect-to-user/{userId}")
    public ResponseEntity<String> affectPositionToUser(@PathVariable Integer positionId,
                                                       @PathVariable Long userId) {
        accountService.affectPositionToUser(positionId, userId);
        return ResponseEntity.ok("POSITION AFFECTED TO USER.");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/make-manager/{userId}")
    public ResponseEntity<AppUser> makeManager(@PathVariable Long userId) {
        AppUser appUser = accountService.makeManager(userId);
        return ResponseEntity.ok(appUser);
    }



}
