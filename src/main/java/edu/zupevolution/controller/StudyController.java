package edu.zupevolution.controller;

import edu.zupevolution.model.StudyModel;
import edu.zupevolution.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zupevolution/study")
public class StudyController {

    @Autowired
    private StudyService studyService;

    @PostMapping("/create")
    public ResponseEntity<Object> createStudy(@RequestBody StudyModel studyModel) {
        ResponseEntity<Object> createdStudy = studyService.createStudy(studyModel);
        return new ResponseEntity<>(createdStudy, HttpStatus.CREATED);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteStudy(@PathVariable Long id) {
        ResponseEntity<Object> response = studyService.deleteStudy(id);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }
}
