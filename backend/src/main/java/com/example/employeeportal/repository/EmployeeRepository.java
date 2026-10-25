package com.example.employeeportal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.employeeportal.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByEmail(String email);
    boolean existsByEmployeeCode(String employeeCode);

    boolean existsByEmailAndIdNot(String email, Long id);
    boolean existsByEmployeeCodeAndIdNot(String employeeCode, Long id);

    Optional<Employee> findByEmployeeCode(String employeeCode);

    @Query("SELECT DISTINCT e FROM Employee e LEFT JOIN FETCH e.department WHERE " +
           "(:search IS NULL OR :search = '' OR " +
           "LOWER(e.employeeCode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(e.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(e.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(e.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(e.designation) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(e.department.departmentName) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<Employee> searchEmployees(@Param("search") String search);
}