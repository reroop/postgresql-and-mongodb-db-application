package com.dbapplication.services.postgre.ref;

import com.dbapplication.models.postgre.ref.OccupationRef;
import com.dbapplication.repositories.postgre.ref.PostgreRefOccupationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("postgreref")
public class PostgreRefOccupationService {

    @Autowired
    private PostgreRefOccupationRepository occupationRepository;

    public List<OccupationRef> getAllOccupations() {
        return occupationRepository.findAll();
    }

    public OccupationRef getOccupationByOccupationCode(Integer occupationCode) {
        return occupationRepository.findById(occupationCode).orElse(null);
    }

    public OccupationRef addOccupation(OccupationRef occupationRef) {
        return occupationRepository.findById(occupationRef.getOccupation_code()).isPresent()
                ? null
                : occupationRepository.save(occupationRef);
    }
}
