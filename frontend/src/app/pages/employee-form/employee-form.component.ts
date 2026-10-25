import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { EmployeeService, Employee } from '../../core/services/employee.service';

@Component({
  selector: 'app-employee-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './employee-form.component.html',
  styleUrls: ['./employee-form.component.css'],
})
export class EmployeeFormComponent implements OnInit {
  isEditMode = false;
  employeeId?: number;
  errorMessage = '';

  employee: Employee = {
    employeeCode: '',
    firstName: '',
    lastName: '',
    email: '',
    phone: '',
    designation: '',
    salary: 0,
    dateOfJoining: new Date().toISOString().split('T')[0],
    status: 'ACTIVE',
    department: null, // Fixes TS2561 error
  };

  selectedDepartmentId: number | null = null;

  departments = [
    { id: 1, departmentCode: 'ENG', departmentName: 'Engineering' },
    { id: 2, departmentCode: 'HR', departmentName: 'Human Resources' },
    { id: 3, departmentCode: 'FIN', departmentName: 'Finance' },
    { id: 4, departmentCode: 'MKT', departmentName: 'Marketing' },
  ];

  constructor(
    private employeeService: EmployeeService,
    private router: Router,
    private route: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.isEditMode = true;
      this.employeeId = +idParam;
      this.loadEmployee(this.employeeId);
    }
  }

  loadEmployee(id: number): void {
    this.employeeService.getEmployeeById(id).subscribe({
      next: (data) => {
        this.employee = data;
        if (data.department) {
          this.selectedDepartmentId = data.department.id || null;
        }
      },
      error: (err) => {
        console.error('Error loading employee:', err);
        this.errorMessage = 'Failed to load employee details.';
      },
    });
  }

  onSubmit(): void {
    this.errorMessage = '';

    // Assign full department object based on selected ID
    if (this.selectedDepartmentId) {
      const dept = this.departments.find((d) => d.id === +this.selectedDepartmentId!);
      this.employee.department = dept ? dept : null;
    } else {
      this.employee.department = null;
    }

    // Clean payload strings
    this.employee.employeeCode = this.employee.employeeCode.trim();
    this.employee.email = this.employee.email.trim();

    if (this.isEditMode && this.employeeId) {
      this.employeeService.updateEmployee(this.employeeId, this.employee).subscribe({
        next: () => this.router.navigate(['/employees']),
        error: (err) => {
          console.error(err);
          this.errorMessage =
            err.error?.message ||
            'Failed to update employee. Ensure employee code and email are unique.';
        },
      });
    } else {
      this.employeeService.createEmployee(this.employee).subscribe({
        next: () => this.router.navigate(['/employees']),
        error: (err) => {
          console.error(err);
          this.errorMessage =
            err.error?.message ||
            'Failed to create employee. Ensure employee code and email are unique.';
        },
      });
    }
  }
}
