package com.example.employeeportal.controller;

import com.example.employeeportal.entity.Department;
import com.example.employeeportal.entity.Project;
import com.example.employeeportal.repository.DepartmentRepository;
import com.example.employeeportal.repository.ProjectRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LookupController {

    private final DepartmentRepository departmentRepository;
    private final ProjectRepository projectRepository;

    public LookupController(DepartmentRepository departmentRepository, ProjectRepository projectRepository) {
        this.departmentRepository = departmentRepository;
        this.projectRepository = projectRepository;
    }

    @GetMapping("/departments")
    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    @GetMapping("/projects")
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }
}