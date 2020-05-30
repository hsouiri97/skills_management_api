package com.alten.skillsmanagement.controller;

import com.alten.skillsmanagement.dto.DepartmentDto;
import com.alten.skillsmanagement.model.Department;
import com.alten.skillsmanagement.model.Sector;
import com.alten.skillsmanagement.repository.DepartmentRepository;
import com.alten.skillsmanagement.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/departments")
public class DepartmentRestController {

    private DepartmentService departmentService;

    @Autowired
    public DepartmentRestController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<Department> getDepartment(@PathVariable Integer departmentId) {
        Department department = departmentService.getDepartment(departmentId);
        return ResponseEntity.ok(department);

    }

    @GetMapping
    public ResponseEntity<List<Department>> getDepartments() {
        List<Department> departments = departmentService.getDepartments();
        return ResponseEntity.ok(departments);
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@Valid @RequestBody DepartmentDto departmentDto) {
        Department department = departmentService.createDepartment(departmentDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{departmentId}")
                .buildAndExpand(department.getId()).toUri();
        return ResponseEntity.created(location).body(department);
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Integer departmentId,
                                                       @Valid @RequestBody DepartmentDto departmentDto) {
        final Department updatedDepartment = departmentService.updateDepartment(departmentId, departmentDto);
        return ResponseEntity.ok(updatedDepartment);
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Integer departmentId) {
        departmentService.deleteDepartment(departmentId);
        return ResponseEntity.ok("DEPARTMENT DELETED");
    }

}
