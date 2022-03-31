package com.dbapplication.services.postgre;

import com.dbapplication.models.postgre.Occupation;
import com.dbapplication.repositories.postgre.PostgreTradOccupationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("postgretrad")
public class PostgreTradOccupationService {

    @Autowired
    private PostgreTradOccupationRepository occupationRepository;

    public List<Occupation> getAllOccupations() {
        return occupationRepository.findAll();
    }

    public Occupation getOccupationByOccupationCode(Integer occupationCode) {
        return occupationRepository.findById(occupationCode).orElse(null);
    }

    public Occupation addOccupation(Occupation newOccupation) {
        return occupationRepository.save(newOccupation);
    }
}