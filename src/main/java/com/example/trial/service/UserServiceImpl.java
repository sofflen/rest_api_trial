package com.example.trial.service;

import com.example.trial.dto.UserDto;
import com.example.trial.dto.UserPatchDto;
import com.example.trial.mapper.UserMapper;
import com.example.trial.model.User;
import com.example.trial.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Implementation of service for operating users
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    private static final String USER_NOT_FOUND_FORMAT = "User with login %s not found";

    public UserServiceImpl(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public User getByLogin(String login) {
        return userRepository.findById(login)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND_FORMAT, login)));
    }

    @Override
    @Transactional
    public boolean saveUser(UserDto userDto) {
        if (userRepository.findById(userDto.getLogin()).isPresent()) {
            return false;
        }

        User newUser = mapper.map(userDto);
        userRepository.save(newUser);
        return true;
    }

    @Override
    @Transactional
    public User updateUser(String login, UserPatchDto userPatchDto) {
        User user = userRepository.findById(login).map(u -> mapper.updateUser(userPatchDto, u))
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND_FORMAT, login)));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(String login) {
        userRepository.findById(login)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND_FORMAT, login)));
        userRepository.deleteById(login);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
