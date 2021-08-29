package com.yasinyazici.studentmanagement;

import com.yasinyazici.studentmanagement.repository.IStudentRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(
        scanBasePackages = "com.yasinyazici.studentmanagement"
)
@EnableMongoRepositories(basePackageClasses = IStudentRepository.class)
public class StudentManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementApplication.class, args);
    }

}
