package com.example.demo.Controller;

import com.example.demo.Dto.EmployeeDto;
import com.example.demo.Service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/employees")
    public List<EmployeeDto> addEmployees() {
        return employeeService.addEmployees();
    }
    @GetMapping("/employee/{id}")
    public EmployeeDto getEmployeeId(@PathVariable long id) {
        return employeeService.getEmployeeId(id);
    }

    @PostMapping("/employee")
    public void addEmployee(@RequestBody EmployeeDto employeeDto) {
        employeeService.addEmployee(employeeDto);
    }
    @PutMapping("/employee/{id}")
    public void updateEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable long id) {
        employeeService.updateEmployeeById(employeeDto, id);
    }
    @DeleteMapping("/employee/{id}")
    public void deleteEmployee(@PathVariable long id) {
        employeeService.deleteEmployee(id);
    }
}

