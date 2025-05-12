package com.org.test.subscription.service.impl;

import com.org.test.subscription.dto.UserDTO;
import com.org.test.subscription.dto.UserResponseDTO;
import com.org.test.subscription.entity.UsersEntity;
import com.org.test.subscription.exception.NotFoundUserException;
import com.org.test.subscription.repository.UsersRepository;
import com.org.test.subscription.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserResponseDTO addUser(UserDTO user) {
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setName(user.name());
        usersEntity.setDescription(user.description() == null ? "" : user.description());
        var newUser = usersRepository.save(usersEntity);
        log.info("Пользователь добавлен: {}", newUser.getUserId());
        UserResponseDTO userResponseDTO = new UserResponseDTO(newUser.getUserId(), newUser.getName(), newUser.getDescription() == null ? "" : newUser.getDescription());
        return userResponseDTO;
    }

    @Override
    public UserDTO getUser(Long userId) {
        var user = usersRepository.findById(userId);
        return user.map(usersEntity -> new UserDTO(usersEntity.getName(), usersEntity.getDescription())).orElseThrow(
                () -> new NotFoundUserException(String.valueOf(userId))
        );
    }

    @Override
    public void updateUser(Long userId, UserDTO user) {
        var usersEntity = usersRepository.findById(userId).orElseThrow(
                () -> new NotFoundUserException(String.valueOf(userId))
        );
        usersEntity.setName(user.name());
        usersEntity.setDescription(user.description());
        usersRepository.save(usersEntity);
        log.info("Пользователь обновлен: {}", userId);
    }

    @Override
    public void deleteUser(Long userId) {
        if (!usersRepository.existsById(userId)) {
            throw new NotFoundUserException(String.valueOf(userId));
        }
        usersRepository.deleteById(userId);
        log.info("Пользователь удален: {}", userId);
    }
}
