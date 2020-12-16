package com.example.demo;


import com.example.demo.Entity.DepartmentEntity;
import com.example.demo.Repository.DepartmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


//	@Bean
//	public CommandLineRunner demo(DepartmentRepository departmentRepository) {
//		return (args -> {
//			departmentRepository.save(new DepartmentEntity("Financiar"));
//			departmentRepository.save(new DepartmentEntity("Contabilitate"));
//			departmentRepository.save(new DepartmentEntity("Administrativ"));
//			departmentRepository.save(new DepartmentEntity("Galvanizare"));
//			departmentRepository.save(new DepartmentEntity("Injectie"));
//			departmentRepository.save(new DepartmentEntity("WasteWaterTreatment"));
//
//		});
//	}
}
