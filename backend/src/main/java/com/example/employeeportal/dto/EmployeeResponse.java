package com.example.employeeportal.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record EmployeeResponse(
    Long id,
    String employeeCode,
    String firstName,
    String lastName,
    String fullName,
    String email,
    String phone,
    String designation,
    BigDecimal salary,
    LocalDate dateOfJoining,
    String status,
    DepartmentResponse department,
    ProjectResponse project,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public record DepartmentResponse(Long id, String name, String location) {}
    public record ProjectResponse(Long id, String name, String client, String status) {}
}