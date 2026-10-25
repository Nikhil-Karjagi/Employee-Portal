package com.example.employeeportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.employeeportal.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}