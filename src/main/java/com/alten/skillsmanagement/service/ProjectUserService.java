package com.alten.skillsmanagement.service;

import com.alten.skillsmanagement.exception.ResourceNotFoundException;
import com.alten.skillsmanagement.model.ProjectUser;
import com.alten.skillsmanagement.model.ProjectUserId;
import com.alten.skillsmanagement.repository.ProjectUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProjectUserService {

    private ProjectUserRepository projectUserRepository;

    @Autowired
    public ProjectUserService(ProjectUserRepository projectUserRepository) {
        this.projectUserRepository = projectUserRepository;
    }

    public ProjectUser saveProjectUser(ProjectUser projectUser) {
        return projectUserRepository.save(projectUser);
    }

    public ProjectUser getProjectUser(ProjectUserId projectUserId) {
        return projectUserRepository.findById(projectUserId)
                .orElseThrow(() -> new ResourceNotFoundException("ProjectUser", "id", projectUserId));
    }

    public void deleteProjectUser(ProjectUser projectUser) {
        projectUserRepository.delete(projectUser);
    }
}
