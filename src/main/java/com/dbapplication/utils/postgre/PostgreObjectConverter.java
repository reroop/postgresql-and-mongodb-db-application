package com.dbapplication.utils.postgre;

import com.dbapplication.models.postgre.Employment;

import java.util.ArrayList;
import java.util.List;

public class PostgreObjectConverter {

    public Employment.FrontEmployment convertEmploymentToFrontEmployment(Employment e) {
        Employment.FrontEmployment frontEmployment = new Employment.FrontEmployment(String.valueOf(e.getPersonId()), e.getOccupationCode(), e.getStartTime(), e.getEndTime());
        return frontEmployment;
    }

    public List<Employment.FrontEmployment> convertListOfEmploymentsToListOfFrontEmployments(List<Employment> employmentList) {
        List<Employment.FrontEmployment> frontEmploymentList = new ArrayList<>();
        for (Employment e : employmentList) {
            frontEmploymentList.add(convertEmploymentToFrontEmployment(e));
        }
        return frontEmploymentList;
    }
}
