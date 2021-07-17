package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.lang.model.element.UnknownElementException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    private final EmployeeService employeeService;

    EmployeeController(@Autowired EmployeeService service) {
        this.employeeService = service;
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return employeeService.retrieveEmployees();
    }

    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable(name="employeeId")Long employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    @PostMapping("/employees")
    public Employee saveEmployee(@RequestBody Employee employee){
        return employeeService.saveEmployee(employee);
    }

    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(@PathVariable(name="employeeId")Long employeeId){
        employeeService.deleteEmployee(employeeId);
    }

    @PutMapping("/employees/{employeeId}")
    public Employee updateEmployee(@RequestBody Employee employee,
                               @PathVariable(name="employeeId")Long employeeId){
        Employee emp = employeeService.getEmployee(employeeId);
        if (emp == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown Employee");
        }
        employee.setId(emp.getId());
        emp = employeeService.updateEmployee(employee);
        return emp;
    }
}
