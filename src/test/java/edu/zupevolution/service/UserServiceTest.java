package edu.zupevolution.service;

import edu.zupevolution.model.AccessTypeModel;
import edu.zupevolution.model.UserModel;
import edu.zupevolution.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    private UserModel userValid;
    private UserModel userInvalid;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        userValid = new UserModel(1l, "Milena", new Date(20030621), "milena@gmail.com", "12345678",
                null);
        userInvalid = new UserModel();
    }

    @Test
    @DisplayName("Dever retornar um HTTP status CREATED ao criar um usuário válido")
    public void testOneCreatUser() {
        when(userRepository.save(userValid)).thenReturn(userValid);
        ResponseEntity response = userService.createUser(userValid);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Usuário salvo com sucesso.", response.getBody());
    }

    @Test
    @DisplayName("Dever retornar um HTTP status CONFLIT ao criar um usuário inválido")
    public void testTwoCreatUser() {
        when(userRepository.save(userInvalid)).thenReturn(userInvalid);
        ResponseEntity response = userService.createUser(userInvalid);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Verifique os dados informados.", response.getBody());
    }


}
