//package com.vibeverse.server.service.impl;
//
//import com.vibeverse.server.dto.request.ViberRequestDto;
//import com.vibeverse.server.dto.response.ViberResponseDto;
//import com.vibeverse.server.mapper.ViberMapper; // Import the mapper
//import com.vibeverse.server.model.Viber;
//import com.vibeverse.server.repository.ViberRepository;
//import com.vibeverse.server.service.ViberService;
//import jakarta.persistence.EntityNotFoundException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder; // Import PasswordEncoder
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//// No longer need Collectors.toList() and manual mapping methods thanks to MapStruct
//
//@Service
//@RequiredArgsConstructor
//@Transactional // Transactional by default for all methods
//public class ViberServiceImpl implements ViberService {
//
//    private final ViberRepository viberRepository; // Renamed repo for clarity
//    private final ViberMapper viberMapper; // Inject the mapper
//    private final PasswordEncoder passwordEncoder; // Inject PasswordEncoder
//
//    @Override
//    public ViberResponseDto createViber(ViberRequestDto req) {
//        // TODO: Add checks for existing username or email before creating
//
//        // Use MapStruct to convert DTO to Entity
//        Viber viber = viberMapper.toEntity(req);
//
//        // --- IMPORTANT: Hash the password before saving ---
//        viber.setPassword(passwordEncoder.encode(req.getPassword()));
//
//        // Save the entity
//        Viber savedViber = viberRepository.save(viber);
//
//        // Use MapStruct to convert the saved entity back to Response DTO
//        return viberMapper.toDto(savedViber);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<ViberResponseDto> getAllVibers() {
//        List<Viber> vibers = viberRepository.findAll();
//        // Use MapStruct to convert list of entities to list of DTOs
//        return viberMapper.toDtoList(vibers);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public ViberResponseDto getViberById(Long id) {
//        Viber viber = viberRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Viber not found with ID: " + id));
//        // Use MapStruct to convert entity to DTO
//        return viberMapper.toDto(viber);
//    }
//
//    @Override
//    public ViberResponseDto updateViber(Long id, ViberRequestDto req) {
//        Viber existingViber = viberRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Viber not found with ID: " + id));
//
//        // TODO: Add checks for username/email uniqueness if they are changed
//        // Example: if (!req.getUsername().equals(existingViber.getUsername()) && viberRepository.existsByUsername(req.getUsername())) { ... }
//
//        // Use MapStruct to update the existing entity from the DTO
//        // This only updates fields defined in the mapper and not ignored
//        viberMapper.updateEntityFromDto(req, existingViber);
//
//        // --- IMPORTANT: Password is NOT updated via this general update method ---
//        // Implement a separate method for password changes that hashes the new password.
//
//        // Save the updated entity
//        Viber updatedViber = viberRepository.save(existingViber);
//
//        // Use MapStruct to convert the updated entity back to Response DTO
//        return viberMapper.toDto(updatedViber);
//    }
//
//    @Override
//    public void deleteViber(Long id) {
//        // Using existsById then deleteById provides a specific "not found" error
//        // Alternatively, just call deleteById and let it throw EmptyResultDataAccessException
//        if (!viberRepository.existsById(id)) {
//            throw new EntityNotFoundException("Viber not found with ID: " + id);
//        }
//        viberRepository.deleteById(id);
//        // If using cascade=ALL + orphanRemoval=true, related entities will be deleted here
//    }
//
//    // Manual mapping method (toDto) removed as MapStruct handles it.
//}