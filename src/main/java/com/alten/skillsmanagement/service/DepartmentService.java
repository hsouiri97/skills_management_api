package com.alten.skillsmanagement.service;

import com.alten.skillsmanagement.dto.DepartmentDto;
import com.alten.skillsmanagement.exception.ResourceNotFoundException;
import com.alten.skillsmanagement.model.Department;
import com.alten.skillsmanagement.model.Position;
import com.alten.skillsmanagement.model.Sector;
import com.alten.skillsmanagement.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class DepartmentService {
    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department getDepartment(Integer id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", id));
    }

    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    public Department createDepartment(DepartmentDto departmentDto) {
        Department department = new Department();
        department.setName(departmentDto.getName());
        departmentDto.getSectors().forEach(department::addSector);
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Integer id, DepartmentDto departmentDto) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Poll", "pollId", id));

        department.setName(departmentDto.getName());
        Set<Sector> sectors = department.getSectors();
        sectors.forEach(department::removeSector);
        department.getSectors().clear();
        departmentDto.getSectors().forEach(department::addSector);
        return departmentRepository.save(department);
    }

    public void deleteDepartment(Integer id) {
        departmentRepository.deleteById(id);
    }

    public void addPositionToDepartment(Integer departmentId, Position position) {
        Department department = getDepartment(departmentId);
        department.getPositions().add(position);
        departmentRepository.save(department);
    }

}
