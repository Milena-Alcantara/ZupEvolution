package edu.zupevolution.service;

import edu.zupevolution.model.HardSkillsModel;
import edu.zupevolution.model.ProfessionalProfileModel;
import edu.zupevolution.model.UserModel;
import edu.zupevolution.repository.HardSkillsRepository;
import edu.zupevolution.repository.ProfessionalProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfessionalProfileServiceTest {
    @Mock
    private ProfessionalProfileRepository repository;
    @Mock
    private HardSkillsRepository hardSkillsRepository;
    @InjectMocks
    private ProfessionalProfileService profileService;
    private UserModel userValid;
    private ProfessionalProfileModel profileModelValid;
    private ProfessionalProfileModel profileModelInvalid;
    private List<Object[]> listUsersValid;
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
        listUsersValid = new ArrayList<>();
        listUsersValid.add(arrayUser);
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
    @DisplayName("Deve retornar um HTTP status OK quando passado uma skill válida ")
    public void testOneGetUsersWithSkill(){
        when(repository.getUsersWithSkill(anyString())).thenReturn(listUsersValid);
        ResponseEntity response = profileService.getUsersWithSkill(anyString());

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(listUsersValid,response.getBody());
    }
    @Test
    @DisplayName("Deve retornar um HTTP status NOT_FOUND quando não localizado perfis.")
    public void testTwoGetUsersWithSkill(){
        when(repository.getUsersWithSkill(anyString())).thenReturn(null);
        ResponseEntity response = profileService.getUsersWithSkill(anyString());

        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertEquals("Não foi localizado nenhum usuário com a Skill solicitada.",response.getBody());
    }

    @Test
    @DisplayName("Deve retornar um HTTP status BAD_REQUEST quando passado uma skill inválida ")
    public void testThreeGetUsersWithSkill(){
        when(repository.getUsersWithSkill(null)).thenReturn(listUsersValid);
        ResponseEntity response = profileService.getUsersWithSkill(null);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals("É necessário informar uma skill válida",response.getBody());
    }


    @Test
    @DisplayName("Deve obter todos os perfis profissionais quando existem perfis")
    void testGetAllProfessionalProfilesWhenProfilesExist() {
        ProfessionalProfileModel profile1 = new ProfessionalProfileModel();
        profile1.setId(1L);
        ProfessionalProfileModel profile2 = new ProfessionalProfileModel();
        profile2.setId(2L);
        List<ProfessionalProfileModel> profiles = Arrays.asList(profile1, profile2);

        when(repository.findAll()).thenReturn(profiles);

        ResponseEntity<Object> responseEntity = profileService.getAllProfessionalProfiles();

        assert(responseEntity.getStatusCodeValue() == 200);
        assert(responseEntity.getBody() instanceof List);
        List<ProfessionalProfileModel> returnedProfiles = (List<ProfessionalProfileModel>) responseEntity.getBody();
        assert(returnedProfiles.size() == 2);
        assert(returnedProfiles.get(0).getId() == 1L);
        assert(returnedProfiles.get(1).getId() == 2L);

        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar HTTP status NOT_FOUND quando não existem perfis profissionais")
    void testGetAllProfessionalProfilesWhenNoProfilesExist() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        ResponseEntity<Object> responseEntity = profileService.getAllProfessionalProfiles();

        assert(responseEntity.getStatusCodeValue() == 404);
        assert(responseEntity.getBody().equals("Nenhum perfil profissional encontrado."));

        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve atualizar o perfil profissional quando o perfil existe")
    void testUpdateProfessionalProfileWhenProfileExists() {
        ProfessionalProfileModel existingProfile = new ProfessionalProfileModel();
        existingProfile.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(existingProfile));

        ProfessionalProfileModel updatedProfile = new ProfessionalProfileModel();
        updatedProfile.setSoftSkills(Collections.singletonList("Java, Spring"));
        updatedProfile.setDescription("Experienced Java Developer");
        updatedProfile.setStrongPoints(Collections.singletonList("Problem-solving, Teamwork"));
        updatedProfile.setImprovementPoints(Collections.singletonList("Data Structures, Algorithms"));

        ResponseEntity<Object> responseEntity = profileService.updateProfessionalProfile(1L, updatedProfile);

        assert(responseEntity.getStatusCodeValue() == 200);
        assert(responseEntity.getBody().equals("Perfil profissional atualizado com sucesso."));
        verify(repository, times(1)).save(existingProfile);
    }

    @Test
    @DisplayName("Deve retornar HTTP status NOT_FOUND ao tentar atualizar um perfil inexistente")
    void testUpdateProfessionalProfileWhenProfileDoesNotExist() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ProfessionalProfileModel updatedProfile = new ProfessionalProfileModel();
        updatedProfile.setSoftSkills(Collections.singletonList("Java, Spring"));
        updatedProfile.setDescription("Experienced Java Developer");
        updatedProfile.setStrongPoints(Collections.singletonList("Problem-solving, Teamwork"));
        updatedProfile.setImprovementPoints(Collections.singletonList("Data Structures, Algorithms"));

        ResponseEntity<Object> responseEntity = profileService.updateProfessionalProfile(1L, updatedProfile);

        assert(responseEntity.getStatusCodeValue() == 404);
        assert(responseEntity.getBody().equals("Perfil profissional não encontrado."));
        verify(repository, never()).save(any());
    }


    @Test
    @DisplayName("Deve atualizar o nome da habilidade quando os nomes são válidos")
    void testUpdateHardSkillNameValidSkills() {
        ProfessionalProfileModel professionalProfile = new ProfessionalProfileModel();
        List<HardSkillsModel> hardSkillsList = new ArrayList<>();
        HardSkillsModel hardSkill = new HardSkillsModel();
        hardSkill.setName("Java");
        hardSkillsList.add(hardSkill);
        professionalProfile.setHardSkills(hardSkillsList);

        when(hardSkillsRepository.save(any(HardSkillsModel.class))).thenReturn(hardSkill);

        ResponseEntity<Object> response = profileService.updateHardSkillName(professionalProfile, "Java", "Python");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Python", ((HardSkillsModel) response.getBody()).getName());

        verify(hardSkillsRepository, times(1)).save(any(HardSkillsModel.class));
    }

    @Test
    @DisplayName("Deve retornar HTTP status BAD_REQUEST ao tentar atualizar com nomes de habilidade inválidos")
    void testUpdateHardSkillNameInvalidSkills() {
        ProfessionalProfileModel professionalProfile = new ProfessionalProfileModel();

        ResponseEntity<Object> response = profileService.updateHardSkillName(professionalProfile, null, "Python");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Nomes de habilidade inválidos.", response.getBody());

        verify(hardSkillsRepository, never()).save(any(HardSkillsModel.class));
    }

    @Test
    @DisplayName("Deve retornar HTTP 404 Not Found ao tentar atualizar uma habilidade não existente")
    void testUpdateHardSkillNameNotFound() {
        ProfessionalProfileModel professionalProfile = new ProfessionalProfileModel();
        List<HardSkillsModel> hardSkillsList = new ArrayList<>();
        HardSkillsModel hardSkill = new HardSkillsModel();
        hardSkill.setName("Java");
        hardSkillsList.add(hardSkill);
        professionalProfile.setHardSkills(hardSkillsList);

        when(hardSkillsRepository.save(hardSkill)).thenReturn(hardSkill);
        ResponseEntity<Object> response = profileService.updateHardSkillName(professionalProfile, "Python", "NewSkill");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Habilidade não encontrada.", response.getBody());
    }

    @Test
    @DisplayName("Deve retornar true para nomes de habilidade válidos")
    void testValidateHardSkillNamesValidSkills() {
        boolean isValid = profileService.validateAndUpdateHardSkill("Java", "Python");
        assertTrue(isValid);
    }

    @Test
    @DisplayName("Deve retornar false para nomes de habilidade inválidos")
    void testValidateHardSkillNamesInvalidSkills() {
        boolean isValid = profileService.validateAndUpdateHardSkill(null, "Python");
        assertFalse(isValid);
    }
}