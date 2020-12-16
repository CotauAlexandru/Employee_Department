package com.example.demo.Dto;

import com.example.demo.TestDtoUtility;
import org.junit.Test;

import java.io.IOException;

public class DepartmentDtoTest {

    @Test
    public void serialization() throws IOException {
        TestDtoUtility.assertSerialization("departmentDto.json", DepartmentDto.class);

    }
}
