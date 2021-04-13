package com.example.trial.rest;


import com.example.trial.dto.UserDto;
import com.example.trial.dto.UserPatchDto;
import com.example.trial.model.User;
import com.example.trial.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * contains methods for operating with users
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserRestController {

    private final UserService userService;

    /**
     *  finds user by login
     * @param login login of User
     * @return user found by login, status OK
     */
    @GetMapping(value = "/get/{login}")
    public ResponseEntity<User> getUser(@PathVariable String login) {
        User user = userService.getByLogin(login);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * creates new user
     * @param userDto dto of user's entity
     * @return status {@code CREATED} if successful, else return {@code BAD_REQUEST}
     */
    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<User> saveUser(@RequestBody UserDto userDto) {
            boolean isCreated = userService.saveUser(userDto);
            if (!isCreated) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * updates user with given fields
     * @param login login of user
     * @param userPatchDto DTO for updating the user's entity
     * @return user that was updated and status OK
     */
    @PutMapping(value = "/update/{login}")
    public ResponseEntity<User> updateUser(@PathVariable String login, @RequestBody UserPatchDto userPatchDto) {
        User updatedUser = userService.updateUser(login, userPatchDto);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    /**
     * removes user by login
     * @param login login of user
     */
    @DeleteMapping(value = "/delete/{login}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String login) {
        userService.deleteUser(login);
    }

    /**
     * finds all users
     * @return {@code List} of all users if it's not empty
     */
    @GetMapping(value = "/get")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAll();

        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
