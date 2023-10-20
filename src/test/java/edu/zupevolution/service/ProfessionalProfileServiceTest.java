package edu.zupevolution.service;

import edu.zupevolution.model.ProfessionalProfileModel;
import edu.zupevolution.model.UserModel;
import edu.zupevolution.repository.ProfessionalProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfessionalProfileServiceTest {
    @Mock
    private ProfessionalProfileRepository repository;
    @InjectMocks
    private ProfessionalProfileService profileService;
    private UserModel userValid;
    private ProfessionalProfileModel profileModelValid;
    private ProfessionalProfileModel profileModelInvalid;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userValid = new UserModel(1l, "Milena", new Date(20030621), "milena@gmail.com", "12345678",
                null);
        profileModelValid = new ProfessionalProfileModel(1l,userValid,null,null,null,
                null,null);
        profileModelInvalid = new ProfessionalProfileModel();
    }
    @Test
    @DisplayName("Deve retornar um HTTP status CREATED ao passar um perfil profissional com id do usuário válido")
    public void testOneCreateProfessionalProfile(){
        when(repository.save(profileModelValid)).thenReturn(profileModelValid);
        ResponseEntity response = profileService.createProfessionalProfile(profileModelValid);

        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals("Perfil Profional do usuário criado com sucesso.",response.getBody());
    }
    @Test
    @DisplayName("Deve retornar um HTTP status BAD_REQUEST ao passar um perfil profissional com id do usuário válido")
    public void testTwoCreateProfessionalProfile(){
        when(repository.save(profileModelInvalid)).thenReturn(profileModelInvalid);
        ResponseEntity response = profileService.createProfessionalProfile(profileModelInvalid);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals("É necessário associar um usuário ao seu perfil profissional.",response.getBody());
    }
    @Test
    void testGetAllProfessionalProfilesWhenProfilesExist() {
        // Cria uma lista de perfis
        ProfessionalProfileModel profile1 = new ProfessionalProfileModel();
        profile1.setId(1L);
        ProfessionalProfileModel profile2 = new ProfessionalProfileModel();
        profile2.setId(2L);
        List<ProfessionalProfileModel> profiles = Arrays.asList(profile1, profile2);

        // Simula o comportamento do repositório quando perfis existem
        when(repository.findAll()).thenReturn(profiles);

        // Chama o método a ser testado
        ResponseEntity<Object> responseEntity = profileService.getAllProfessionalProfiles();

        // Verifica se a resposta tem o status esperado e contém a lista de perfis
        assert(responseEntity.getStatusCodeValue() == 200);
        assert(responseEntity.getBody() instanceof List);
        List<ProfessionalProfileModel> returnedProfiles = (List<ProfessionalProfileModel>) responseEntity.getBody();
        assert(returnedProfiles.size() == 2);
        assert(returnedProfiles.get(0).getId() == 1L);
        assert(returnedProfiles.get(1).getId() == 2L);

        // Verifica se o método findAll do repositório foi chamado
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetAllProfessionalProfilesWhenNoProfilesExist() {
        // Simula o comportamento do repositório quando não existem perfis
        when(repository.findAll()).thenReturn(Collections.emptyList());

        // Chama o método a ser testado
        ResponseEntity<Object> responseEntity = profileService.getAllProfessionalProfiles();

        // Verifica se a resposta tem o status esperado e contém a mensagem de erro
        assert(responseEntity.getStatusCodeValue() == 404);
        assert(responseEntity.getBody().equals("Nenhum perfil profissional encontrado."));

        // Verifica se o método findAll do repositório foi chamado
        verify(repository, times(1)).findAll();
    }
}