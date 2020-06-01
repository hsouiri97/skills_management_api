package com.alten.skillsmanagement.controller;

import com.alten.skillsmanagement.dto.ProjectDto;
import com.alten.skillsmanagement.model.Project;
import com.alten.skillsmanagement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("projects")
public class ProjectRestController {

    private ProjectService projectService;

    @Autowired
    public ProjectRestController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProject(@PathVariable Integer projectId) {
        Project project = projectService.getProject(projectId);
        return ResponseEntity.ok(project);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<Project>> getProjects() {
        List<Project> projects = projectService.getProjects();
        return ResponseEntity.ok(projects);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Project> createProject(@Valid @RequestBody ProjectDto projectDto) {
        Project project = projectService.createProject(projectDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{projectId}")
                .buildAndExpand(project.getId()).toUri();
        return ResponseEntity.created(location).body(project);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(@PathVariable Integer projectId,
                                 @Valid @RequestBody ProjectDto projectDto) {
        Project project = projectService.updateProject(projectId, projectDto);
        return ResponseEntity.ok(project);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable Integer projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.ok("PROJECT DELETED");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{projectId}/affect-to-manager/{userId}")
    public ResponseEntity<String> affectProjectToManager(@PathVariable Integer projectId,
                                                         @PathVariable Long userId) {
        boolean success = projectService.affectProjectToManager(projectId, userId);
        if (success)
            return ResponseEntity.ok("PROJECT AFFECTED TO MANAGER");

        return ResponseEntity.badRequest().body("User doesn't have enough privileges !!");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{projectId}/affect/consultant/{userId}/start-date/{startDate}/end-date/{endDate}/responsibility/{responsibility}")
    public ResponseEntity<String> affectProjectToConsultant(@PathVariable Integer projectId,
                                                            @PathVariable Long userId,
                                                            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                                            @PathVariable String responsibility) {
        projectService.addResponsibilityAndDateToProjectUser(projectId,
                userId, startDate, endDate, responsibility);
        return ResponseEntity.ok("PROJECT AFFECTED TO CONSULTANT");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{projectId}/update/consultant/{userId}/start-date/{startDate}/end-date/{endDate}/responsibility/{responsibility}")
    public ResponseEntity<String> updateAffectationToConsultant(@PathVariable Integer projectId,
                                                                @PathVariable Long userId,
                                                                @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                                @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                                                @PathVariable String responsibility) {
        projectService.updateResponsibilityAndDateToProjectUser(projectId,
                userId, startDate, endDate, responsibility);
        return ResponseEntity.ok("PROJECT AFFECTATION TO CONSULTANT UPDATED");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("{projectId}/delete/consultant/{userId}")
    public ResponseEntity<String> unaffectedProjectToConsultant(@PathVariable Integer projectId, @PathVariable Long userId) {

        projectService.deleteResponsibilityAndDateToProjectUser(projectId, userId);

        return ResponseEntity.ok("PROJECT UNAFFECTED TO CONSULTANT");
    }





}
