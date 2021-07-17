package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    EmployeeServiceImpl(@Autowired EmployeeRepository repository) {
        this.employeeRepository = repository;
    }

    public List<Employee> retrieveEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployee(Long employeeId) {
        Optional<Employee> optEmp = employeeRepository.findById(employeeId);
        return optEmp.get();
    }

    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long employeeId){
        employeeRepository.deleteById(employeeId);
    }

    public Employee updateEmployee(Employee employee) {
       return employeeRepository.save(employee);
    }
}