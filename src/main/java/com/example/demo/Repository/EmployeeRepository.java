package com.example.demo.Repository;

import com.example.demo.Entity.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Long> {

}
