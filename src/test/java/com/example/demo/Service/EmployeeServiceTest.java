package com.example.demo.Service;

import com.example.demo.Dto.EmployeeDto;
import com.example.demo.Entity.DepartmentEntity;
import com.example.demo.Entity.EmployeeEntity;
import com.example.demo.Exception.DepartmentNotFoundException;
import com.example.demo.Exception.EmployeeNotFoundException;
import com.example.demo.Repository.DepartmentRepository;
import com.example.demo.Repository.EmployeeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {
    @InjectMocks
    private EmployeeService service;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private DepartmentRepository departmentRepository;

    @Test
    public void givenAnEntity_addEmployeeToList_Valid() {
        List<EmployeeEntity> employeeEntityList = new ArrayList<>();
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(1);
        employeeEntity.setName("Mircea");
        employeeEntity.setId(2);
        employeeEntityList.add(employeeEntity);
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setId(1);
        employeeEntity.setDepartmentEntity(departmentEntity);

        when(employeeRepository.findAll()).thenReturn(employeeEntityList);
        List<EmployeeDto> result = service.addEmployees();


        Assert.assertEquals(1, result.size());//verifica daca se baga in lista employeeEntity pe pozitie
        Assert.assertEquals(employeeEntity.getId(), result.get(0).getId());//verifica dupa id
        Assert.assertEquals(employeeEntity.getName(), result.get(0).getName());//verifica daca in lista se afla name`ul employee
        Assert.assertEquals(employeeEntityList.size(), result.size());//verifica daca se adauga in lista ca numar

    }

    //getEmployeeId
    @Test
    public void giveAnEntity_findById_Valid() {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(1);
        employeeEntity.setName("Mircea");
        employeeEntity.setJob("Baker");
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setId(1);
        employeeEntity.setDepartmentEntity(departmentEntity);


        when(employeeRepository.findById(employeeEntity.getId())).thenReturn(Optional.of(employeeEntity));
        EmployeeDto result = service.getEmployeeId(employeeEntity.getId());


        Assert.assertEquals(employeeEntity.getId(), result.getId());
        Assert.assertEquals(employeeEntity.getName(), result.getName());

    }

    @Test(expected = EmployeeNotFoundException.class)
    public void giveAnEntityWithWrongId_findById_throwException() {



        service.getEmployeeId(1);


    }

    @Test
    public void givenAnEntity_addEmployee_Valid() {

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(1);
        employeeDto.setName("Mircea");
        employeeDto.setJob("Baker");
        employeeDto.setDepartmentId(1);
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setId(2);
        departmentEntity.setName("HR");


        when(departmentRepository.findById(1L)).thenReturn(Optional.of(departmentEntity));
        service.addEmployee(employeeDto);

        verify(employeeRepository).save(any(EmployeeEntity.class));

    }

    @Test(expected = DepartmentNotFoundException.class)
    public void givenAnEntity_addEmployee_ExceptionDepartmentIdNotFound() {
//        when(departmentRepository.findById(1L)).thenThrow(new DepartmentNotFoundException(1));

        service.addEmployee(new EmployeeDto());

    }

    @Test
    public void givenAnEntity_updateEmployee_ExceptSave() {

        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(1);
        employeeEntity.setName("Mircea");
        employeeEntity.setJob("Baker");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employeeEntity));
        service.updateEmployeeById(new EmployeeDto(), 1);

        verify(employeeRepository).save(employeeEntity);

    }

    @Test(expected = EmployeeNotFoundException.class)
    public void getBetterNameUpdate_Exception() {
        when(employeeRepository.findById(1L)).thenThrow(new EmployeeNotFoundException(1));

        service.updateEmployeeById(new EmployeeDto(), 1);
    }


    @Test
    public void deleteGetBetterName() {

        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(1);
        employeeEntity.setName("Mircea");
        employeeEntity.setJob("Baker");


        DepartmentEntity departmentEntity=new DepartmentEntity();
        employeeEntity.setDepartmentEntity(departmentEntity);


        when(employeeRepository.findById(employeeEntity.getId())).thenReturn(Optional.of(employeeEntity));
        service.deleteEmployee(1);

        verify(employeeRepository).deleteById(employeeEntity.getId());

    }

    @Test(expected = EmployeeNotFoundException.class)
    public void deleteTest_Exeption() {
        when(employeeRepository.findById(1L)).thenThrow(new EmployeeNotFoundException(1));

        service.deleteEmployee(1);
    }

}
