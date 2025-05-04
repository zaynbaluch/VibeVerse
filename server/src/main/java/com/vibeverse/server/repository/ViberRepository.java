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
    Optional<Viber> findByUsername(String username);

    Optional<Viber> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query("SELECT v FROM Viber v WHERE LOWER(v.firstName) LIKE LOWER(concat('%', :query, '%')) OR LOWER(v.lastName) LIKE LOWER(concat('%', :query, '%'))")
    List<Viber> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            @Param("query") String firstNameQuery,
            @Param("query") String lastNameQuery
    );

    @Query("SELECT v FROM Viber v WHERE v.profilePictureUrl IS NOT NULL AND v.bio IS NOT NULL")
    List<Viber> findWithCompleteProfiles();

    @Query("SELECT v FROM Viber v WHERE v.dateOfBirth BETWEEN :start AND :end")
    List<Viber> findByBirthDateBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);
}