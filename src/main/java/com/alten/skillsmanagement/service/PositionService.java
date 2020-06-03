package com.alten.skillsmanagement.service;


import com.alten.skillsmanagement.dto.PositionDto;
import com.alten.skillsmanagement.exception.ResourceNotFoundException;
import com.alten.skillsmanagement.model.AppUser;
import com.alten.skillsmanagement.model.Department;
import com.alten.skillsmanagement.model.Position;
import com.alten.skillsmanagement.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PositionService {

    private PositionRepository positionRepository;
    private DepartmentService departmentService;
    private AccountService accountService;

    @Autowired
    public PositionService(PositionRepository positionRepository,
                           DepartmentService departmentService,
                           AccountService accountService) {
        this.positionRepository = positionRepository;
        this.departmentService = departmentService;
        this.accountService = accountService;
    }

    public Position getPosition(Integer id) {
        return positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Position", "id", id));
    }

    public List<Position> getPositions() {
        return positionRepository.findAll();
    }

    public Position createPosition(PositionDto positionDto) {
        Position position = new Position();
        position.setName(positionDto.getName());
        //Integer departmentId = positionDto.getDepartmentId();
        //departmentService.addPositionToDepartment(departmentId, position);

        //return positionRepository.save(position);

        return saveAndAddPositionToDepartment(position, positionDto);
    }

    public Position updatePosition(Integer id, PositionDto positionDto) {
        Position position = getPosition(id);
        position.setName(positionDto.getName());

        return saveAndAddPositionToDepartment(position, positionDto);
    }

    private Position saveAndAddPositionToDepartment(Position position, PositionDto positionDto) {

        Integer departmentId = positionDto.getDepartmentId();
        //departmentService.addPositionToDepartment(departmentId, position);
        Department department = departmentService.getDepartment(departmentId);
        position.setDepartment(department);
        return positionRepository.save(position);
        //return savedPosition;
    }

    public void deletePosition(Integer id) {
        positionRepository.deleteById(id);
    }

    public void affectPositionToUser(Integer positionId, Long appUserId) {
        Position position = getPosition(positionId);
        AppUser appUser = accountService.getUserById(appUserId);

        position.getAppUsers().add(appUser);
    }

    public void removePositionFromUser(Integer positionId, Long appUserId) {
        Position position = getPosition(positionId);
        AppUser appUser = accountService.getUserById(appUserId);

        position.getAppUsers().remove(appUser);
    }
}
