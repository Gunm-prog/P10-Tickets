package com.emilie.Lib7.Services.contract;


import com.emilie.Lib7.Exceptions.UserAlreadyExistException;
import com.emilie.Lib7.Exceptions.UserNotFoundException;
import com.emilie.Lib7.Models.Dtos.UserDto;

import java.util.List;

public interface UserService {

    UserDto getLoggedUser();

    UserDto findById(Long id) throws UserNotFoundException;


    UserDto save(UserDto userDto) throws UserAlreadyExistException;

    void isNewUserValid(UserDto userDto);

    UserDto findByEmail(String email) throws UserNotFoundException;

    List<UserDto> findAll();

    boolean deleteById(Long id) throws UserNotFoundException;

    UserDto update(UserDto userDto) throws UserNotFoundException;


}
