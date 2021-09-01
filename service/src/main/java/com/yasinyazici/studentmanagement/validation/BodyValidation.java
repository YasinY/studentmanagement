package com.yasinyazici.studentmanagement.validation;

import com.yasinyazici.studentmanagement.data.constants.FactoryConstants;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public class BodyValidation {

    public boolean invalidBody(Map<String, String> body) {
        return body == null || !body.containsKey("name") || !body.containsKey("address");
    }

    public boolean hasInvalidBody(String name, String address, String universityName) {
        return hasInvalidUniversityName(universityName)
                || hasInvalidStudentName(name)
                || hasInvalidAddress(address);
    }

    public boolean isEligibleStudentBody(@RequestBody Map<String, String> body) {
        return invalidBody(body) && !body.containsKey("universityName");
    }

    public boolean hasInvalidAddress(String address) {
        return address.length() < FactoryConstants.MINIMUM_ADDRESS_LENGTH_REQUIRED;
    }

    public boolean hasInvalidStudentName(String name) {
        return name.length() < FactoryConstants.MINIMUM_NAME_LENGTH_REQUIRED;
    }

    public boolean hasInvalidUniversityName(String universityName) {
        return universityName.length() < FactoryConstants.MINIMUM_UNIVERSITY_NAME_LENGTH_REQUIREMENT;
    }

}
