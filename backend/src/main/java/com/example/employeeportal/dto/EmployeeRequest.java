package com.example.employeeportal.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeRequest(
    @NotBlank(message = "Employee code is required")
    String employeeCode,

    @NotBlank(message = "First name is required")
    String firstName,

    @NotBlank(message = "Last name is required")
    String lastName,

    @NotBlank(message = "Email address is required")
    @Email(message = "Enter a valid email address")
    String email,

    String phone,

    @NotBlank(message = "Designation is required")
    String designation,

    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be greater than zero")
    BigDecimal salary,

    @NotNull(message = "Date of joining is required")
    @PastOrPresent(message = "Date of joining cannot be in the future")
    LocalDate dateOfJoining,

    @NotBlank(message = "Employment status is required")
    String status,

    @NotNull(message = "Department is required")
    Long departmentId,

    Long projectId
) {}