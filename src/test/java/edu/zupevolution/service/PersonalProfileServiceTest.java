package edu.zupevolution.service;

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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class  PersonalProfileServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PersonalProfileService personalProfileService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("deve atualizar o perfil do usuário")
    public void testUpdatePersonalProfile() {
        long userId = 1L;
        UserModel existingUser = new UserModel();
        existingUser.setId(userId);
        existingUser.setPassword("senhaAntiga");

        UserModel updatedUser = new UserModel();
        updatedUser.setId(userId);
        updatedUser.setPassword("senhaAtual");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        ResponseEntity<Object> response = personalProfileService.updatePersonalProfile(userId, updatedUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Perfil do usuário atualizado com sucesso!", response.getBody());
        assertEquals(updatedUser.getPassword(), existingUser.getPassword());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(existingUser);
    }
    @Test
    @DisplayName("deve retornar NOT_FOUND quando o perfil do usuário não é encontrado")
    public void testPutPersonalProfileNotFound() {
        Long userId = 2L;
        UserModel updatedUserModel = new UserModel();
        updatedUserModel.setPassword("newPassword");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = personalProfileService.updatePersonalProfile(userId, updatedUserModel);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Usuário não encontrado com o ID fornecido.", responseEntity.getBody());
    }
    @Test
    @DisplayName("deve retornar BAD_REQUEST  quando o perfil do usuário existe mas a senha não é válida")
    public void testPutPersonalProfileWithPasswordInvalid(){
        Long userId = 2L;
        UserModel updatedUserModel = new UserModel();
        updatedUserModel.setPassword("new");

        when(userRepository.findById(userId)).thenReturn(Optional.of(updatedUserModel));
        ResponseEntity<Object> responseEntity = personalProfileService.updatePersonalProfile(userId, updatedUserModel);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("A senha fornecida não atende aos critérios de segurança.", responseEntity.getBody());
    }

    @Test
    @DisplayName("deve retornar o perfil do usuário")
    public void testGetPersonalProfile() {
        long userId = 1L;
        UserModel existingUser = new UserModel();
        existingUser.setId(userId);
        existingUser.setName("Ana");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        ResponseEntity<Object> response = personalProfileService.getPersonalProfile(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingUser, response.getBody());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("deve retornar NOT_FOUND quando o perfil do usuário não é encontrado")
    public void testGetPersonalProfileNotFound() {
        long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = personalProfileService.getPersonalProfile(userId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Usuário não encontrado com o ID fornecido.", response.getBody());
        verify(userRepository, times(1)).findById(userId);
    }
}
