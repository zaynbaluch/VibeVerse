package com.vibeverse.server.service;

import com.vibeverse.server.dto.BadgeDto;
import com.vibeverse.server.dto.ViberBadgeDto;
import com.vibeverse.server.entity.Badge;
import com.vibeverse.server.entity.Viber;
import com.vibeverse.server.entity.ViberBadge;
import com.vibeverse.server.exception.BadRequestException;
import com.vibeverse.server.exception.ResourceNotFoundException;
import com.vibeverse.server.mapper.BadgeMapper;
import com.vibeverse.server.mapper.ViberBadgeMapper;
import com.vibeverse.server.repository.BadgeRepository;
import com.vibeverse.server.repository.ViberBadgeRepository;
import com.vibeverse.server.repository.ViberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BadgeService {

    private final BadgeRepository badgeRepository;
    private final ViberBadgeRepository viberBadgeRepository;
    private final ViberRepository viberRepository;
    private final BadgeMapper badgeMapper;
    private final ViberBadgeMapper viberBadgeMapper;
    
    @Transactional(readOnly = true)
    public List<BadgeDto> getAllBadges() {
        List<Badge> badges = badgeRepository.findAll();
        return badgeMapper.toDtoList(badges);
    }
    
    @Transactional(readOnly = true)
    public BadgeDto getBadgeById(Long id) {
        Badge badge = badgeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Badge", "id", id));
        
        return badgeMapper.toDto(badge);
    }
    
    @Transactional(readOnly = true)
    public List<ViberBadgeDto> getViberBadges(Long viberId) {
        List<ViberBadge> viberBadges = viberBadgeRepository.findByViberId(viberId);
        return viberBadgeMapper.toDtoList(viberBadges);
    }
    
    @Transactional
    public BadgeDto createBadge(BadgeDto badgeDto) {
        // Check if badge with same name already exists
        Optional<Badge> existingBadge = badgeRepository.findByName(badgeDto.getName());
        if (existingBadge.isPresent()) {
            throw new BadRequestException("Badge with name '" + badgeDto.getName() + "' already exists");
        }
        
        Badge badge = badgeMapper.toEntity(badgeDto);
        badgeRepository.save(badge);
        
        return badgeMapper.toDto(badge);
    }
    
    @Transactional
    public ViberBadgeDto awardBadge(Long viberId, Long badgeId) {
        Viber viber = viberRepository.findById(viberId)
                .orElseThrow(() -> new ResourceNotFoundException("Viber", "id", viberId));
        
        Badge badge = badgeRepository.findById(badgeId)
                .orElseThrow(() -> new ResourceNotFoundException("Badge", "id", badgeId));
        
        // Check if viber already has this badge
        Optional<ViberBadge> existingViberBadge = viberBadgeRepository.findByViberIdAndBadgeId(viberId, badgeId);
        if (existingViberBadge.isPresent()) {
            throw new BadRequestException("Viber already has this badge");
        }
        
        ViberBadge viberBadge = new ViberBadge();
        viberBadge.setViber(viber);
        viberBadge.setBadge(badge);
        
        viberBadgeRepository.save(viberBadge);
        
        return viberBadgeMapper.toDto(viberBadge);
    }
    
    @Transactional
    public void revokeBadge(Long viberId, Long badgeId) {
        ViberBadge viberBadge = viberBadgeRepository.findByViberIdAndBadgeId(viberId, badgeId)
                .orElseThrow(() -> new ResourceNotFoundException("ViberBadge", "viberId and badgeId", viberId + ", " + badgeId));
        
        viberBadgeRepository.delete(viberBadge);
    }
}
