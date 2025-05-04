package com.vibeverse.server.repository;

import com.vibeverse.server.model.Viber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ViberRepository extends JpaRepository<Viber, UUID> {
//
//    // Basic queries
//    Optional<Viber> findByUsername(String username);
//    Optional<Viber> findByEmail(String email);
//    boolean existsByUsername(String username);
//    boolean existsByEmail(String email);
//
//    // Search queries
//    List<Viber> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
//
//    // Filter queries
//    List<Viber> findByDateOfBirthBetween(LocalDate start, LocalDate end);
//    List<Viber> findByCreatedAtAfter(LocalDateTime cutoff);
//
//    // Profile completeness check
//    @Query("SELECT v FROM Viber v WHERE v.profilePictureUrl IS NOT NULL AND v.bio IS NOT NULL")
//    List<Viber> findWithCompleteProfiles();
//
//    // Count badges per viber
//    @Query("SELECT SIZE(v.viberBadges) FROM Viber v WHERE v.id = :viberId")
//    int countBadgesForViber(@Param("viberId") UUID viberId);
}