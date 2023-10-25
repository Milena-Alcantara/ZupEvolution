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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    @Test
    @DisplayName("Deve retornar um HTTP status OK ao tentar deletar um usuário com email válido")
    public void testOneDeleteUser(){
        when(userRepository.findByEmail(userValid.getEmail())).thenReturn(Optional.ofNullable(userValid));
        ResponseEntity response = userService.deleteUser(userValid.getEmail());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuário deletado.", response.getBody());
    }
    @Test
    @DisplayName("Deve retornar um HTTP status CONFLIT ao tentar deletar um usuário com email inválido")
    public void testTwoDeleteUser(){
        when(userRepository.findByEmail(userInvalid.getEmail())).thenReturn(Optional.ofNullable(userInvalid));
        ResponseEntity response = userService.deleteUser(userInvalid.getEmail());

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("E-mail inválido.", response.getBody());
    }
    @Test
    @DisplayName("Deve retornar um HTTP status NOT_FOUND ao tentar deletar um usuário com email inválido")
    public void testThreeDeleteUser(){
        when(userRepository.findByEmail(userValid.getEmail())).thenReturn(null);
        ResponseEntity response = userService.deleteUser(userValid.getEmail());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Usuário não localizado.", response.getBody());
    }

    @Test
    @DisplayName("Deve retornar um HTTP status OK ao tentar localizar um usuário com email válido")
    public void testOneFindUserByEmail(){
        Optional<UserModel> user = Optional.of(new UserModel(1l, "Milena", new Date(20030621),
                "milena@gmail.com", "12345678", null));
        when(userRepository.findByEmail("milena@gmail.com")).thenReturn(user);
        ResponseEntity response = userService.findUserByEmail("milena@gmail.com");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user.get(),response.getBody());
    }
    @Test
    @DisplayName("Deve retornar um HTTP status CONFLICT ao tentar localizar um usuário com email válido")
    public void testTwoFindUserByEmail(){
        when(userRepository.findByEmail(userInvalid.getEmail())).thenReturn(Optional.ofNullable(userInvalid));
        ResponseEntity response = userService.findUserByEmail(userInvalid.getEmail());

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("E-mail inválido.",response.getBody());
    }

    @Test
    @DisplayName("Deve retornar um HTTP status NOT_FOUND ao tentar localizar um usuário com email válido")
    public void testThreeFindUserByEmail(){
        when(userRepository.findByEmail(userValid.getEmail())).thenReturn(null);
        ResponseEntity response = userService.findUserByEmail(userValid.getEmail());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Usuário não localizado.", response.getBody());
    }
    @Test
    @DisplayName("Deve retornar um HTTP status OK quando houver usuários")
    public void testGetAllUsersNotEmpty() {
        List<UserModel> users = new ArrayList<>();
        users.add(new UserModel(1L, "Usuário 1", new Date(), "usuario1@gmail.com", "senha1", null));
        when(userRepository.findAll()).thenReturn(users);

        ResponseEntity<Object> response = userService.getAllStudies();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<UserModel> responseUsers = (List<UserModel>) response.getBody();

        assertEquals(users.size(), responseUsers.size());
        assertEquals(users.get(0).getId(), responseUsers.get(0).getId());
        assertEquals(users.get(0).getName(), responseUsers.get(0).getName());
    }
    @Test
    @DisplayName("Deve retornar um HTTP status NOT_FOUND quando não houver usuários")
    public void testGetAllUsersEmpty() {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        ResponseEntity<Object> response = userService.getAllUsers();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Usuário não localizado.", response.getBody());
    }
    @Test
    @DisplayName("Deve retornar um HTTP status OK ao passar um id e um tipo de acesso válido")
    public void testOneUpdateAccessTypeUserById(){
        Optional<UserModel> user = Optional.of(new UserModel(1l, "Milena", new Date(20030621),
                "milena@gmail.com", "12345678", null));
        when(userRepository.findById(1L)).thenReturn(user);
        AccessTypeModel accessTypeModel = new AccessTypeModel(1l,"Admin");
        ResponseEntity<Object> response = userService.updateAccessTypeUserByID(userValid.getId(),accessTypeModel);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(user.get(),response.getBody());
    }
    @Test
    @DisplayName("Deve retornar um HTTP status BAD_REQUEST quando passar id e tipo de acesso nulos")
    public void testTwoUpdateAccessTypeUserById(){
       when(userRepository.findById(userInvalid.getId())).thenReturn(Optional.ofNullable(userInvalid));
       ResponseEntity response = userService.updateAccessTypeUserByID(userInvalid.getId(),null);

       assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
       assertEquals("Revise os dados informados",response.getBody());
    }

    @Test
    @DisplayName("Deve retornar um HTTP status NOT_FOUND quando passar id e tipo de acesso válido")
    public void testThreeUpdateAccessTypeUserById(){
        when(userRepository.findById(userValid.getId())).thenReturn(null);
        AccessTypeModel accessTypeModel = new AccessTypeModel(1l,"Admin");
        ResponseEntity response = userService.updateAccessTypeUserByID(userValid.getId(),accessTypeModel);

        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertEquals("Usuário não localizado.",response.getBody());
    }
}
