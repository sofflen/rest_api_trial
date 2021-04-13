package com.example.trial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

/**
 * DTO representation of {@link com.example.trial.model.User} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NonNull
    private String login;

    @NonNull
    private String fullName;

    @NonNull
    private LocalDate dateOfBirth;

    @NonNull
    private String gender;

}

