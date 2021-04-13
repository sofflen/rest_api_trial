package com.example.trial.repository;

import com.example.trial.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for {@link com.example.trial.model.User} class
 */

public interface UserRepository extends JpaRepository<User, String> {
}
