package com.vibeverse.server.service;

import com.vibeverse.server.dto.PasswordUpdateDto;
import com.vibeverse.server.dto.ViberDto;
import com.vibeverse.server.dto.ViberUpdateDto;
import com.vibeverse.server.entity.Viber;
import com.vibeverse.server.exception.BadRequestException;
import com.vibeverse.server.exception.ResourceNotFoundException;
import com.vibeverse.server.exception.UnauthorizedException;
import com.vibeverse.server.mapper.ViberMapper;
import com.vibeverse.server.repository.ViberRepository;
import com.vibeverse.server.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ViberService {

    private final ViberRepository viberRepository;
    private final ViberMapper viberMapper;
    private final PasswordEncoder passwordEncoder;



    
    @Transactional(readOnly = true)
    public ViberDto getCurrentViber() {
        Viber viber = getAuthenticatedViber();
        return viberMapper.toDto(viber);
    }
    
    @Transactional(readOnly = true)
    public ViberDto getViberById(Long id) {
        Viber viber = viberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Viber", "id", id));
        
        return viberMapper.toDto(viber);
    }
    
    @Transactional(readOnly = true)
    public ViberDto getViberByUsername(String username) {
        Viber viber = viberRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Viber", "username", username));
        
        return viberMapper.toDto(viber);
    }
    
    @Transactional(readOnly = true)
    public List<ViberDto> searchVibers(String query) {
        // Simple implementation - can be enhanced with more sophisticated search
        List<Viber> vibers = viberRepository.findAll().stream()
                .filter(v -> v.getUsername().toLowerCase().contains(query.toLowerCase()) ||
                        v.getFirstName().toLowerCase().contains(query.toLowerCase()) ||
                        v.getLastName().toLowerCase().contains(query.toLowerCase()))
                .toList();
        
        return viberMapper.toDtoList(vibers);
    }
    
    @Transactional
    public ViberDto updateViber(ViberUpdateDto viberUpdateDto) {
        Viber viber = getAuthenticatedViber();
        
        // Check if email is being changed and is already in use
        if (viberUpdateDto.getEmail() != null && 
                !viberUpdateDto.getEmail().equals(viber.getEmail()) && 
                viberRepository.existsByEmail(viberUpdateDto.getEmail())) {
            throw new BadRequestException("Email is already in use");
        }
        
        viberMapper.updateEntity(viberUpdateDto, viber);
        viberRepository.save(viber);
        
        return viberMapper.toDto(viber);
    }
    
    @Transactional
    public void updatePassword(PasswordUpdateDto passwordUpdateDto) {
        Viber viber = getAuthenticatedViber();
        
        // Verify current password
        if (!passwordEncoder.matches(passwordUpdateDto.getCurrentPassword(), viber.getPasswordHash())) {
            throw new BadRequestException("Current password is incorrect");
        }
        
        // Update password
        viber.setPasswordHash(passwordEncoder.encode(passwordUpdateDto.getNewPassword()));
        viberRepository.save(viber);
    }
    
    @Transactional
    public void deleteViber() {
        Viber viber = getAuthenticatedViber();
        viberRepository.delete(viber);
    }
    
    // Helper method to get the authenticated viber
    public Viber getAuthenticatedViber() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if (!(principal instanceof CustomUserDetails)) {
            throw new UnauthorizedException("User not authenticated");
        }
        
        CustomUserDetails userDetails = (CustomUserDetails) principal;
        return userDetails.getViber();
    }
}
