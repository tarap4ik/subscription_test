package com.org.test.subscription.controller;

import com.org.test.subscription.dto.UserDTO;
import com.org.test.subscription.dto.UserResponseDTO;
import com.org.test.subscription.exception.NotFoundUserException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.text.MessageFormat;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class UsersControllerTest {

    @Autowired
    private UsersController usersController;
    private static UserDTO userDTO;
    private static UserResponseDTO responseDTO;

    @BeforeAll
    static void setUp() {
        userDTO  = new UserDTO("name", "desc");
    }

    @Test
    @Order(1)
    void addUser() {
        responseDTO = usersController.addUser(userDTO).getBody();
        var result = usersController.getUser(responseDTO.userId());
        assertNotNull(result);
    }

    @Test
    @Order(2)
    void getUser() {
        var result = usersController.getUser(responseDTO.userId());
        assertNotNull(result);
    }

    @Test
    @Order(3)
    void getWrongUser() {
        Exception exception = assertThrows(NotFoundUserException.class, () -> {
            usersController.getUser(responseDTO.userId() + 1);
        });

        String expectedMessage = MessageFormat.format("Пользватель с идентификатором {0} не был найден", responseDTO.userId() + 1);
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @Order(4)
    void updateUser() {
        usersController.updateUser(responseDTO.userId(), userDTO);
        var result = usersController.getUser(responseDTO.userId());
        assertNotNull(result);
        assertEquals(result.getBody().name(),userDTO.name());
    }

    @Test
    @Order(5)
    void deleteUser() {
        usersController.deleteUser(responseDTO.userId());
        Exception exception = assertThrows(NotFoundUserException.class, () -> {
            usersController.getUser(responseDTO.userId());
        });

        String expectedMessage = MessageFormat.format("Пользватель с идентификатором {0} не был найден", responseDTO.userId());
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}