package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDto {
    private long id;
    private String name;
    private String job;
    private long departmentId;

}
