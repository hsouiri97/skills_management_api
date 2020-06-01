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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Skill> createSkill(@Valid @RequestBody SkillDto skillDto) {
        Skill skill = skillService.createSkill(skillDto);
        return ResponseEntity.ok(skill);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{skillId}")
    public ResponseEntity<Skill> updateSkill(@PathVariable Long skillId,
                                             @Valid @RequestBody SkillDto skillDto) {
        Skill skill = skillService.updateSkill(skillId, skillDto);
        return ResponseEntity.ok(skill);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{skillId}")
    public ResponseEntity<String> deleteSkill(@PathVariable Long skillId) {
        skillService.deleteSkill(skillId);
        return ResponseEntity.ok("SKILL DELETED");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{skillId}/add-to-matrix/{matrixId}")
    public ResponseEntity<String> addSkillToMatrix(@PathVariable Long skillId,
                                                   @PathVariable Long matrixId) {
        skillService.addSkillToMatrix(skillId, matrixId);
        return ResponseEntity.ok("SKILL ADDED TO MATRIX");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{skillId}/remove-from-matrix/{matrixId}")
    public ResponseEntity<String> removeSkillFromMatrix(@PathVariable Long skillId,
                                                        @PathVariable Long matrixId) {
        skillService.removeSkillFromMatrix(skillId, matrixId);
        return ResponseEntity.ok("SKILL REMOVED FROM MATRIX");
    }

    /*@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{skillId}/affect-to-user/{userId}")
    public ResponseEntity<String> affectSkillToUser(@PathVariable Long skillId,
                                                    @PathVariable Long userId) {
        skillService.addSkillToAppUser(skillId, userId);
        return ResponseEntity.ok("SKILL AFFECTED TO USER");
    }*/ // No need we affect matrices not skills

    //This method should be invoked by consultants and maybe managers
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_CONSULTANT', 'ROLE_ADMIN')")
    @PostMapping("/{skillId}/add-rating/{rating}/to-user/{userId}")
    public ResponseEntity<String> addRatingToSkillUser(@PathVariable Long skillId,
                                                       @PathVariable Double rating,
                                                       @PathVariable Long userId
                                                       ) {
        skillService.addRatingToSkillUser(skillId, rating, userId);
        return ResponseEntity.ok("RATING ADDED TO SkillUser");
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_CONSULTANT', 'ROLE_ADMIN')")
    @PutMapping("/{skillId}/update-rating/{rating}/of-user/{userId}")
    public ResponseEntity<String> updateRatingOfSkillUser(@PathVariable Long skillId,
                                                       @PathVariable Double rating,
                                                       @PathVariable Long userId ){
        skillService.updateRatingOfSkillUser(skillId, rating, userId);
        return ResponseEntity.ok("RATING UPDATED");
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_CONSULTANT', 'ROLE_ADMIN')")
    @DeleteMapping("/{skillId}/delete-rating-of-user/{userId}")
    public ResponseEntity<String> deleteRatingOfSkillUser(@PathVariable Long skillId,
                                                          @PathVariable Long userId ) {

        skillService.deleteRatingOfSkillUser(skillId, userId);
        return ResponseEntity.ok("SkillUser DELETED");
    }
}
