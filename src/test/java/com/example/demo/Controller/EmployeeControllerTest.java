package com.example.demo.Controller;

import com.example.demo.Dto.EmployeeDto;
import com.example.demo.Exception.EmployeeNotFoundException;
import com.example.demo.Service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON;




@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {

    private MockMvc mockMvc;
    private String requestJson;
    @InjectMocks
    private EmployeeController controller;
    @Mock
    private EmployeeService service;

    @Before
    public void setUp() throws Exception {
        File file = ResourceUtils.getFile("src/test/resources/employeeDto.json");
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        ObjectMapper mapper = new ObjectMapper();
        EmployeeDto orderRequestDto = mapper.readValue(file, EmployeeDto.class);
        requestJson = mapper.writeValueAsString(orderRequestDto);
    }

    @Test
    public void getEmployees_expectSuccess() throws Exception {
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        EmployeeDto employeeDto=new EmployeeDto();
        employeeDto.setId(1);
        employeeDto.setJob("Baker");
        employeeDto.setName("Mircea");
        employeeDto.setDepartmentId(1);
        employeeDtoList.add(employeeDto);
        when(service.addEmployees()).thenReturn(employeeDtoList);
        mockMvc.perform(get("/employees")
                .contentType(MediaType.asMediaType(APPLICATION_JSON)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType((MediaType.asMediaType(APPLICATION_JSON))))
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].name", Matchers.is(employeeDto.getName())))
                .andExpect(jsonPath("$[0].job",Matchers.is(employeeDto.getJob())))
                .andExpect(jsonPath("$[0].departmentId",Matchers.is(1)))
                .andReturn();


        verify(service,times(1)).addEmployees();        //odata sa fie apelata
        verify(service,atLeastOnce()).addEmployees();                        //same pt 1
        verifyNoMoreInteractions(service);                                   //just once


    }

    @Test
    public void getEmployeeById_expectSuccess() throws Exception {
        EmployeeDto employeeDto=new EmployeeDto();
        employeeDto.setId(1);
        employeeDto.setName("Mircea");
        employeeDto.setJob("Baker");
        employeeDto.setDepartmentId(1);
        when(service.getEmployeeId(1)).thenReturn(employeeDto);
        mockMvc.perform(get("/employee" + "/1")
                .contentType(MediaType.asMediaType(APPLICATION_JSON)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType((MediaType.asMediaType(APPLICATION_JSON))))
                .andExpect(jsonPath("$.id",Matchers.is(1)))
                .andExpect(jsonPath("$.name",Matchers.is(employeeDto.getName())))
                .andExpect(jsonPath("$.job",Matchers.is(employeeDto.getJob())))
                .andExpect(jsonPath("$.departmentId",Matchers.is(1)))
                .andReturn();
        verify(service,times(1)).getEmployeeId(employeeDto.getId());
        verifyNoMoreInteractions(service);
    }
    @Test
    public void getEmployeeById_expectException() throws Exception {
        EmployeeDto employeeDto=new EmployeeDto();
        employeeDto.setId(7);
        when(service.getEmployeeId(1)).thenThrow(new EmployeeNotFoundException(1));
        mockMvc.perform(get("/employee" + "/1")
                .contentType(MediaType.asMediaType(APPLICATION_JSON)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void addNewEmployee_expectSuccess() throws Exception {
        mockMvc.perform(post("/employee")
                .contentType(MediaType.asMediaType(APPLICATION_JSON))
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }


    @Test
    public void updateEmployee_expectSuccess() throws Exception {
        mockMvc.perform(put("/employee" + "/1")
                .contentType(MediaType.asMediaType(APPLICATION_JSON))
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }


    @Test
    public void deleteEmployee_expectSuccess() throws Exception {
        mockMvc.perform(delete("/employee" + "/1")
                .contentType(MediaType.asMediaType(APPLICATION_JSON))
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }


}