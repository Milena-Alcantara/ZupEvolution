package edu.zupevolution.service;

import edu.zupevolution.model.StudyModel;
import edu.zupevolution.model.UserModel;
import edu.zupevolution.repository.StudyRepository;
import edu.zupevolution.repository.UserRepository;
import edu.zupevolution.util.TimelineUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StudyService {
    @Autowired
    private StudyRepository studyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    TimelineUtils timelineUtils;

    public ResponseEntity<Object> createStudy(Long userId, StudyModel studyModel) {
        UserModel user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário com ID " + userId + " não encontrado.");
        }
        studyModel.setUserModel(user);

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
        if (!timelineUtils.isTimelineValid(studyModel.getTimeline())) {
            return new ResponseEntity<>("Horário indisponível na timeline.", HttpStatus.BAD_REQUEST);
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

    public ResponseEntity<Object> getAllStudies() {
        List<StudyModel> studies = studyRepository.findAll();
        if (studies.isEmpty()) {
            return new ResponseEntity<>("Estudo não encontrado.", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(studies, HttpStatus.OK);
        }
    }
    @Transactional
    public void deleteStudiesAndSkills(@Param("id_user") Long id_user){
        List<StudyModel> studies = studyRepository.findAllStudies(id_user);
        for (StudyModel study: studies) {
            studyRepository.deleteSkillsByStudyId(study.getId());
            studyRepository.deleteById(study.getId());

        }
    }
}
