package com.example.demo.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "EMPLOYEE")
@SequenceGenerator(name="seg_Employee",initialValue = 1,allocationSize = 1)
@Entity
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seg_Employee")
    @Column(name = "EMPLOYEE_ID")
    private long id;
    @Column(name = "EMPLOYEE_NAME")
    private String name;
    @Column(name = "EMPLOYEE_JOB")
    private String job;


    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    private DepartmentEntity departmentEntity;

}


