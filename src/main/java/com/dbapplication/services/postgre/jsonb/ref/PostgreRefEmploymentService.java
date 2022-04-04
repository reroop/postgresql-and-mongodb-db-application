package com.dbapplication.services.postgre.jsonb.ref;

import com.dbapplication.models.postgre.jsonb.ref.EmploymentRef;
import com.dbapplication.models.postgre.traditional.Employment;
import com.dbapplication.repositories.postgre.jsonb.ref.PostgreRefEmploymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.dbapplication.utils.mongodb.ValidationChecks.isDateInRange2010to2100;
import static com.dbapplication.utils.mongodb.ValidationChecks.isFirstDateBeforeSecondDate;

@Slf4j
@Service
@Profile("postgreref")
public class PostgreRefEmploymentService {

    @Autowired
    private PostgreRefEmploymentRepository employmentRepository;

    public List<Employment.FrontEmployment> getEmployeeAllEmployments(Long personId) {
        List<EmploymentRef> repoResults = employmentRepository.findAllByPersonId(personId);
        List<Employment.FrontEmployment> results = new ArrayList<>();
        for (EmploymentRef employmentRef: repoResults) {
            Employment.FrontEmployment frontEmployment = new Employment.FrontEmployment(
                    String.valueOf(employmentRef.getPersonId()),
                    employmentRef.getOccupationCode(),
                    employmentRef.getData().getStart_time(),
                    employmentRef.getData().getEnd_time()
            );
            results.add(frontEmployment);
        }
        return results;
    }

    public List<Employment> getAllEmploymentsByOccupationCode(Integer occupationCode) {
        List<EmploymentRef> repoRes = employmentRepository.findAllByOccupationCode(occupationCode);
        List<Employment> result = new ArrayList<>();
        for (EmploymentRef employmentRef: repoRes) {
            Employment employment = new Employment(
                    employmentRef.getPersonId(),
                    employmentRef.getOccupationCode(),
                    employmentRef.getData().getStart_time(),
                    employmentRef.getData().getEnd_time()
            );
            result.add(employment);
        }

        return result;
    }

    public EmploymentRef addEmployment(EmploymentRef employmentRef) {
        //todo: add null check to front and delete this
        if (employmentRef.getData().getStart_time()== null) {
            log.info("employmentref starttime is null, returning null");
            return null;
        }
        if (!isDateInRange2010to2100(employmentRef.getData().getStart_time())) {
            log.info("employmentref starttime not in range 2010-2100");
            return null;
        }

        List<EmploymentRef> employeeEmploymentsForOccupationCode = employmentRepository.findAllByPersonIdAndOccupationCode(employmentRef.getPersonId(), employmentRef.getOccupationCode());
        for (EmploymentRef ref : employeeEmploymentsForOccupationCode) {
            if (ref.getData().getEnd_time()==null) {
                log.info("already in active employment!");
                return null;
            }
        }

        return employmentRepository.save(employmentRef);
    }

    public EmploymentRef endEmployeeActiveEmployment(EmploymentRef employmentRef) {
        if (!isDateInRange2010to2100(employmentRef.getData().getEnd_time())) {
            log.info("employmentref endtime not in range 2010-2100");
            return null;
        }

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

        if (!isFirstDateBeforeSecondDate(editableEmploymentRef.getData().getStart_time() ,employmentRef.getData().getEnd_time())) {
            log.info("employmentref endtime is before start time");
            return null;
        }

        editableEmploymentRef.getData().setEnd_time(employmentRef.getData().getEnd_time());
        return employmentRepository.save(editableEmploymentRef);
    }

    public List<EmploymentRef> endEmployeeAllEmployments(EmploymentRef endEmploymentRef) {
        if (!isDateInRange2010to2100(endEmploymentRef.getData().getEnd_time())) {
            log.info("employmentref endtime not in range 2010-2100");
            return null;
        }

        List<EmploymentRef> employeeAllEmployments = employmentRepository.findAllByPersonId(endEmploymentRef.getPersonId());
        for (EmploymentRef employmentRef : employeeAllEmployments) {
            if (employmentRef.getData().getEnd_time()==null) {
                if (!isFirstDateBeforeSecondDate(employmentRef.getData().getStart_time() ,endEmploymentRef.getData().getEnd_time())) {
                    log.info("employmentref endtime is before start time");
                    return null;
                }
                employmentRef.getData().setEnd_time(endEmploymentRef.getData().getEnd_time());
            }
        }
        return employmentRepository.saveAll(employeeAllEmployments);
    }
}
