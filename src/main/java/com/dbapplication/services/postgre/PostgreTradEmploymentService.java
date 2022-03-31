package com.dbapplication.services.postgre;

import com.dbapplication.models.postgre.Employment;
import com.dbapplication.repositories.postgre.PostgreTradEmploymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("postgretrad")
public class PostgreTradEmploymentService {

    @Autowired
    private PostgreTradEmploymentRepository employmentRepository;

    public List<Employment> getAllEmploymentsByOccupationCode(Integer occupationCode) {
        return employmentRepository.findAllByOccupationCode(occupationCode);
    }

    public List<Employment> getEmployeeAllEmployments(Long personId) {
        return employmentRepository.findAllByPersonId(personId);
    }

    public Employment addEmployment(Employment employment) {
        //check if is already in active employment for that occupation
        List<Employment> savedPersonEmploymentsInOccupation = employmentRepository.findAllByPersonIdAndOccupationCode(employment.getPersonId(), employment.getOccupationCode());
        System.out.println(savedPersonEmploymentsInOccupation);
        for (Employment savedEmployment: savedPersonEmploymentsInOccupation) {
            if (savedEmployment.getEndTime() == null || savedEmployment.getStartTime().equals(employment.getStartTime())) {
                return null;
            }
        }

        return employmentRepository.save(employment);
    }

    public Employment endEmployeeActiveEmployment(Employment employment) {
        Employment.EmploymentCompositeKey key = new Employment.EmploymentCompositeKey(employment.getPersonId(), employment.getOccupationCode(), employment.getStartTime());
        Employment foundEmployment = employmentRepository.findById(key).orElse(null);
        if (foundEmployment==null) {
            return null;
        }
        foundEmployment.setEndTime(employment.getEndTime());
        return employmentRepository.save(foundEmployment);
    }

    public List<Employment> endEmployeeAllEmployments(Employment endEmploymentInfo) {
        List<Employment> employeeAllEmployments = getEmployeeAllEmployments(endEmploymentInfo.getPersonId());
        for (Employment employment : employeeAllEmployments) {
            if (employment.getEndTime()==null) {
                employment.setEndTime(endEmploymentInfo.getEndTime());
            }
        }
        return employmentRepository.saveAll(employeeAllEmployments);
    }



}
