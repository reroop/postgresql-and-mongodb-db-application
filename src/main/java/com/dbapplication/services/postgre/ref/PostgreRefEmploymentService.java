package com.dbapplication.services.postgre.ref;

import com.dbapplication.models.postgre.ref.EmploymentRef;
import com.dbapplication.models.postgre.traditional.Employment;
import com.dbapplication.repositories.postgre.ref.PostgreRefEmploymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("postgreref")
public class PostgreRefEmploymentService {

    @Autowired
    private PostgreRefEmploymentRepository employmentRepository;

    public List<EmploymentRef> getEmployeeAllEmployments(Long personId) {
        return employmentRepository.findAllByPersonId(personId);
    }

    public List<EmploymentRef> getAllEmploymentsByOccupationCode(Integer occupationCode) {
        return employmentRepository.findAllByOccupationCode(occupationCode);
    }

    public EmploymentRef addEmployment(EmploymentRef employmentRef) {
        if (employmentRef.getData().getStart_time()== null) {
            System.out.println("employmentref starttime is null, returning null");
            return null;
        }
        List<EmploymentRef> employeeEmploymentsForOccupationCode = employmentRepository.findAllByPersonIdAndOccupationCode(employmentRef.getPersonId(), employmentRef.getOccupationCode());
        for (EmploymentRef ref : employeeEmploymentsForOccupationCode) {
            if (ref.getData().getEnd_time()==null) {
                System.out.println("already in active employment!");
                return null;
            }
        }

        return employmentRepository.save(employmentRef);
    }

    public EmploymentRef endEmployeeActiveEmployment(EmploymentRef employmentRef) {
        List<EmploymentRef> employeeEmploymentsForOccupationCode = employmentRepository.findAllByPersonIdAndOccupationCode(employmentRef.getPersonId(), employmentRef.getOccupationCode());

        EmploymentRef editableEmploymentRef = null;
        for (EmploymentRef e : employeeEmploymentsForOccupationCode) {
            if (e.getData().getEnd_time() == null) {
                editableEmploymentRef = e;
                break;
            }
        }
        if (editableEmploymentRef == null) {
            return null;
        }

        editableEmploymentRef.getData().setEnd_time(employmentRef.getData().getEnd_time());
        return employmentRepository.save(editableEmploymentRef);
    }

    public List<EmploymentRef> endEmployeeAllEmployments(EmploymentRef endEmploymentRef) {
        List<EmploymentRef> employeeAllEmployments = employmentRepository.findAllByPersonId(endEmploymentRef.getPersonId());
        for (EmploymentRef employmentRef : employeeAllEmployments) {
            if (employmentRef.getData().getEnd_time()==null) {
                employmentRef.getData().setEnd_time(endEmploymentRef.getData().getEnd_time());
            }
        }
        return employmentRepository.saveAll(employeeAllEmployments);
    }
}
