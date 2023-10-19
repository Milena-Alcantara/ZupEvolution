package edu.zupevolution.service;

import edu.zupevolution.model.StudyModel;
import edu.zupevolution.repository.StudyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
}