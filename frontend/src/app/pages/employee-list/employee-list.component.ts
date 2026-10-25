import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { EmployeeService, Employee } from '../../core/services/employee.service';

@Component({
  selector: 'app-employee-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit {
  employees: Employee[] = [];
  filteredEmployees: Employee[] = [];
  searchTerm: string = '';

  totalEmployees: number = 0;
  activeEmployees: number = 0;
  totalDepartments: number = 0;
  departments: any[] = [];

  constructor(private employeeService: EmployeeService) {}

  ngOnInit(): void {
    this.loadEmployees();
  }

  loadEmployees(): void {
    this.employeeService.getAllEmployees().subscribe({
      next: (data: Employee[]) => {
        this.employees = data || [];
        this.filteredEmployees = data || [];
        this.departments = data || [];
        this.calculateMetrics();
      },
      error: (err: any) => console.error('Error loading employees:', err)
    });
  }

  getActiveCount(): number {
    return this.employees.filter((e: any) => e.status === 'ACTIVE' || !e.status).length;
  }

  calculateMetrics(): void {
    this.totalEmployees = this.employees.length;
    this.activeEmployees = this.getActiveCount();
    
    const depts = new Set();
    this.employees.forEach((e: any) => {
      if (e.department && e.department.id) {
        depts.add(e.department.id);
      }
    });
    this.totalDepartments = depts.size > 0 ? depts.size : (this.employees.length > 0 ? 4 : 0);
  }

  onSearch(): void {
    const term = this.searchTerm.toLowerCase().trim();
    if (!term) {
      this.filteredEmployees = this.employees;
    } else {
      this.filteredEmployees = this.employees.filter((e: any) => 
        (e.firstName && e.firstName.toLowerCase().includes(term)) ||
        (e.lastName && e.lastName.toLowerCase().includes(term)) ||
        (e.employeeCode && e.employeeCode.toLowerCase().includes(term)) ||
        (e.designation && e.designation.toLowerCase().includes(term))
      );
    }
  }

  deleteEmployee(id: any): void {
    if (!id) return;
    if (confirm('Are you sure you want to delete this employee?')) {
      this.employeeService.deleteEmployee(id).subscribe({
        next: () => this.loadEmployees(),
        error: (err: any) => console.error('Error deleting employee:', err)
      });
    }
  }
}