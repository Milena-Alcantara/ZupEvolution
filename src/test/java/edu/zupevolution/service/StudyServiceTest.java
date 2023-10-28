package edu.zupevolution.service;

import edu.zupevolution.model.StudyModel;
import edu.zupevolution.model.TimelineModel;
import edu.zupevolution.model.UserModel;
import edu.zupevolution.repository.StudyRepository;
import edu.zupevolution.repository.TimelineRepository;
import edu.zupevolution.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StudyServiceTest {

    @Mock
    private StudyRepository studyRepository;
    @InjectMocks
    private StudyService studyService;
    @Mock
    private TimelineRepository timelineRepository;
    @Mock
    private TimelineService timelineService;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("deve retornar status created")
    public void testCreatStudy() {
        Long userId = 1L;
        UserModel user = new UserModel();
        user.setId(userId);
        StudyModel studyModel = new StudyModel();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(timelineService.isTimelineConflict(any(TimelineModel.class))).thenReturn(Collections.emptyList());

        studyModel.setContent("Conteúdo de Teste");
        studyModel.setDeadline(new Date());
        studyModel.setSkills(Collections.singletonList("Habilidade de Teste"));
        studyModel.setGoal("Meta de Teste");
        studyModel.setTimeline(new TimelineModel());
        studyModel.setStatus(true);


        ResponseEntity<Object> response = studyService.createStudy(userId, studyModel);

        Mockito.verify(studyRepository).save(studyModel);

        ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.CREATED).body("Estudo criado.");
        assertEquals(expectedResponse, response);

    }

    @Test
    @DisplayName("deve retornar usuário não encontrado")
    public void testCreateStudyWithMissingUser() {
        Long userId = 1L;
        StudyModel studyModel = new StudyModel();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = studyService.createStudy(userId, studyModel);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Usuário com ID 1 não encontrado.", response.getBody());

        verify(studyRepository, never()).save(any(StudyModel.class));
        verify(timelineRepository, never()).save(any(TimelineModel.class));
    }


    @Test
    @DisplayName("deve retornar BAD_REQUEST se content for nulo")
    public void testCreateStudyContentNull() {
        Long userId = 1L;
        UserModel user = new UserModel();
        user.setId(userId);
        StudyModel studyModel = new StudyModel();
        studyModel.setDeadline(new Date());
        studyModel.setSkills(Collections.singletonList("Habilidade de Teste"));
        studyModel.setGoal("Meta de Teste");
        studyModel.setTimeline(new TimelineModel());
        studyModel.setStatus(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        ResponseEntity<Object> response = studyService.createStudy(userId, studyModel);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("O campo 'content' não pode ser nulo.", response.getBody());
    }

    @Test
    @DisplayName("deve retornar BAD_REQUEST se deadline for nulo")
    public void testCreateStudyDeadlineNull() {
        Long userId = 1L;
        UserModel user = new UserModel();
        user.setId(userId);
        StudyModel studyModel = new StudyModel();
        studyModel.setContent("Conteúdo de Teste");
        studyModel.setSkills(Collections.singletonList("Habilidade de Teste"));
        studyModel.setGoal("Meta de Teste");
        studyModel.setTimeline(new TimelineModel());
        studyModel.setStatus(true);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<Object> response = studyService.createStudy(userId, studyModel);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("O campo 'deadline' não pode ser nulo.", response.getBody());
    }

    @Test
    @DisplayName("deve retornar BAD_REQUEST se skills forem nulas ou vazias")
    public void testCreateStudySkillsNullOrEmpty() {
        Long userId = 1L;
        UserModel user = new UserModel();
        user.setId(userId);
        StudyModel studyModel = new StudyModel();
        studyModel.setContent("Conteúdo de Teste");
        studyModel.setDeadline(new Date());
        studyModel.setSkills(new ArrayList<>());
        studyModel.setGoal("Meta de Teste");
        studyModel.setTimeline(new TimelineModel());
        studyModel.setStatus(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        ResponseEntity<Object> response = studyService.createStudy(userId, studyModel);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("O campo 'skills' não pode ser nulo ou vazio.", response.getBody());
    }

    @Test
    @DisplayName("deve retornar BAD_REQUEST se goal for nulo")
    public void testCreateStudyGoalNull() {
        Long userId = 1L;
        UserModel user = new UserModel();
        user.setId(userId);
        StudyModel studyModel = new StudyModel();
        studyModel.setContent("Conteúdo de Teste");
        studyModel.setDeadline(new Date());
        studyModel.setSkills(Collections.singletonList("Habilidade de Teste"));
        studyModel.setTimeline(new TimelineModel());
        studyModel.setStatus(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        ResponseEntity<Object> response = studyService.createStudy(userId, studyModel);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("O campo 'goal' não pode ser nulo.", response.getBody());
    }

    @Test
    @DisplayName("deve retornar BAD_REQUEST se timeline for nulo")
    public void testCreateStudyTimelineNull() {
        Long userId = 1L;
        UserModel user = new UserModel();
        user.setId(userId);
        StudyModel studyModel = new StudyModel();
        studyModel.setContent("Conteúdo de Teste");
        studyModel.setDeadline(new Date());
        studyModel.setSkills(Collections.singletonList("Habilidade de Teste"));
        studyModel.setGoal("Meta de Teste");
        studyModel.setTimeline(null);
        studyModel.setStatus(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        ResponseEntity<Object> response = studyService.createStudy(userId, studyModel);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("O campo 'timeline' não pode ser nulo.", response.getBody());
    }

    @Test
    @DisplayName("deve retornar BAD_REQUEST se status for nulo")
    public void testCreateStudyStatusNull() {
        Long userId = 1L;
        UserModel user = new UserModel();
        user.setId(userId);
        StudyModel studyModel = new StudyModel();
        studyModel.setContent("Conteúdo de Teste");
        studyModel.setDeadline(new Date());
        studyModel.setSkills(Collections.singletonList("Habilidade de Teste"));
        studyModel.setGoal("Meta de Teste");
        studyModel.setTimeline(new TimelineModel());
        studyModel.setStatus(null);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        ResponseEntity<Object> response = studyService.createStudy(userId, studyModel);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("O campo 'status' não pode ser nulo.", response.getBody());
    }

    @Test
    @DisplayName("deve retornar NO_CONTENT ao excluir estudo existente")
    public void testDeleteStudyExisting() {
        Long existingStudyId = 1L;
        StudyModel existingStudy = new StudyModel();
        existingStudy.setId(existingStudyId);

        Mockito.when(studyRepository.findById(existingStudyId)).thenReturn(Optional.of(existingStudy));
        ResponseEntity<Object> response = studyService.deleteStudy(existingStudyId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("deve retornar NOT_FOUND ao tentar excluir estudo inexistente")
    public void testDeleteStudyNonExisting() {
        Long nonExistingStudyId = 2L;
        Mockito.when(studyRepository.findById(nonExistingStudyId)).thenReturn(Optional.empty());
        ResponseEntity<Object> response = studyService.deleteStudy(nonExistingStudyId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("deve atualizar estudo existente")
    public void testUpdateStudyExisting() {
        Long existingStudyId = 1L;

        UserModel user = new UserModel();
        user.setId(1L);

        StudyModel existingStudy = new StudyModel();
        existingStudy.setId(existingStudyId);
        existingStudy.setContent("Conteúdo Antigo");
        existingStudy.setDeadline(new Date());
        existingStudy.setSkills(new ArrayList<>());
        existingStudy.setGoal("Meta Antiga");
        existingStudy.setTimeline(new TimelineModel());
        existingStudy.setUserModel(user);
        existingStudy.setStatus(false);

        StudyModel updatedStudy = new StudyModel();
        updatedStudy.setContent("Novo Conteúdo");
        updatedStudy.setDeadline(new Date());
        updatedStudy.setSkills(new ArrayList<>());
        updatedStudy.setGoal("Nova Meta");
        updatedStudy.setTimeline(new TimelineModel());
        updatedStudy.setStatus(true);

        Mockito.when(studyRepository.findById(existingStudyId)).thenReturn(Optional.of(existingStudy));
        Mockito.when(studyRepository.save(existingStudy)).thenReturn(existingStudy);

        ResponseEntity<Object> response = studyService.updateStudy(existingStudyId, updatedStudy);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        StudyModel returnedStudy = (StudyModel) response.getBody();
        assertEquals(updatedStudy.getContent(), returnedStudy.getContent());
        assertEquals(updatedStudy.getDeadline(), returnedStudy.getDeadline());
        assertEquals(updatedStudy.getSkills(), returnedStudy.getSkills());
        assertEquals(updatedStudy.getGoal(), returnedStudy.getGoal());
        assertEquals(updatedStudy.getTimeline(), returnedStudy.getTimeline());
        assertEquals(updatedStudy.getStatus(), returnedStudy.getStatus());
    }

    @Test
    @DisplayName("deve retornar NOT_FOUND ao tentar atualizar estudo inexistente")
    public void testUpdateStudyNonExisting() {
        Long nonExistingStudyId = 2L;
        StudyModel updatedStudy = new StudyModel();

        Mockito.when(studyRepository.findById(nonExistingStudyId)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = studyService.updateStudy(nonExistingStudyId, updatedStudy);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("deve retornar a lista de estudos existentes")
    public void testGetAllStudies() {
        List<StudyModel> existingStudies = new ArrayList<>();
        existingStudies.add(new StudyModel());
        existingStudies.add(new StudyModel());

        when(studyRepository.findAll()).thenReturn(existingStudies);

        ResponseEntity<Object> response = studyService.getAllStudies();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingStudies.size(), ((List<StudyModel>) response.getBody()).size());
    }

    @Test
    @DisplayName("Deve retornar um HTTP status NOT_FOUND quando não houver estudos")
    public void testGetAllStudiesEmpty() {
        when(studyRepository.findAll()).thenReturn(new ArrayList<>());
        ResponseEntity<Object> response = studyService.getAllStudies();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Estudo não encontrado.", response.getBody());
    }

    @Test
    @DisplayName("Deve deletar estudos e skills")
    public void testDeleteStudiesAndSkills() {
        Long userId = 1L;

        List<StudyModel> studies = new ArrayList<>();
        StudyModel study1 = new StudyModel();
        study1.setId(1L);
        studies.add(study1);

        StudyModel study2 = new StudyModel();
        study2.setId(2L);
        studies.add(study2);

        Mockito.when(studyRepository.findAllStudies(userId)).thenReturn(studies);

        studyService.deleteStudiesAndSkills(userId);

        Mockito.verify(studyRepository, times(1)).deleteSkillsByStudyId(1L);
        Mockito.verify(studyRepository, times(1)).deleteById(1L);
        Mockito.verify(studyRepository, times(1)).deleteSkillsByStudyId(2L);
        Mockito.verify(studyRepository, times(1)).deleteById(2L);
    }

}