package com.dbapplication.services.postgre.traditional;

import com.dbapplication.models.postgre.traditional.Occupation;
import com.dbapplication.repositories.postgre.traditional.PostgreTradOccupationRepository;
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

    public Occupation addOccupation(Occupation newOccupation) throws Throwable {
        if (occupationRepository.findById(newOccupation.getOccupation_code()).isPresent()) {
            throw new Exception(new Throwable("occupation with code " + newOccupation.getOccupation_code() + " is already present!"));
        }
        try {
            return occupationRepository.save(newOccupation);
        } catch (Exception e) {
            throw new Exception(new Throwable(e.getMessage()));
        }
    }
}
