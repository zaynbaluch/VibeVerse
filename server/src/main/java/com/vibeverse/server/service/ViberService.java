//package com.vibeverse.server.service;
//
//import com.vibeverse.server.dto.request.ViberRequestDto;
//import com.vibeverse.server.dto.response.ViberResponseDto;
//
//import java.util.List;
//
//public interface ViberService {
//    ViberResponseDto createViber(ViberRequestDto request);
//    List<ViberResponseDto> getAllVibers();
//    ViberResponseDto getViberById(Long id);
//    ViberResponseDto updateViber(Long id, ViberRequestDto request);
//    void deleteViber(Long id);
//
//    // Consider adding methods for finding by username/email, password change, etc.
//    // Optional: ViberResponseDto findByUsername(String username);
//    // Optional: ViberResponseDto findByEmail(String email);
//    // void changePassword(Long id, String oldPassword, String newPassword);
//}