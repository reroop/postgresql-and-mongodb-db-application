package com.dbapplication.services.postgre.traditional;

import com.dbapplication.models.postgre.traditional.Employment;
import com.dbapplication.repositories.postgre.traditional.PostgreTradEmploymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.dbapplication.utils.postgre.PostgreObjectConverter.convertListOfEmploymentsToListOfFrontEmployments;

@Service
@Profile("postgretrad")
public class PostgreTradEmploymentService {

    @Autowired
    private PostgreTradEmploymentRepository employmentRepository;

    public List<Employment> getAllEmploymentsByOccupationCode(Integer occupationCode) {
        return employmentRepository.findAllByOccupationCode(occupationCode);
    }

    public List<Employment.FrontEmployment> getEmployeeAllEmployments(Long personId) {
        return convertListOfEmploymentsToListOfFrontEmployments(employmentRepository.findAllByPersonId(personId));
    }

    public Employment addEmployment(Employment employment) throws Throwable {
        //check if is already in active employment for that occupation
        List<Employment> employeeEmploymentsInOccupation = employmentRepository.findAllByPersonIdAndOccupationCode(employment.getPersonId(), employment.getOccupationCode());
        for (Employment savedEmployment: employeeEmploymentsInOccupation) {
            if (savedEmployment.getEndTime() == null) {
                throw new Exception(new Throwable("Employee is already actively employed in this occupation!"));
            }
            if (savedEmployment.getStartTime().equals(employment.getStartTime())) {
                throw new Exception(new Throwable("Employment with this occupation and this start time (" + savedEmployment.getStartTime() + ") has already been registered! Change the occupation or start date"));
            }
        }
        try {
            return employmentRepository.save(employment);
        } catch (Exception e) {
            throw new Exception(new Throwable(e.getMessage()));
        }
    }


    public Employment endEmployeeActiveEmployment(Employment employment) throws Throwable {
        Employment.EmploymentCompositeKey key = new Employment.EmploymentCompositeKey(employment.getPersonId(), employment.getOccupationCode(), employment.getStartTime());
        Employment foundEmployment = employmentRepository.findById(key).orElse(null);
        if (foundEmployment==null) {
            return null;
        }
        foundEmployment.setEndTime(employment.getEndTime());
        try {
            return employmentRepository.save(foundEmployment);
        } catch (Exception e) {
            throw new Exception(new Throwable(e.getMessage()));
        }
    }

    public List<Employment> endEmployeeAllEmployments(Employment endEmploymentInfo) throws Throwable {
        List<Employment> employeeAllEmployments = employmentRepository.findAllByPersonId(endEmploymentInfo.getPersonId());
        for (Employment employment : employeeAllEmployments) {
            if (employment.getEndTime()==null) {
                employment.setEndTime(endEmploymentInfo.getEndTime());
            }
        }
        try {
            return employmentRepository.saveAll(employeeAllEmployments);
        } catch (Exception e) {
            throw new Exception(new Throwable(e.getMessage()));
        }
    }
}
