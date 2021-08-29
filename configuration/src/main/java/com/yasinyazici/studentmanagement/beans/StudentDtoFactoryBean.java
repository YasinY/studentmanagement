package com.yasinyazici.studentmanagement.beans;

import com.yasinyazici.studentmanagement.data.factory.IStudentDtoFactory;
import com.yasinyazici.studentmanagement.data.factory.StudentDtoFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentDtoFactoryBean {

    private final IStudentDtoFactory studentDtoFactory = new StudentDtoFactory();

    @Bean
    public IStudentDtoFactory studentDtoFactory() {
        return studentDtoFactory;
    }
}
