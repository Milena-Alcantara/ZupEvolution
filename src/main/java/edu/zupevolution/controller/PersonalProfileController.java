package edu.zupevolution.controller;

import edu.zupevolution.model.UserModel;
import edu.zupevolution.service.PersonalProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zupevolution/personalprofile")
public class PersonalProfileController {
    @Autowired
    private PersonalProfileService personalProfileService;
    @Operation(description = "Atualiza a senha de um usuário pelo seu id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil do usuário atualizado com sucesso!"),
            @ApiResponse(responseCode = "409", description = "Quando a nova senha passada não é válida."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado com o ID fornecido.")
    }
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateProfessionalProfile(@PathVariable Long id, @RequestBody UserModel updatedUserModel) {
        return personalProfileService.updatePersonalProfile(id, updatedUserModel);
    }
    @Operation(description = "Localiza um usuário pelo seu id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o usuário."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado com o ID fornecido.")
    }
    )
    @GetMapping("/getAll/{id}")
    public ResponseEntity<Object> getProfessionalProfile(@PathVariable Long id) {
        return personalProfileService.getPersonalProfile(id);
    }
}
