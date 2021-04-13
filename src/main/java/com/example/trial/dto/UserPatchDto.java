package com.example.trial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO representation for {@link com.example.trial.model.User} update
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPatchDto {

    private String fullName;

    private LocalDate dateOfBirth;

    private String gender;

}
