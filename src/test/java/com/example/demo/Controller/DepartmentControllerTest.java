package com.example.demo.Controller;

import com.example.demo.Dto.DepartmentDto;
import com.example.demo.Dto.EmployeeDto;
import com.example.demo.Exception.DepartmentNotFoundException;
import com.example.demo.Service.DepartmentService;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
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
public class DepartmentControllerTest {

    private MockMvc mockMvc;
    private String requestJson;

    @InjectMocks
    private DepartmentController controller;
    @Mock
    private DepartmentService service;

    @Before
    public void setUp() throws Exception {
        File file = ResourceUtils.getFile("src/test/resources/departmentDto.json");
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        ObjectMapper mapper = new ObjectMapper();
        EmployeeDto orderRequestDto = mapper.readValue(file, EmployeeDto.class);
        requestJson = mapper.writeValueAsString(orderRequestDto);
    }

    @Test
    public void addDepartments() throws Exception {
        List<DepartmentDto> departmentDtoList = new ArrayList<>();
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(1);
        departmentDto.setName("HR");
        departmentDtoList.add(departmentDto);
        when(service.addDepartments()).thenReturn(departmentDtoList);
        mockMvc.perform(get("/departments")
                .contentType(MediaType.asMediaType(MediaType.APPLICATION_JSON)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType((MediaType.asMediaType(APPLICATION_JSON))))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].name", Matchers.is(departmentDto.getName())))
                .andReturn();

        verify(service, atLeastOnce()).addDepartments();
        verifyNoMoreInteractions(service);

    }

    @Test
    public void getDepartmentById_Valid() throws Exception {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(1);
        departmentDto.setName("HR");
        when(service.getDepartmentId(1)).thenReturn(departmentDto);
        mockMvc.perform(get("/department" + "/1")
                .contentType(MediaType.asMediaType(MediaType.APPLICATION_JSON)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType((MediaType.asMediaType(APPLICATION_JSON))))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is(departmentDto.getName())))
                .andReturn();

        verify(service, times(1)).getDepartmentId(departmentDto.getId());
        verifyNoMoreInteractions(service);
    }

    @Test
    public void getDepartmentById_expectException() throws Exception {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(1);
        when(service.getDepartmentId(1)).thenThrow(new DepartmentNotFoundException(1));
        mockMvc.perform(get("/department" + "/1")
                .contentType(MediaType.asMediaType(APPLICATION_JSON)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void addNewDepartment_Valid() throws Exception {
        mockMvc.perform(post("/department")
                .contentType(MediaType.asMediaType(APPLICATION_JSON))
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    public void updateDepartmentById_Valid() throws Exception {
        mockMvc.perform(put("/department" + "/1")
                .contentType(MediaType.asMediaType(APPLICATION_JSON))
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
    @Test
    public void deleteDepartmentById_Valid() throws Exception {
        mockMvc.perform(delete("/department" + "/1")
                .contentType(MediaType.asMediaType(APPLICATION_JSON))
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

}