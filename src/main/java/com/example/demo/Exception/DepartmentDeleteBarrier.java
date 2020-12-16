package com.example.demo.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DepartmentDeleteBarrier extends RuntimeException {
    public DepartmentDeleteBarrier(long id){
        super("Department with id "+id+ " can not be deleted because it is populated with Employees");
    }

}
