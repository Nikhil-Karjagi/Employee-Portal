package com.example.employeeportal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.employeeportal.entity.Department;
import com.example.employeeportal.entity.Employee;
import com.example.employeeportal.repository.DepartmentRepository;
import com.example.employeeportal.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Transactional
    public Employee createEmployee(Employee employee) {
        if (employeeRepository.existsByEmployeeCode(employee.getEmployeeCode())) {
            throw new IllegalArgumentException("Employee Code already exists!");
        }
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new IllegalArgumentException("Email already exists!");
        }

        attachDepartment(employee);
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + id));

        if (employeeRepository.existsByEmployeeCodeAndIdNot(employeeDetails.getEmployeeCode(), id)) {
            throw new IllegalArgumentException("Employee Code already exists on another record!");
        }

        if (employeeRepository.existsByEmailAndIdNot(employeeDetails.getEmail(), id)) {
            throw new IllegalArgumentException("Email already exists on another record!");
        }

        existing.setEmployeeCode(employeeDetails.getEmployeeCode());
        existing.setFirstName(employeeDetails.getFirstName());
        existing.setLastName(employeeDetails.getLastName());
        existing.setEmail(employeeDetails.getEmail());
        existing.setPhone(employeeDetails.getPhone());
        existing.setDesignation(employeeDetails.getDesignation());
        existing.setSalary(employeeDetails.getSalary());
        existing.setDateOfJoining(employeeDetails.getDateOfJoining());
        existing.setStatus(employeeDetails.getStatus());

        attachDepartment(employeeDetails);
        existing.setDepartment(employeeDetails.getDepartment());

        return employeeRepository.save(existing);
    }

    @Transactional
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new IllegalArgumentException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }

    private void attachDepartment(Employee employee) {
        if (employee.getDepartment() != null && employee.getDepartment().getId() != null) {
            Department dept = departmentRepository.findById(employee.getDepartment().getId())
                    .orElse(null);
            employee.setDepartment(dept);
        }
    }
}