package edu.zupevolution.service;

import edu.zupevolution.model.StudyModel;
import edu.zupevolution.repository.StudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudyService {
    @Autowired
    private StudyRepository studyRepository;

    public ResponseEntity<Object> createStudy(StudyModel studyModel) {

        if (studyModel.getContent() == null) {
            return new ResponseEntity<>("O campo 'content' não pode ser nulo.", HttpStatus.BAD_REQUEST);
        }
        if (studyModel.getDeadline() == null) {
            return new ResponseEntity<>("O campo 'deadline' não pode ser nulo.", HttpStatus.BAD_REQUEST);
        }
        if (studyModel.getSkills() == null || studyModel.getSkills().isEmpty()) {
            return new ResponseEntity<>("O campo 'skills' não pode ser nulo ou vazio.", HttpStatus.BAD_REQUEST);
        }
        if (studyModel.getGoal() == null) {
            return new ResponseEntity<>("O campo 'goal' não pode ser nulo.", HttpStatus.BAD_REQUEST);
        }
        if (studyModel.getTimeline() == null) {
            return new ResponseEntity<>("O campo 'timeline' não pode ser nulo.", HttpStatus.BAD_REQUEST);
        }
        if (studyModel.getStatus() == null) {
            return new ResponseEntity<>("O campo 'status' não pode ser nulo.", HttpStatus.BAD_REQUEST);
        }

        studyRepository.save(studyModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("Estudo criado.");
    }

    public ResponseEntity<Object> deleteStudy(Long id) {
        Optional<StudyModel> study = studyRepository.findById(id);

        if (study.isPresent()) {
            studyRepository.deleteById(id);
            return new ResponseEntity<>("Estudo excluído com sucesso.", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Estudo não encontrado.", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> updateStudy(Long id, StudyModel updatedStudy) {
        Optional<StudyModel> existingStudy = studyRepository.findById(id);

        if (existingStudy.isPresent()) {
            StudyModel study = existingStudy.get();

            if (updatedStudy.getContent() != null) {
                study.setContent(updatedStudy.getContent());
            }
            if (updatedStudy.getDeadline() != null) {
                study.setDeadline(updatedStudy.getDeadline());
            }
            if (updatedStudy.getSkills() != null) {
                study.setSkills(updatedStudy.getSkills());
            }
            if (updatedStudy.getGoal() != null) {
                study.setGoal(updatedStudy.getGoal());
            }
            if (updatedStudy.getTimeline() != null) {
                study.setTimeline(updatedStudy.getTimeline());
            }
            if (updatedStudy.getStatus() != null) {
                study.setStatus(updatedStudy.getStatus());
            }

            StudyModel updated = studyRepository.save(study);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Estudo não encontrado.", HttpStatus.NOT_FOUND);
        }
    }

}
