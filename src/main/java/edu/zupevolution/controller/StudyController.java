package edu.zupevolution.controller;

import edu.zupevolution.model.StudyModel;
import edu.zupevolution.service.StudyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zupevolution/study")
public class StudyController {

    @Autowired
    private StudyService studyService;

    @Operation(description = "Cria um novo estudo de um usuário pelo id do seu usuário dono.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estudo criado."),
            @ApiResponse(responseCode = "400", description = "Nenhum Usuário localizado com o id informado."),
            @ApiResponse(responseCode = "400", description = "Quando determinado campo passado não pode ser nulo.")
    }
    )
    @PostMapping("/create/{userId}")
    public ResponseEntity<Object> createStudy(@PathVariable Long userId, @RequestBody StudyModel studyModel) {
        ResponseEntity<Object> createdStudy = studyService.createStudy(userId, studyModel);
        return new ResponseEntity<>(createdStudy, HttpStatus.CREATED);
    }
    @Operation(description = "Deleta um estudo pelo seu id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estudo excluído com sucesso."),
            @ApiResponse(responseCode = "404", description = "Estudo não encontrado.")
    }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteStudy(@PathVariable Long id) {
        ResponseEntity<Object> response = studyService.deleteStudy(id);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }
    @Operation(description = "Atualiza um estudo pelo seu id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o estudo atualizado."),
            @ApiResponse(responseCode = "409", description = "Quando o horário passado já foi marcado anteriomente pelo " +
                    "usuário dono."),
            @ApiResponse(responseCode = "404", description = "Estudo não encontrado.")
    }
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateStudy(@PathVariable Long id, @RequestBody StudyModel updatedStudy) {

        ResponseEntity<Object> response = studyService.updateStudy(id, updatedStudy);

        if (response.getStatusCode() == HttpStatus.OK) {
            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
        } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            return new ResponseEntity<>(response.getBody(), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(response.getBody(), HttpStatus.NOT_FOUND);
        }
    }
    @Operation(description = "Retorna todos os estudos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna os estudos."),
            @ApiResponse(responseCode = "404", description = "Nenhum estudo encontrado.")
    }
    )
    @GetMapping("/getAll")
    public ResponseEntity<Object> getAllStudies() {
        return studyService.getAllStudies();
    }
}
