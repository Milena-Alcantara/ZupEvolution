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

class PersonalProfileServiceTest {

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
}
