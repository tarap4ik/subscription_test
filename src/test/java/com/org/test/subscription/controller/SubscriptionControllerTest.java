package com.org.test.subscription.controller;

import com.org.test.subscription.dto.SubscriptionDTO;
import com.org.test.subscription.dto.UserDTO;
import com.org.test.subscription.dto.UserResponseDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class SubscriptionControllerTest {

    @Autowired
    private SubscriptionController subscriptionController;
    @Autowired
    private UsersController usersController;
    private static UserResponseDTO responseDTO;
    private static SubscriptionDTO subscriptionDTO;

    @BeforeAll
    static void setUp() {
    }

    @Test
    @Order(1)
    void subscribe() {
        responseDTO = usersController.addUser(new UserDTO("name", "desc")).getBody();
        subscriptionController.subscribe(responseDTO.userId(), "Netflix");
        subscriptionController.subscribe(responseDTO.userId(), "Spotify");
        subscriptionController.subscribe(responseDTO.userId(), "Deezer");
        var result = subscriptionController.getSubscriptions(responseDTO.userId());
        assertNotNull(result);
        assertFalse(result.getBody().isEmpty());
        subscriptionDTO = result.getBody().get(0);
    }

    @Test
    @Order(2)
    void getTopSubscriptions() {
        var result = subscriptionController.getTopSubscriptions();
        assertNotNull(result);
        assertFalse(result.getBody().isEmpty());
    }

    @Test
    @Order(3)
    void unsubscribe() {
        subscriptionController.unsubscribe(responseDTO.userId(), subscriptionDTO.subscriptionId());
        var result = subscriptionController.getSubscriptions(responseDTO.userId());
        assertNotNull(result);
        assertEquals(result.getBody().size(), 2);
    }
}