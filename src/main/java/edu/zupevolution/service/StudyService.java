package edu.zupevolution.service;

import edu.zupevolution.model.StudyModel;
import edu.zupevolution.repository.StudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StudyService {
    @Autowired
    private StudyRepository studyRepository;

    public ResponseEntity<Object> createStudy(StudyModel studyModel) {
        studyRepository.save(studyModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("Estudo criado.");
    }
}
