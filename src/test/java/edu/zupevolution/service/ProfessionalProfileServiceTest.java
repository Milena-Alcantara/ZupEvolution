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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class ProfessionalProfileServiceTest {
    @Mock
    private ProfessionalProfileRepository repository;
    @InjectMocks
    private ProfessionalProfileService profileService;
    private UserModel userValid;
    private ProfessionalProfileModel profileModelValid;
    private ProfessionalProfileModel profileModelInvalid;
    private List<Object[]> listUsersValid;
    private List<Object[]> listUsersValidTwo;
    private Object[] arrayUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userValid = new UserModel(1l, "Milena", new Date(20030621), "milena@gmail.com", "12345678",
                null);
        profileModelValid = new ProfessionalProfileModel(1l,userValid,null,null,null,
                null,null);
        profileModelInvalid = new ProfessionalProfileModel();
        arrayUser = new Object[]{userValid};
        listUsersValidTwo = new ArrayList<>();
        listUsersValidTwo.add(arrayUser);
        listUsersValid = new ArrayList<>();
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
    @DisplayName("Deve retornar um HTTP status OK ao passar uma skill válida")
    public void testOneGetUsersWithSkill(){
        when(repository.getUsersWithSkill(anyString())).thenReturn(listUsersValidTwo);
        ResponseEntity response = profileService.getUsersWithSkill("comunicação");

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(listUsersValidTwo,response.getBody());
    }
    @Test
    @DisplayName("Deve retornar um HTTP status BAD_REQUEST ao passar uma skill inválida")
    public void testTwoGetUsersWithSkill(){
        when(repository.getUsersWithSkill(null)).thenReturn(listUsersValid);
        ResponseEntity response = profileService.getUsersWithSkill(null);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals("É necessário informar uma skill válida",response.getBody());

    }
    @Test
    @DisplayName("Deve retornar um HTTP status NOT_FOUND ao passar uma skill válida mas não presente.")
    public void testThreeGetUsersWithSkill(){
        when(repository.getUsersWithSkill("comunicacao")).thenReturn(null);
        ResponseEntity response = profileService.getUsersWithSkill("comunicacao");

        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertEquals("Não foi localizado nenhum usuário com a Skill solicitada.",response.getBody());

    }
}