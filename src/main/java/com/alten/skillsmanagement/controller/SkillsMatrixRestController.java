package com.alten.skillsmanagement.controller;

import com.alten.skillsmanagement.dto.SkillsMatrixDto;
import com.alten.skillsmanagement.exception.ResourceNotFoundException;
import com.alten.skillsmanagement.model.AppUser;
import com.alten.skillsmanagement.model.Skill;
import com.alten.skillsmanagement.model.SkillsMatrix;
import com.alten.skillsmanagement.model.SkillsMatrixUser;
import com.alten.skillsmanagement.payload.MatrixOfUserResponse;
import com.alten.skillsmanagement.payload.UserOfMatrixResponse;
import com.alten.skillsmanagement.service.AccountService;
import com.alten.skillsmanagement.service.SkillService;
import com.alten.skillsmanagement.service.SkillsMatrixService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("skills-matrices")
public class SkillsMatrixRestController {

    private SkillsMatrixService skillsMatrixService;
    private AccountService accountService;
    private SkillService skillService;

    @Autowired
    public SkillsMatrixRestController(SkillsMatrixService skillsMatrixService,
                                      AccountService accountService,
                                      SkillService skillService) {
        this.skillsMatrixService = skillsMatrixService;
        this.accountService = accountService;
        this.skillService = skillService;
    }

    @PreAuthorize("hasAuthority('skills_matrix:read')")
    @GetMapping("/{matrixId}")
    public ResponseEntity<SkillsMatrix> getSkillMatrix(@PathVariable Long matrixId) {
        SkillsMatrix skillsMatrix = skillsMatrixService.getSkillsMatrix(matrixId);
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
    @PostMapping("/{matrixId}/affect-to-user/{userId}")
    public ResponseEntity<String> affectMatrixToUser(@PathVariable Long matrixId,
                                                     @PathVariable Long userId) {

        skillsMatrixService.affectMatrixToUser(matrixId, userId);
        return ResponseEntity.ok("MATRIX AFFECTED TO USER.");
    }

    @PreAuthorize(("hasAnyRole('ROLE_ADMIN')"))
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MatrixOfUserResponse>> getSkillsMatrixByUserId(@PathVariable Long userId) {
        List<MatrixOfUserResponse> matrixOfUserRespons =
                skillsMatrixService.getSkillsMatricesByAppUser(userId);
        return ResponseEntity.ok(matrixOfUserRespons);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_CONSULTANT')")
    @GetMapping("/my-matrices")
    public ResponseEntity<List<MatrixOfUserResponse>> getSkillsMatrixOfAuthenticatedUser(@AuthenticationPrincipal Object principal) {
        String username = accountService.getUsernameOfAuthenticatedUser(principal);
        AppUser appUser = accountService.findUserByUsername(username);
        //List<SkillsMatrix> skillsMatrices = skillsMatrixService.getSkillsMatricesByAppUser(appUser.getId());//old way
        List<MatrixOfUserResponse> matrixOfUserResponse =
                skillsMatrixService.getSkillsMatricesByAppUser(appUser.getId());
        return ResponseEntity.ok(matrixOfUserResponse);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping("/users-of-matrix/{matrixId}")
    public ResponseEntity<List<UserOfMatrixResponse>> getUsersOfMatrix(@PathVariable Long matrixId) {
        List<UserOfMatrixResponse> usersByMatrix = skillsMatrixService.getUsersByMatrix(matrixId);
        return ResponseEntity.ok(usersByMatrix);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_CONSULTANT')")
    @PutMapping("{matrixId}/set-rating/{rating}")
    public ResponseEntity<String> setRating(@AuthenticationPrincipal Object principal,
                                            @PathVariable Long matrixId,
                                            @PathVariable double rating) {
        String username = accountService.getUsernameOfAuthenticatedUser(principal);
        AppUser appUser = accountService.findUserByUsername(username);
        List<MatrixOfUserResponse> matrices =
                skillsMatrixService.getSkillsMatricesByAppUser(appUser.getId());
        //List<SkillsMatrix> skillsMatricesByAppUser = skillsMatrixService.getSkillsMatricesByAppUser(appUser.getId());//old way
        List<SkillsMatrix> skillsMatrices = matrices.stream().map(MatrixOfUserResponse::getSkillsMatrix).collect(Collectors.toList());
        SkillsMatrix skillsMatrix = skillsMatrices.stream().filter(matrix -> matrix.getId().equals(matrixId)).findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("SkillsMatrix", "id", matrixId));
        skillsMatrixService.setAverageRating(skillsMatrix.getId(), appUser.getId(), rating);
        return ResponseEntity.ok("Average rating is set successfully to the matrix.");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("of-skill/{skillId}")
    public ResponseEntity<List<SkillsMatrix>> getMatricesOfSkill(@PathVariable Long skillId) {
        Skill skill = this.skillService.getSkill(skillId);
        //ArrayList<Skill> skills = new ArrayList<>();
        //skills.add(skill);
        List<SkillsMatrix> skillsMatricesBySkills = this.skillsMatrixService.getSkillsMatricesBySkills(skill);
        return ResponseEntity.ok(skillsMatricesBySkills);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("title-only")
    public ResponseEntity<List<SkillsMatrix>> getAllMatricesTitleOnly() {
        List<SkillsMatrix> matrices = this.skillsMatrixService.getSkillsMatricesTitleOnly();
        return ResponseEntity.ok(matrices);
    }

    /*@PreAuthorize("hasAuthority('skills_matrix:write')")
    @PutMapping
    public ResponseEntity<SkillsMatrix> updateSkillsMatrix(@Valid @Req)*/

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users-and-ratings")
    public ResponseEntity<List<MatrixOfUserResponse>> getSkillsMatrixUserList() {
        List<MatrixOfUserResponse> matrices = this.skillsMatrixService.getSkillsMatrixUserList();
        return ResponseEntity.ok(matrices);
    }
}
