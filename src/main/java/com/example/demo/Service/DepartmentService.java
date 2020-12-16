package com.example.demo.Service;


import com.example.demo.Dto.DepartmentDto;
import com.example.demo.Entity.DepartmentEntity;
import com.example.demo.Exception.DepartmentDeleteBarrier;
import com.example.demo.Exception.DepartmentNotFoundException;
import com.example.demo.Repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    DepartmentEntity departmentEntity = new DepartmentEntity();

    public List<DepartmentDto> addDepartments() {

        return StreamSupport.stream(departmentRepository.findAll().spliterator(), false)
                .map(this::getDepartmentDto)
                .collect(Collectors.toList());

    }

    public void addDepartment(DepartmentDto departmentDto) {
        departmentEntity.setId(departmentDto.getId());
        departmentEntity.setName(departmentDto.getName());
        departmentRepository.save(departmentEntity);
    }

    public void updateDepartmentById(DepartmentDto departmentDto, long id) {
        DepartmentEntity departmentEntity = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));
        departmentEntity.setName(departmentDto.getName());
        departmentRepository.save(departmentEntity);

    }

    public void deleteDepartment(long id) {
        DepartmentDto departmentDtoTemp = getDepartmentId(id);
        DepartmentEntity departmentEntity = departmentRepository.findById(departmentDtoTemp.getId()).orElseThrow(() -> new DepartmentNotFoundException(id));
        if (departmentEntity.getEmployeeEntityList().isEmpty()) {
            departmentRepository.deleteById(departmentDtoTemp.getId());
        } else throw new DepartmentDeleteBarrier(id);
    }

    public   DepartmentDto getDepartmentId(long id) {
        DepartmentEntity departmentEntity = departmentRepository.findById(id).orElseThrow(() -> new DepartmentNotFoundException(id));
        return getDepartmentDto(departmentEntity);
    }

    private DepartmentDto getDepartmentDto(DepartmentEntity departmentEntity) {
        DepartmentDto departmentDto = new DepartmentDto();
        DepartmentDtoFromDepartment(departmentEntity, departmentDto);
        return departmentDto;
    }

    private void DepartmentDtoFromDepartment(DepartmentEntity departmentEntity, DepartmentDto departmentDto) {
        departmentDto.setId(departmentEntity.getId());
        departmentDto.setName(departmentEntity.getName());
    }


}


