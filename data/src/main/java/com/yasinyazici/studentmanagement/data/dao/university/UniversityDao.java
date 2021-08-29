package com.yasinyazici.studentmanagement.data.dao.university;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.List;

@Getter
@Builder
@Document(collection = "universities")
public class UniversityDao {

    @Indexed(unique=true)
    private final String id;

    private final String universityName;

    private final List<Long> studentEnrollmentIds;

}
