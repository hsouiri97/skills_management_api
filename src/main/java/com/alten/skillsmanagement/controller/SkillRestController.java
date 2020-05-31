package com.alten.skillsmanagement.controller;

import com.alten.skillsmanagement.dto.SkillDto;
import com.alten.skillsmanagement.model.Skill;
import com.alten.skillsmanagement.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("skills")
public class SkillRestController {

    private SkillService skillService;

    @Autowired
    public SkillRestController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PreAuthorize("hasAuthority('skills_matrix:read')")
    @GetMapping("/{skillId}")
    public ResponseEntity<Skill> getSkill(@PathVariable Long skillId) {
        Skill skill = skillService.getSkill(skillId);
        return ResponseEntity.ok(skill);
    }

    @PreAuthorize("hasAuthority('skills_matrix:read')")
    @GetMapping
    public ResponseEntity<List<Skill>> getSkills() {
        List<Skill> skills = skillService.getSkills();
        return ResponseEntity.ok(skills);
    }

    @PreAuthorize("hasAuthority('skills_matrix:write')")
    @PostMapping
    public ResponseEntity<Skill> createSkill(@Valid @RequestBody SkillDto skillDto) {
        Skill skill = skillService.createSkill(skillDto);
        return ResponseEntity.ok(skill);
    }

    @PreAuthorize("hasAuthority('skills_matrix:write')")
    @PutMapping("/{skillId}")
    public ResponseEntity<Skill> updateSkill(@PathVariable Long skillId,
                                             @Valid @RequestBody SkillDto skillDto) {
        Skill skill = skillService.updateSkill(skillId, skillDto);
        return ResponseEntity.ok(skill);
    }

    @PreAuthorize("hasAuthority('skills_matrix:write')")
    @DeleteMapping("/{skillId}")
    public ResponseEntity<String> deleteSkill(@PathVariable Long skillId) {
        skillService.deleteSkill(skillId);
        return ResponseEntity.ok("SKILL DELETED");
    }

    @PreAuthorize("hasAuthority('skills_matrix:write')")
    @PostMapping("/{skillId}/{matrixId}")
    public ResponseEntity<String> addSkillToMatrix(@PathVariable Long skillId,
                                                   @PathVariable Long matrixId) {
        skillService.addSkillToMatrix(skillId, matrixId);
        return ResponseEntity.ok("SKILL ADDED TO MATRIX");
    }





}
