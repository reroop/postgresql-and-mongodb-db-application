package com.dbapplication.utils;

public class UniversalConstants {

    //-----endpoints----
    //--countries
    public static final String COUNTRIES = "countries";
    public static final String COUNTRIES_COUNTRYCODE = COUNTRIES + "/{countryCode}";
    //occupations
    public static final String OCCUPATIONS = "occupations";
    public static final String OCCUPATIONS_OCCUPATIONCODE = OCCUPATIONS + "/{occupationCode}";
    //employee status types
    public static final String EMPLOYEESTATUSTYPES = "employeeStatusTypes";
    public static final String EMPLOYEESTATUSTYPES_TYPECODE = EMPLOYEESTATUSTYPES + "/{employeeStatusTypeCode}";
    //person status types
    public static final String PERSONSTATUSTYPES = "personStatusTypes";
    public static final String PERSONSTATUSTYPES_TYPECODE = PERSONSTATUSTYPES + "/{personStatusTypeCode}";
    //persons
    public static final String PERSONS = "persons";
    public static final String PERSONS_OBJECTID = PERSONS + "/{objectId}";
    public static final String PERSONS_ID = PERSONS + "/{_id}";
    //employees
    public static final String EMPLOYEES = "employees";
    public static final String EMPLOYEES_PERSONID = EMPLOYEES + "/{personId}";
    //employments
    public static final String EMPLOYMENTS = "employments";
    public static final String EMPLOYMENTS_BY_OCCUPATIONCODE = EMPLOYMENTS + "/occupationCode={occupationCode}";
    public static final String EMPLOYMENTS_BY_EMPLOYEE = EMPLOYMENTS + "/personId={personId}";
    public static final String EMPLOYMENTS_END_EMPLOYMENTS = EMPLOYMENTS + "/endEmployments";
    //------------------

    public static final int EMPLOYEE_STATUS_HAS_FINISHED_WORKING = 5;


    //---error messages---
    public static final String ADD_EMPLOYMENT_WRONG_EMPLOYEE_STATUS = "Current employee status is not suitable for adding new employments!";
}
