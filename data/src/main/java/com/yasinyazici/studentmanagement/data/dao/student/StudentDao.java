package com.yasinyazici.studentmanagement.data.dao.student;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@Document(collection = "students")
@ToString
public class StudentDao {

    @Id
    private final String id;

    @Indexed
    private final String name;

    private final String address;

    private final UniversityEnrollment universityEnrollment;

}
