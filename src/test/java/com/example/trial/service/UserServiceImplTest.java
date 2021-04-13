package com.example.trial.service;

import com.example.trial.dto.UserDto;
import com.example.trial.dto.UserPatchDto;
import com.example.trial.mapper.UserMapper;
import com.example.trial.model.User;
import com.example.trial.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
@MockBean({UserRepository.class, UserMapper.class})
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserServiceImpl service;


    private UserDto userDto;
    private User user;
    private UserPatchDto userPatchDto;
    private User user2;
    private String login;

    @BeforeEach
    void init() {
        login = "test";
        userDto = new UserDto(login, "Test Testov", LocalDate.of(1999, 12, 12), "male");
        user = new User(login, "Test Testov", LocalDate.of(1999, 12, 12), "male");
        userPatchDto = new UserPatchDto("Test Testik", null, "female");
        user2 = new User(login, "Test Testik", LocalDate.of(1999, 12, 12), "female");
    }

    @Test
    void verifyCorrectUserSaving() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        when(mapper.map(any())).thenReturn(user);

        boolean isCreated = service.saveUser(userDto);

        assertTrue(isCreated);

        verify(userRepository).findById(user.getLogin());
        verify(mapper).map(userDto);
        verify(userRepository).save(user);

        Mockito.verifyNoMoreInteractions(
                userRepository,
                mapper
        );
    }

    @Test
    void CreatingAlreadyExistedUsersAssertFalse() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        boolean isCreated = service.saveUser(userDto);

        assertFalse(isCreated);

        verify(userRepository).findById(user.getLogin());

        Mockito.verifyNoMoreInteractions(
                userRepository,
                mapper
        );
    }


    @Test
    void getExistingUserByLogin() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        User retrievedUser = service.getByLogin(login);

        assertEquals(retrievedUser, user);

        verify(userRepository).findById(login);

        Mockito.verifyNoMoreInteractions(
                userRepository
        );
    }

    @Test
    void throwErrorWhenTryingToGetNonExistingUserByLogin() {
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.getByLogin(login));
        String expectedMessage = "User with login " + login + " not found";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);

        verify(userRepository).findById(login);

        verifyNoMoreInteractions(
                userRepository
        );
    }

    @Test
    void getAllExistingUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user, user2));

        List<User> users = service.getAll();

        assertEquals(user, users.get(0));
        assertEquals(user2, users.get(1));

        verify(userRepository).findAll();

        verifyNoMoreInteractions(
                userRepository
        );
    }

    @Test
    void verifyCorrectUserUpdating() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(mapper.updateUser(any(), any())).thenReturn(user2);
        when(userRepository.save(any())).thenReturn(user2);

        User updatedUser = service.updateUser(login, userPatchDto);
        assertEquals(updatedUser, user2);

        verify(userRepository).findById(login);
        verify(userRepository).save(user2);
        verify(mapper).updateUser(userPatchDto, user);

        verifyNoMoreInteractions(
                userRepository,
                mapper
        );
    }

    @Test
    void throwErrorWhenTryingToUpdateNonExistingUser() {
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.updateUser(login, userPatchDto));

        String expectedMessage = "User with login " + login + " not found";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);

        verify(userRepository).findById(login);

        verifyNoMoreInteractions(
                userRepository,
                mapper
        );
    }

    @Test
    void verifyCorrectDeletingOfExistingUser() {
        when(userRepository.findById(anyString())).thenReturn((Optional.of(user)));

        service.deleteUser(login);

        verify(userRepository).findById(login);
        verify(userRepository).deleteById(login);

        verifyNoMoreInteractions(
                userRepository
        );
    }

    @Test
    void throwErrorWhenTryingToDeleteNonExistingUser() {
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.deleteUser(login));
        String expectedMessage = "User with login " + login + " not found";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);

        verify(userRepository).findById(login);

        verifyNoMoreInteractions(
                userRepository
        );

    }

}