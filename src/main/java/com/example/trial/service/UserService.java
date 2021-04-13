package com.example.trial.service;

import com.example.trial.dto.UserDto;
import com.example.trial.dto.UserPatchDto;
import com.example.trial.model.User;

import java.util.List;

/**
 * Service's interface for operating users
 */
public interface UserService {

    /**
     * finds user by login
     * @param login login of user
     * @return user
     */
    User getByLogin(String login);

    /**
     * saves user to database
     * @param userDto dto representation of user
     * @return {@code true} if new user was created successfully
     */
    boolean saveUser(UserDto userDto);

    /**
     * updates existing user
     * @param login login of existing user
     * @param userPatchDto dto for patching of user
     * @return updated user
     */
    User updateUser(String login, UserPatchDto userPatchDto);

    /**
     * removes user from database if exists
     * @param login login of user
     */
    void deleteUser(String login);

    /**
     * finds all users in database
     * @return {@code List} of users
     */
    List<User> getAll();
}
