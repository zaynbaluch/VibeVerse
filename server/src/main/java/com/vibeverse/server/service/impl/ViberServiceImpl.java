package com.vibeverse.server.service.impl;

import com.vibeverse.server.model.Viber;
import com.vibeverse.server.repository.ViberRepository;
import com.vibeverse.server.service.ViberService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ViberServiceImpl implements ViberService {

    private final ViberRepository viberRepository;

    @Override
    public Viber createViber(Viber viber) {
        return viberRepository.save(viber);
    }

    @Override
    public Viber getViberById(Long id) {
        return viberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Viber not found with id: " + id));
    }

    @Override
    public List<Viber> getAllVibers() {
        return viberRepository.findAll();
    }

    @Override
    public Viber updateViber(Long id, Viber viberDetails) {
        Viber existingViber = getViberById(id);

        existingViber.setUsername(viberDetails.getUsername());
        existingViber.setFirstName(viberDetails.getFirstName());
        existingViber.setLastName(viberDetails.getLastName());
        existingViber.setEmail(viberDetails.getEmail());
        existingViber.setPassword(viberDetails.getPassword());
        existingViber.setBio(viberDetails.getBio());
        existingViber.setProfilePictureUrl(viberDetails.getProfilePictureUrl());
        existingViber.setDateOfBirth(viberDetails.getDateOfBirth());

        return viberRepository.save(existingViber);
    }

    @Override
    public void deleteViber(Long id) {
        if (!viberRepository.existsById(id)) {
            throw new EntityNotFoundException("Viber not found with id: " + id);
        }
        viberRepository.deleteById(id);
    }
}
