package edu.zupevolution.service;

import edu.zupevolution.model.StudyModel;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
    public void testCreatStudy(){
        StudyModel studyModel = new StudyModel();
        when(studyRepository.save(studyModel)).thenReturn(studyModel);
        ResponseEntity<Object> response = studyService.createStudy(studyModel);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Estudo criado.", response.getBody());
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

}