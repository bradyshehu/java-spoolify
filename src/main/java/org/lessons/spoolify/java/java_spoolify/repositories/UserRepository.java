package org.lessons.spoolify.java.java_spoolify.repositories;

import java.util.Optional;

import org.lessons.spoolify.java.java_spoolify.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    public Optional<User> findByUsername(String username);
    
}