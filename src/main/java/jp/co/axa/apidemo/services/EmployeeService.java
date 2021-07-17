package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getEmployees();

    Employee getEmployeeById(Long employeeId);

    Employee saveEmployee(Employee employee);

    void deleteEmployeeById(Long employeeId);

    Employee updateEmployeeById(Long employeeId, Employee employee);
}