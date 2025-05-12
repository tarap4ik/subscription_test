package com.org.test.subscription.service;

import com.org.test.subscription.dto.UserDTO;
import com.org.test.subscription.dto.UserResponseDTO;

public interface UsersService {

    UserResponseDTO addUser(UserDTO user);

    UserDTO getUser(Long userId);

    void updateUser(Long userId, UserDTO user);

    void deleteUser(Long userId);

}
