package com.alten.skillsmanagement.controller;

import com.alten.skillsmanagement.dto.SkillsMatrixDto;
import com.alten.skillsmanagement.model.AppUser;
import com.alten.skillsmanagement.model.SkillsMatrix;
import com.alten.skillsmanagement.service.AccountService;
import com.alten.skillsmanagement.service.SkillsMatrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("skills-matrices")
public class SkillsMatrixRestController {

    private SkillsMatrixService skillsMatrixService;
    private AccountService accountService;

    @Autowired
    public SkillsMatrixRestController(SkillsMatrixService skillsMatrixService,
                                      AccountService accountService) {
        this.skillsMatrixService = skillsMatrixService;
        this.accountService = accountService;
    }

    @PreAuthorize("hasAuthority('skills_matrix:read')")
    @GetMapping("/{skillsMatrixId}")
    public ResponseEntity<SkillsMatrix> getSkillMatrix(@PathVariable Long skillsMatrixId) {
        SkillsMatrix skillsMatrix = skillsMatrixService.getSkillsMatrix(skillsMatrixId);
        return ResponseEntity.ok(skillsMatrix);
    }

    @PreAuthorize("hasAuthority('skills_matrix:read')")
    @GetMapping
    public ResponseEntity<List<SkillsMatrix>> skillsMatrices() {
        List<SkillsMatrix> skillsMatrices = skillsMatrixService.getSkillsMatrices();
        return ResponseEntity.ok(skillsMatrices);
    }

    @PreAuthorize("hasAuthority('skills_matrix:write')")
    @PostMapping
    public ResponseEntity<SkillsMatrix> createSkillsMatrix(@Valid @RequestBody SkillsMatrixDto skillsMatrixDto) {
        SkillsMatrix skillsMatrix = skillsMatrixService.createSkillsMatrix(skillsMatrixDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{positionId}")
                .buildAndExpand(skillsMatrix.getId()).toUri();

        return ResponseEntity.created(location).body(skillsMatrix);
    }

    @PreAuthorize(("hasRole('ROLE_ADMIN')"))
    @PutMapping("/{matrixId}")
    public ResponseEntity<SkillsMatrix> updateSkillsMatrix(@PathVariable Long matrixId,
                                           @Valid @RequestBody SkillsMatrixDto skillsMatrixDto) {
        SkillsMatrix skillsMatrix = skillsMatrixService.updateSkillsMatrix(matrixId, skillsMatrixDto);
        return ResponseEntity.ok(skillsMatrix);
    }

    @PreAuthorize(("hasRole('ROLE_ADMIN')"))
    @DeleteMapping("/{matrixId}")
    public ResponseEntity<String> deleteSkillsMatrix(@PathVariable Long matrixId) {
        skillsMatrixService.deleteSkillsMatrix(matrixId);
        return ResponseEntity.ok("SKILLSMATRIX DELETED");
    }


    @PreAuthorize(("hasRole('ROLE_ADMIN')"))
    @PutMapping("/{matrixId}/affect-to-user/{userId}")
    public ResponseEntity<String> affectMatrixToUser(@PathVariable Long matrixId,
                                                     @PathVariable Long userId) {

        skillsMatrixService.affectMatrixToUser(matrixId, userId);
        return ResponseEntity.ok("MATRIX AFFECTED TO USER.");
    }

    @PreAuthorize(("hasAnyRole('ROLE_ADMIN')"))
    @GetMapping("/user/{userId}")
    public ResponseEntity<SkillsMatrix> getSkillsMatrixByUserId(@PathVariable Long userId) {
        SkillsMatrix skillsMatrix = skillsMatrixService.getSkillsMatrixByAppUser(userId);
        return ResponseEntity.ok(skillsMatrix);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_CONSULTANT')")
    @GetMapping("/user")
    public ResponseEntity<SkillsMatrix> getSkillsMatrixOfAuthenticatedUser(@AuthenticationPrincipal Object principal) {
        String username = accountService.getUsernameOfAuthenticatedUser(principal);
        AppUser appUser = accountService.findUserByUsername(username);
        SkillsMatrix skillsMatrixByAppUser = skillsMatrixService.getSkillsMatrixByAppUser(appUser.getId());
        return ResponseEntity.ok(skillsMatrixByAppUser);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_CONSULTANT')")
    @PutMapping("/set-rating/{rating}")
    public ResponseEntity<SkillsMatrix> setRating(@AuthenticationPrincipal Object principal,
                                                  @PathVariable double rating) {
        String username = accountService.getUsernameOfAuthenticatedUser(principal);
        AppUser appUser = accountService.findUserByUsername(username);
        SkillsMatrix skillsMatrix = skillsMatrixService.getSkillsMatrixByAppUser(appUser.getId());
        SkillsMatrix skillsMatrixUpdated = skillsMatrixService.setAverageRating(skillsMatrix, rating);
        return ResponseEntity.ok(skillsMatrixUpdated);
    }




    /*@PreAuthorize("hasAuthority('skills_matrix:write')")
    @PutMapping
    public ResponseEntity<SkillsMatrix> updateSkillsMatrix(@Valid @Req)*/
}
