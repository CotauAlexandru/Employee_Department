package com.example.demo.Dto;

import com.example.demo.TestDtoUtility;
import org.junit.Test;
import java.io.IOException;

public class EmployeeDtoTest {

    @Test
    public void serialization() throws IOException {
        TestDtoUtility.assertSerialization("employeeDto.json", EmployeeDto.class);

    }
}