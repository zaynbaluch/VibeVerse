//package com.vibeverse.server.repository;
//
//import com.vibeverse.server.model.Viber;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional; // Import Optional
//
//@Repository
//public interface ViberRepository extends JpaRepository<Viber, Long> {
//
//    // Add custom queries here if needed
//    // Example: Find Viber by username or email
//    Optional<Viber> findByUsername(String username);
//    Optional<Viber> findByEmail(String email);
//
//    // Example: Check if a username or email already exists (useful for validation)
//    boolean existsByUsername(String username);
//    boolean existsByEmail(String email);
//}