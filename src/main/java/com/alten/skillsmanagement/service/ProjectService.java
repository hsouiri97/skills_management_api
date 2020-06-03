package com.alten.skillsmanagement.service;

import com.alten.skillsmanagement.dto.ProjectDto;
import com.alten.skillsmanagement.exception.ResourceNotFoundException;
import com.alten.skillsmanagement.model.*;
import com.alten.skillsmanagement.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.alten.skillsmanagement.model.AppRoleName.*;

@Service
@Transactional
public class ProjectService {

    private ProjectRepository projectRepository;
    private AccountService accountService;
    private ProjectUserService projectUserService;

    @Autowired
    public ProjectService(ProjectRepository projectRepository,
                          AccountService accountService,
                          ProjectUserService projectUserService) {
        this.projectRepository = projectRepository;
        this.accountService = accountService;
        this.projectUserService = projectUserService;
    }

    public Project getProject(Integer id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
    }

    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    public Project createProject(ProjectDto projectDto) {
        Project project = new Project();
        project.setTitle(projectDto.getTitle());
        project.setStartDate(projectDto.getStartDate());
        project.setExpectedEndDate(projectDto.getExpectedEndDate());

        return projectRepository.save(project);
    }

    public Project updateProject(Integer id, ProjectDto projectDto) {
        Project project = getProject(id);
        project.setTitle(projectDto.getTitle());
        project.setStartDate(projectDto.getStartDate());
        project.setExpectedEndDate(projectDto.getExpectedEndDate());

        return projectRepository.save(project);
    }

    public void deleteProject(Integer id) {
        projectRepository.deleteById(id);
    }

    public boolean affectProjectToManager(Integer projectId, Long userId) {
        AppUser appUser = accountService.getUser(userId);
        boolean allowed = checkRole(appUser);
        if (allowed) {
            Project project = getProject(projectId);
            project.setProjectManager(appUser);
            return true;
        }
        return false;
    }

    private boolean checkRole(AppUser appUser) {
        Set<AppRoleName> roles = appUser.getRoles().stream().map(AppRole::getRoleName).collect(Collectors.toSet());
        for (AppRoleName roleName: roles) {
            if (roleName.equals(MANAGER))
                return true;
        }
        return false;
    }

    public void addResponsibilityAndDateToProjectUser(Integer projectId,
                                                      Long userId,
                                                      Date startDate,
                                                      Date endDate,
                                                      String responsibility) {
        Project project = getProject(projectId);
        AppUser appUser = accountService.getUser(userId);

        ProjectUser projectUser = new ProjectUser();
        projectUser.setProject(project);
        projectUser.setAppUser(appUser);
        projectUser.setStartDate(startDate);
        projectUser.setEndDate(endDate);
        projectUser.setResponsibility(responsibility);

        project.getProjectUsers().add(projectUser);
        appUser.getProjectUsers().add(projectUser);

        projectUserService.saveProjectUser(projectUser);
    }

    public void updateResponsibilityAndDateToProjectUser(Integer projectId,
                                                         Long userId,
                                                         Date startDate,
                                                         Date endDate,
                                                         String responsibility) {
        ProjectUser projectUser = projectUserService.getProjectUser(new ProjectUserId(projectId, userId));
        projectUser.setStartDate(startDate);
        projectUser.setEndDate(endDate);
        projectUser.setResponsibility(responsibility);

        projectUserService.saveProjectUser(projectUser);
    }

    public void deleteResponsibilityAndDateToProjectUser(Integer projectId, Long userId) {
        ProjectUser projectUser = projectUserService.getProjectUser(new ProjectUserId(projectId, userId));
        projectUserService.deleteProjectUser(projectUser);
    }
}
