package com.alten.skillsmanagement.controller;

import com.alten.skillsmanagement.dto.SkillsMatrixDto;
import com.alten.skillsmanagement.model.SkillsMatrix;
import com.alten.skillsmanagement.service.SkillsMatrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("skills-matrices")
public class SkillsMatrixRestController {

    private SkillsMatrixService skillsMatrixService;

    @Autowired
    public SkillsMatrixRestController(SkillsMatrixService skillsMatrixService) {
        this.skillsMatrixService = skillsMatrixService;
    }

    @PreAuthorize("hasAuthority('skills_matrix:read')")
    @GetMapping("/{skillsMatrixId}")
    public SkillsMatrix getSkillMatrix(@PathVariable Long skillsMatrixId) {
        return skillsMatrixService.getSkillsMatrix(skillsMatrixId);
    }

    @PreAuthorize("hasAuthority('skills_matrix:read')")
    @GetMapping
    public List<SkillsMatrix> skillsMatrices() {
        return skillsMatrixService.getSkillsMatrices();
    }

    @PreAuthorize("hasAuthority('skills_matrix:write')")
    @PostMapping
    public SkillsMatrix createSkillsMatrix(@Valid @RequestBody SkillsMatrixDto skillsMatrixDto) {
        return skillsMatrixService.createSkillsMatrix(skillsMatrixDto);
    }

    /*@PreAuthorize("hasAuthority('skills_matrix:write')")
    @PutMapping
    public ResponseEntity<SkillsMatrix> updateSkillsMatrix(@Valid @Req)*/
}
