package com.api.apirestfulcrud.controller;

import com.api.apirestfulcrud.exception.ResourceNotFoundException;
import com.api.apirestfulcrud.model.Employee;
import com.api.apirestfulcrud.repository.IEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.module.ResolutionException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private IEmployeeRepository employeeRepository;

    //get all employees
    @GetMapping("/all/employees")
    public List<Employee> getAllEmployees(){

        return employeeRepository.findAll();

    }

    //get by employee by id
    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") long id) throws ResourceNotFoundException {

        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResolutionException("Employee not found with id: " + id));

        return ResponseEntity.ok().body(employee);
    }

    //create employee
    @PostMapping("/create/employee")
    public Employee createEmployee(@Validated @RequestBody Employee employee){

       return employeeRepository.save(employee);

    }

    //update employee by id
    @PatchMapping("employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") long id, @Validated @RequestBody Employee employeeDetails) throws ResourceNotFoundException{
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResolutionException("Employee not found with id: " + id));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());

        employeeRepository.save(employee);

        return ResponseEntity.ok().body(employee);

    }

    //delete employee by id
    @DeleteMapping("/delete/employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") long id) throws ResourceNotFoundException{
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResolutionException("Employee not found with id: " + id));

        employeeRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }


}
