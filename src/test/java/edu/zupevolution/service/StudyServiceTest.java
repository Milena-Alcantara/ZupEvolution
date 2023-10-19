package edu.zupevolution.service;

import edu.zupevolution.model.StudyModel;
import edu.zupevolution.model.TimelineModel;
import edu.zupevolution.repository.StudyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;


class StudyServiceTest {

    @Mock
    private StudyRepository studyRepository;
    @InjectMocks
    private StudyService studyService;

    @BeforeEach
    public void setUp (){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("deve retornar status created")
    public void testCreatStudy() {
        StudyModel studyModel = new StudyModel();
        studyModel.setContent("Conteúdo de Teste");
        studyModel.setDeadline(new Date());
        studyModel.setSkills(Collections.singletonList("Habilidade de Teste"));
        studyModel.setGoal("Meta de Teste");
        TimelineModel existingTimeline = new TimelineModel();
        existingTimeline.addStudyTime("Segunda", "08:00");
        existingTimeline.addStudyTime("Terça", "10:00");
        studyModel.setTimeline(existingTimeline);
        studyModel.setStatus(true);
        Mockito.when(studyRepository.save(studyModel)).thenReturn(studyModel);
        ResponseEntity<Object> response = studyService.createStudy(studyModel);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Estudo criado.", response.getBody());
    }

    @Test
    @DisplayName("deve retornar BAD_REQUEST se content for nulo")
    public void testCreateStudyContentNull() {
        StudyModel studyModel = new StudyModel();
        studyModel.setDeadline(new Date());
        studyModel.setSkills(Collections.singletonList("Habilidade de Teste"));
        studyModel.setGoal("Meta de Teste");
        studyModel.setTimeline(new TimelineModel());
        studyModel.setStatus(true);

        ResponseEntity<Object> response = studyService.createStudy(studyModel);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("O campo 'content' não pode ser nulo.", response.getBody());
    }

    @Test
    @DisplayName("deve retornar BAD_REQUEST se deadline for nulo")
    public void testCreateStudyDeadlineNull() {
        StudyModel studyModel = new StudyModel();
        studyModel.setContent("Conteúdo de Teste");
        studyModel.setSkills(Collections.singletonList("Habilidade de Teste"));
        studyModel.setGoal("Meta de Teste");
        studyModel.setTimeline(new TimelineModel());
        studyModel.setStatus(true);

        ResponseEntity<Object> response = studyService.createStudy(studyModel);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("O campo 'deadline' não pode ser nulo.", response.getBody());
    }

    @Test
    @DisplayName("deve retornar BAD_REQUEST se skills forem nulas ou vazias")
    public void testCreateStudySkillsNullOrEmpty() {
        StudyModel studyModel = new StudyModel();
        studyModel.setContent("Conteúdo de Teste");
        studyModel.setDeadline(new Date());
        studyModel.setSkills(new ArrayList<>());
        studyModel.setGoal("Meta de Teste");
        studyModel.setTimeline(new TimelineModel());
        studyModel.setStatus(true);

        ResponseEntity<Object> response = studyService.createStudy(studyModel);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("O campo 'skills' não pode ser nulo ou vazio.", response.getBody());
    }

    @Test
    @DisplayName("deve retornar BAD_REQUEST se goal for nulo")
    public void testCreateStudyGoalNull() {
        StudyModel studyModel = new StudyModel();
        studyModel.setContent("Conteúdo de Teste");
        studyModel.setDeadline(new Date());
        studyModel.setSkills(Collections.singletonList("Habilidade de Teste"));
        studyModel.setTimeline(new TimelineModel());
        studyModel.setStatus(true);

        ResponseEntity<Object> response = studyService.createStudy(studyModel);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("O campo 'goal' não pode ser nulo.", response.getBody());
    }

    @Test
    @DisplayName("deve retornar BAD_REQUEST se timeline for nulo")
    public void testCreateStudyTimelineNull() {
        StudyModel studyModel = new StudyModel();
        studyModel.setContent("Conteúdo de Teste");
        studyModel.setDeadline(new Date());
        studyModel.setSkills(Collections.singletonList("Habilidade de Teste"));
        studyModel.setGoal("Meta de Teste");
        studyModel.setTimeline(null);
        studyModel.setStatus(true);

        ResponseEntity<Object> response = studyService.createStudy(studyModel);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("O campo 'timeline' não pode ser nulo.", response.getBody());
    }

    @Test
    @DisplayName("deve retornar BAD_REQUEST se status for nulo")
    public void testCreateStudyStatusNull() {
        StudyModel studyModel = new StudyModel();
        studyModel.setContent("Conteúdo de Teste");
        studyModel.setDeadline(new Date());
        studyModel.setSkills(Collections.singletonList("Habilidade de Teste"));
        studyModel.setGoal("Meta de Teste");
        studyModel.setTimeline(new TimelineModel());
        studyModel.setStatus(null);

        ResponseEntity<Object> response = studyService.createStudy(studyModel);

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
        StudyModel existingStudy = new StudyModel();
        existingStudy.setId(existingStudyId);
        existingStudy.setContent("Conteúdo Antigo");
        existingStudy.setDeadline(new Date());
        existingStudy.setSkills(new ArrayList<>());
        existingStudy.setGoal("Meta Antiga");
        TimelineModel existingTimeline = new TimelineModel();
        existingTimeline.addStudyTime("Segunda", "08:00");
        existingTimeline.addStudyTime("Terça", "10:00");
        existingStudy.setTimeline(existingTimeline);
        existingStudy.setStatus(false);

        StudyModel updatedStudy = new StudyModel();
        updatedStudy.setContent("Novo Conteúdo");
        updatedStudy.setDeadline(new Date());
        updatedStudy.setSkills(new ArrayList<>());
        updatedStudy.setGoal("Nova Meta");
        TimelineModel updatedTimeline = new TimelineModel();
        updatedTimeline.addStudyTime("Quarta", "14:00");
        updatedTimeline.addStudyTime("Quinta", "16:00");
        updatedStudy.setTimeline(updatedTimeline);
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

}