package com.dbapplication.services.postgre.jsonb.common;

import com.dbapplication.models.postgre.jsonb.common.OccupationPostgreJsonCommon;
import com.dbapplication.models.postgre.traditional.Occupation;
import com.dbapplication.repositories.postgre.jsonb.common.PostgreJsonCommonOccupationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.dbapplication.utils.postgre.PostgreObjectConverter.convertOccupationPostgreJsonCommonListToOccupationList;
import static com.dbapplication.utils.postgre.PostgreObjectConverter.convertOccupationPostgreJsonCommonToOccupation;

@Service
@Profile({"postgreref", "postgreemb"})
public class PostgreJsonCommonOccupationService {

    @Autowired
    private PostgreJsonCommonOccupationRepository occupationRepository;

    public List<Occupation> getAllOccupations() {
        return convertOccupationPostgreJsonCommonListToOccupationList(occupationRepository.findAll());
    }

    public Occupation getOccupationByOccupationCode(Integer occupationCode) {
        OccupationPostgreJsonCommon occupationPostgreJsonCommon = occupationRepository.findById(occupationCode).orElse(null);
        return occupationPostgreJsonCommon == null
                ? null
                : convertOccupationPostgreJsonCommonToOccupation(occupationPostgreJsonCommon);
    }

    public OccupationPostgreJsonCommon addOccupation(OccupationPostgreJsonCommon occupationPostgreJsonCommon) throws Throwable {
        if (occupationRepository.findById(occupationPostgreJsonCommon.getOccupation_code()).isPresent()) {
            throw new Exception(new Throwable("occupation with code " + occupationPostgreJsonCommon.getOccupation_code() + " is already present!"));
        }
        try {
            return occupationRepository.save(occupationPostgreJsonCommon);
        } catch (Exception e) {
            throw new Exception(new Throwable(e.getMessage()));
        }
    }
}
