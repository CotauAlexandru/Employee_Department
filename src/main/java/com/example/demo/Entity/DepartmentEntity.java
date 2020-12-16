package com.example.demo.Entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@SequenceGenerator(name="seg_Department",initialValue = 1,allocationSize = 1)
@Table(name = "DEPARTMENT")
public class DepartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seg_Department")
    @Column(name = "DEPARTMENT_ID")
    private long id;
    @Column(name = "DEPARTMENT_NAME")
    private String name;


    @OneToMany(
            mappedBy = "departmentEntity",
            fetch = FetchType.LAZY
    )
    private List<EmployeeEntity> employeeEntityList = new ArrayList<>();


    public DepartmentEntity() {
    }

    public DepartmentEntity(String name) {
        this.name = name;
    }
}




