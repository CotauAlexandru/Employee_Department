package com.example.demo.Service;

import com.example.demo.Dto.DepartmentDto;
import com.example.demo.Entity.DepartmentEntity;
import com.example.demo.Entity.EmployeeEntity;
import com.example.demo.Exception.DepartmentDeleteBarrier;
import com.example.demo.Exception.DepartmentNotFoundException;
import com.example.demo.Repository.DepartmentRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentServiceTest {
    @InjectMocks
    private DepartmentService service;
    @Mock
    private DepartmentRepository departmentRepository;

    @Test
    public void givenAnEntity_addDepartmentToList_Valid() {
        DepartmentEntity departmentEntity = new DepartmentEntity();
        List<DepartmentEntity> departmentEntityList = new ArrayList<>();
        departmentEntity.setId(1);
        departmentEntity.setName("HR");
        departmentEntityList.add(departmentEntity);

        when(departmentRepository.findAll()).thenReturn(departmentEntityList);
        List<DepartmentDto> result = service.addDepartments();

        Assert.assertEquals(1, result.size());
        Assert.assertEquals(departmentEntity.getId(), result.get(0).getId());
        Assert.assertEquals(departmentEntity.getName(), result.get(0).getName());
        Assert.assertEquals(departmentEntityList.size(), result.size());

    }

    @Test
    public void givenAnDepartment_findById_valid() {
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setId(1);
        departmentEntity.setName("HR");

        when(departmentRepository.findById(departmentEntity.getId())).thenReturn(Optional.of(departmentEntity));
        DepartmentDto result = service.getDepartmentId(departmentEntity.getId());

        Assert.assertEquals(departmentEntity.getId(), result.getId());
        Assert.assertEquals(departmentEntity.getName(), result.getName());

    }

    @Test(expected = DepartmentNotFoundException.class)
    public void givenAnEntityWithWrongId_findById_throwException() {

        service.getDepartmentId(1);

    }

    @Test
    public void givenAnEntity_addDepartment_Valid() {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(1);
        departmentDto.setName("HR");
        DepartmentEntity departmentEntity=new DepartmentEntity();
        departmentEntity.setId(1);
        departmentEntity.setName("HR");

//        when(departmentRepository.findById(1L)).thenReturn(Optional.of(departmentEntity));
        service.addDepartment(departmentDto);
        verify(departmentRepository).save(any(DepartmentEntity.class));


    }

    @Test
    public void givenAnEntity_updateDepartmentById_Valid() {
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setId(1);
        departmentEntity.setName("HR");

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(departmentEntity));
        service.updateDepartmentById(new DepartmentDto(), 1);

        verify(departmentRepository).save(departmentEntity);
    }

    @Test(expected = DepartmentNotFoundException.class)
    public void givenAnEntity_updateDepartment_ExceptionById() {
        when(departmentRepository.findById(1L)).thenThrow(new DepartmentNotFoundException(1));

        service.updateDepartmentById(new DepartmentDto(), 1);
    }

    @Test
    public void givenAnEntity_deleteDepartment_Valid() {
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setId(1);
        departmentEntity.setName("HR");


        when(departmentRepository.findById(1L)).thenReturn(Optional.of(departmentEntity));
        service.deleteDepartment( 1);

        verify(departmentRepository).deleteById(departmentEntity.getId());


    }

    @Test(expected = DepartmentNotFoundException.class)
    public void givenAnEntity_deleteDepartment_ExceptionDepartmentNotFound() {
        when(departmentRepository.findById(1L)).thenThrow(new DepartmentNotFoundException(1));
        service.deleteDepartment(1);

    }

    @Test(expected = DepartmentDeleteBarrier.class)
    public void givenAnEntity_deleteDepartment_ExceptionDepartmentHasEmployee() {
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setId(1);
        departmentEntity.setName("HR");
        EmployeeEntity employeeEntity = new EmployeeEntity();
        List<EmployeeEntity> employeeEntityList = new ArrayList<>();
        employeeEntity.setId(1);
        employeeEntity.setJob("Baker");
        employeeEntity.setName("Mircea");
        employeeEntityList.add(employeeEntity);
        departmentEntity.setEmployeeEntityList(employeeEntityList);

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(departmentEntity));
        service.deleteDepartment(1);

    }
}