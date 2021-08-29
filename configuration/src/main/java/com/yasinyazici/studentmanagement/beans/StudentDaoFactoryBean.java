package com.yasinyazici.studentmanagement.beans;

import com.yasinyazici.studentmanagement.data.factory.IStudentDaoFactory;
import com.yasinyazici.studentmanagement.data.factory.StudentDaoFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentDaoFactoryBean {

    private final IStudentDaoFactory studentDaoFactory = new StudentDaoFactory();

    @Bean
    public IStudentDaoFactory studentDaoFactory() {
        return studentDaoFactory;
    }
}
