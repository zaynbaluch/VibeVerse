package com.vibeverse.server.service;

import com.vibeverse.server.dto.request.ViberRequestDto;
import com.vibeverse.server.dto.response.ViberResponseDto;

import java.util.List;

public interface ViberService {
    ViberResponseDto createViber(ViberRequestDto request);
    List<ViberResponseDto> getAllVibers();
    ViberResponseDto getViberById(Long id);
    ViberResponseDto updateViber(Long id, ViberRequestDto request);
    void deleteViber(Long id);
}
