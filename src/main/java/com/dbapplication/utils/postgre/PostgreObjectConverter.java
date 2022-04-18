package com.dbapplication.utils.postgre;

import com.dbapplication.models.postgre.jsonb.common.CountryPostgreJsonCommon;
import com.dbapplication.models.postgre.jsonb.common.EmployeeStatusTypePostgreJsonCommon;
import com.dbapplication.models.postgre.jsonb.common.OccupationPostgreJsonCommon;
import com.dbapplication.models.postgre.jsonb.emb.PersonEmb;
import com.dbapplication.models.postgre.jsonb.ref.PersonRef;
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
        if (Objects.equals(person.getTel_nr(), "")) {
            person.setTel_nr(null);
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
                    person.getAddress(),
                    person.getTel_nr()
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
                personRef.getData().getAddress(),
                personRef.getData().getTel_nr()
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

    public static EmployeeStatusType convertEmployeeStatusTypePostgreJsonCommonToEmployeeStatusType(EmployeeStatusTypePostgreJsonCommon employeeStatusTypePostgreJsonCommon) {
        return new EmployeeStatusType(employeeStatusTypePostgreJsonCommon.getEmployee_status_type_code(), employeeStatusTypePostgreJsonCommon.getData().getName(), employeeStatusTypePostgreJsonCommon.getData().getDescription());
    }

    public static List<EmployeeStatusType> convertEmployeeStatusTypePostgreJsonCommonListToEmployeeStatusTypeList(List<EmployeeStatusTypePostgreJsonCommon> employeeStatusTypePostgreJsonCommonList) {
        List<EmployeeStatusType> result = new ArrayList<>();
        for (EmployeeStatusTypePostgreJsonCommon statusTypeRef : employeeStatusTypePostgreJsonCommonList) {
            result.add(convertEmployeeStatusTypePostgreJsonCommonToEmployeeStatusType(statusTypeRef));
        }
        return result;
    }

    public static EmployeeStatusTypePostgreJsonCommon convertEmployeeStatusTypeToEmployeeStatusTypePostgreJsonCommon(EmployeeStatusType employeeStatusType) {
       return new EmployeeStatusTypePostgreJsonCommon(employeeStatusType.getEmployee_status_type_code(), new EmployeeStatusTypePostgreJsonCommon.EmployeeStatusTypeData(employeeStatusType.getName(), employeeStatusType.getDescription()));
    }

    public static Occupation convertOccupationPostgreJsonCommonToOccupation(OccupationPostgreJsonCommon occupationPostgreJsonCommon) {
        return new Occupation(occupationPostgreJsonCommon.getOccupation_code(), occupationPostgreJsonCommon.getData().getName(), occupationPostgreJsonCommon.getData().getDescription());
    }

    public static List<Occupation> convertOccupationPostgreJsonCommonListToOccupationList(List<OccupationPostgreJsonCommon> occupationPostgreJsonCommonList) {
        List<Occupation> result = new ArrayList<>();
        for (OccupationPostgreJsonCommon occupationPostgreJsonCommon : occupationPostgreJsonCommonList) {
            result.add(convertOccupationPostgreJsonCommonToOccupation(occupationPostgreJsonCommon));
        }
        return result;
    }

    public static OccupationPostgreJsonCommon convertOccupationToOccupationPostgreJsonCommon(Occupation occupation) {
        return new OccupationPostgreJsonCommon(occupation.getOccupation_code(), new OccupationPostgreJsonCommon.OccupationData(occupation.getName(), occupation.getDescription()));
    }

    public static Country convertCountryPostgreJsonCommonToCountry(CountryPostgreJsonCommon countryPostgreJsonCommon) {
        return new Country(countryPostgreJsonCommon.getCountry_code(), countryPostgreJsonCommon.getData().getName());
    }

    public static List<Country> convertCountryPostgreJsonCommonListToCountryList(List<CountryPostgreJsonCommon> countryPostgreJsonCommonList) {
        List<Country> result = new ArrayList<>();
        for (CountryPostgreJsonCommon countryPostgreJsonCommon : countryPostgreJsonCommonList) {
            result.add(new Country(countryPostgreJsonCommon.getCountry_code(), countryPostgreJsonCommon.getData().getName()));
        }
        return result;
    }

    public static CountryPostgreJsonCommon convertCountryToCountryPostgreJsonCommon(Country country) {
        return new CountryPostgreJsonCommon(country.getCountry_code(), new CountryPostgreJsonCommon.CountryData(country.getName()));
    }

    public static PersonEmb convertPersonToPersonEmb(Person person){
        PersonEmb personEmb = new PersonEmb(
                person.get_id(),
                person.getCountry_code(), new PersonEmb.PersonData(
                    person.getNat_id_code(),
                    person.getE_mail(),
                    person.getBirth_date(),
                    person.getReg_time(),
                    person.getGiven_name(),
                    person.getSurname(),
                    person.getAddress(),
                    person.getTel_nr()
                ),
                null
        );
        return personEmb;
    }

    public static Person convertPersonEmbToPerson(PersonEmb personEmb) {
        Person person = new Person(
                personEmb.get_id(),
                personEmb.getData().getNat_id_code(),
                personEmb.getCountry_code(),
                personEmb.getData().getE_mail(),
                personEmb.getData().getBirth_date(),
                personEmb.getData().getReg_time(),
                personEmb.getData().getGiven_name(),
                personEmb.getData().getSurname(),
                personEmb.getData().getAddress(),
                personEmb.getData().getTel_nr()
        );
        return person;
    }

    public static List<Person> convertPersonEmbListToPersonList(List<PersonEmb> personEmbList) {
        List<Person> result = new ArrayList<>();
        for(PersonEmb personEmb : personEmbList) {
            result.add(convertPersonEmbToPerson(personEmb));
        }
        return result;
    }
}
