export interface Department {
  id: number;
  name: string;
  location: string;
}

export interface Project {
  id: number;
  name: string;
  client: string;
  status: string;
}

export interface Employee {
  id: number;
  employeeCode: string;
  firstName: string;
  lastName: string;
  fullName: string;
  email: string;
  phone?: string;
  designation: string;
  salary: number;
  dateOfJoining: string;
  status: 'ACTIVE' | 'ON_LEAVE' | 'INACTIVE';
  department: Department;
  project?: Project;
  createdAt: string;
  updatedAt: string;
}

export interface EmployeeRequest {
  employeeCode: string;
  firstName: string;
  lastName: string;
  email: string;
  phone?: string;
  designation: string;
  salary: number;
  dateOfJoining: string;
  status: string;
  departmentId: number;
  projectId?: number | null;
}

export interface ApiErrorResponse {
  timestamp: string;
  status: number;
  error: string;
  message: string;
  path: string;
  fieldErrors?: { [key: string]: string };
}