package com.example.demo.Service;

import com.example.demo.Dto.EmployeeDto;
import com.example.demo.Entity.DepartmentEntity;
import com.example.demo.Entity.EmployeeEntity;
import com.example.demo.Exception.DepartmentNotFoundException;
import com.example.demo.Exception.EmployeeNotFoundException;
import com.example.demo.Repository.DepartmentRepository;
import com.example.demo.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class EmployeeService {


    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public List<EmployeeDto> addEmployees() {
        return StreamSupport.stream(employeeRepository.findAll().spliterator(), false)
                .map(this::getEmployee)
                .collect(Collectors.toList());

    }

    private EmployeeDto getEmployee(EmployeeEntity employeeEntity) {
        return employeeDtoFromEmployeeEntity(employeeEntity);
    }

    private EmployeeDto employeeDtoFromEmployeeEntity(EmployeeEntity employeeEntity) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employeeEntity.getId());
        employeeDto.setJob(employeeEntity.getJob());
        employeeDto.setName(employeeEntity.getName());
        employeeDto.setDepartmentId(employeeEntity.getDepartmentEntity().getId());
        return employeeDto;
    }

    public EmployeeDto
    getEmployeeId(long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        return getEmployee(employeeEntity);
    }

    public void addEmployee(EmployeeDto employeeDto) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setName(employeeDto.getName());
        employeeEntity.setJob(employeeDto.getJob());
        DepartmentEntity departmentEntity = departmentRepository.findById(employeeDto.getDepartmentId()).
                orElseThrow(() -> new DepartmentNotFoundException(employeeDto.getDepartmentId()));
        employeeEntity.setDepartmentEntity(departmentEntity);
        employeeRepository.save(employeeEntity);

    }

    public void updateEmployeeById(EmployeeDto employeeDto, long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        employeeEntity.setName(employeeDto.getName());
        employeeEntity.setJob(employeeDto.getJob());
        employeeRepository.save(employeeEntity);

    }

    public void deleteEmployee(long id) {
        EmployeeDto employeeDto1 = getEmployeeId(id);
        employeeRepository.deleteById(employeeDto1.getId());
    }


}

