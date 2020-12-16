package com.example.demo.Controller;

import com.example.demo.Dto.DepartmentDto;
import com.example.demo.Service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/departments")
    public List<DepartmentDto> addDepartment() {
        return departmentService.addDepartments();
    }

    @GetMapping("/department/{id}")
    public DepartmentDto getDepartmentId(@PathVariable long id) {
        return departmentService.getDepartmentId(id);
    }

    @PostMapping("/department")
    public void addDepartment(@RequestBody DepartmentDto departmentDto) {
        departmentService.addDepartment(departmentDto);
    }

    @PutMapping("/department/{id}")
    public void updateDepartment(@RequestBody DepartmentDto departmentDto, @PathVariable long id) {
        departmentService.updateDepartmentById(departmentDto, id);
    }

    @DeleteMapping("/department/{id}")
    public void deleteDepartment(@PathVariable long id) {
        departmentService.deleteDepartment(id);
    }

}
