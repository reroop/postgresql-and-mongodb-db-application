package com.dbapplication.utils.postgre;

import com.dbapplication.models.postgre.ref.CountryRef;
import com.dbapplication.models.postgre.ref.EmployeeStatusTypeRef;
import com.dbapplication.models.postgre.ref.OccupationRef;
import com.dbapplication.models.postgre.ref.PersonRef;
import com.dbapplication.models.postgre.traditional.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PostgreObjectConverter {

    public static Employment.FrontEmployment convertEmploymentToFrontEmployment(Employment e) {
        return new Employment.FrontEmployment(String.valueOf(e.getPersonId()), e.getOccupationCode(), e.getStartTime(), e.getEndTime());
    }

    public static List<Employment.FrontEmployment> convertListOfEmploymentsToListOfFrontEmployments(List<Employment> employmentList) {
        List<Employment.FrontEmployment> frontEmploymentList = new ArrayList<>();
        for (Employment e : employmentList) {
            frontEmploymentList.add(convertEmploymentToFrontEmployment(e));
        }
        return frontEmploymentList;
    }

    public static Person convertPersonEmptyValuesToNulls(Person person) {
        if (Objects.equals(person.getGiven_name(), "")) {
            person.setGiven_name(null);
        }
        if (Objects.equals(person.getSurname(), "")) {
            person.setSurname(null);
        }
        if (Objects.equals(person.getAddress(), "")) {
            person.setAddress(null);
        }
        return person;
    }

    public static PersonRef convertPersonToPersonRef(Person person){
        //Person convertedPerson = convertPersonEmptyValuesToNulls(person);
        PersonRef personRef = new PersonRef(
                person.get_id(),
                person.getCountry_code(), new PersonRef.PersonData(
                    person.getNat_id_code(),
                    person.getE_mail(),
                    person.getBirth_date(),
                    person.getReg_time(),
                    person.getGiven_name(),
                    person.getSurname(),
                    person.getAddress()
                    )
        );
        return personRef;
    }

    public static Person convertPersonRefToPerson(PersonRef personRef) {
        Person person = new Person(
                personRef.get_id(),
                personRef.getData().getNat_id_code(),
                personRef.getCountry_code(),
                personRef.getData().getE_mail(),
                personRef.getData().getBirth_date(),
                personRef.getData().getReg_time(),
                personRef.getData().getGiven_name(),
                personRef.getData().getSurname(),
                personRef.getData().getAddress()
        );
        return person;
    }

    public static List<Person> convertPersonRefListToPersonList(List<PersonRef> personRefList) {
        List<Person> result = new ArrayList<>();
        for(PersonRef personRef : personRefList) {
            result.add(convertPersonRefToPerson(personRef));
        }
        return result;
    }

    public static EmployeeStatusType convertEmployeeStatusTypeRefToEmployeeStatusType(EmployeeStatusTypeRef employeeStatusTypeRef) {
        return new EmployeeStatusType(employeeStatusTypeRef.getEmployee_status_type_code(), employeeStatusTypeRef.getData().getName(), employeeStatusTypeRef.getData().getDescription());
    }

    public static List<EmployeeStatusType> convertEmployeeStatusTypeRefListToeEmployeeStatusTypeList(List<EmployeeStatusTypeRef> employeeStatusTypeRefList) {
        List<EmployeeStatusType> result = new ArrayList<>();
        for (EmployeeStatusTypeRef statusTypeRef : employeeStatusTypeRefList) {
            result.add(convertEmployeeStatusTypeRefToEmployeeStatusType(statusTypeRef));
        }
        return result;
    }

    public static EmployeeStatusTypeRef convertEmployeeStatusTypeToEmployeeStatusTypeRef(EmployeeStatusType employeeStatusType) {
       return new EmployeeStatusTypeRef(employeeStatusType.getEmployee_status_type_code(), new EmployeeStatusTypeRef.EmployeeStatusTypeData(employeeStatusType.getName(), employeeStatusType.getDescription()));
    }

    public static Occupation convertOccupationRefToOccupation(OccupationRef occupationRef) {
        return new Occupation(occupationRef.getOccupation_code(), occupationRef.getData().getName(), occupationRef.getData().getDescription());
    }

    public static List<Occupation> convertOccupationRefListfToOccupationList(List<OccupationRef> occupationRefList) {
        List<Occupation> result = new ArrayList<>();
        for (OccupationRef occupationRef : occupationRefList) {
            result.add(convertOccupationRefToOccupation(occupationRef));
        }
        return result;
    }

    public static OccupationRef convertOccupationToOccupationRef(Occupation occupation) {
        return new OccupationRef(occupation.getOccupation_code(), new OccupationRef.OccupationData(occupation.getName(), occupation.getDescription()));
    }

    public static Country convertCountryRefToCountry(CountryRef countryRef) {
        return new Country(countryRef.getCountry_code(), countryRef.getData().getName());
    }

    public static List<Country> convertCountryRefListToCountryList(List<CountryRef> countryRefList) {
        List<Country> result = new ArrayList<>();
        for (CountryRef countryRef : countryRefList) {
            result.add(new Country(countryRef.getCountry_code(), countryRef.getData().getName()));
        }
        return result;
    }

    public static CountryRef convertCountryToCountryRef(Country country) {
        return new CountryRef(country.getCountry_code(), new CountryRef.CountryData(country.getName()));
    }
}
