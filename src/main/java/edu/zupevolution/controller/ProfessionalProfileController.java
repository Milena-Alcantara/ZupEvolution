package edu.zupevolution.controller;

import edu.zupevolution.DTO.ProfessionalProfileRequestDTO;
import edu.zupevolution.model.ProfessionalProfileModel;
import edu.zupevolution.service.ProfessionalProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zupevolution/professionalprofile")
public class ProfessionalProfileController {
    @Autowired
    private ProfessionalProfileService profileService;
    @Operation(description = "Cria um perfil profissional de um usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Perfil Profissional do usuário criado com sucesso."),
            @ApiResponse(responseCode = "409", description = "Quando se tenta criar um perfil profissional para um " +
                    "usuário que já o possui."),
            @ApiResponse(responseCode = "409", description = "Quando é passado um id de um usuário não existente no" +
                    " banco de dados.")
    }
    )
    @PostMapping("/create")
    public ResponseEntity<Object> createProfessionalProfile(@RequestBody ProfessionalProfileRequestDTO requestDTO){
        return profileService.createProfessionalProfile(requestDTO);
    }
    @Operation(description = "Mostra todos usuários que possuirem uma determinada habilidade passada na busca.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna os perfis."),
            @ApiResponse(responseCode = "404", description = "Quando nenhum usuário possui a skill."),
            @ApiResponse(responseCode = "409", description = "Quando é informada um skill nula.")
    }
    )
    @GetMapping("/getAllUsersForSkill/{skillName}")
    public ResponseEntity<Object> getUsersWithSkill(@PathVariable String skillName){
        return profileService.getUsersWithSkill(skillName);
    }
    @Operation(description = "Mostra todos os perfis profissionais existentes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna os perfis."),
            @ApiResponse(responseCode = "404", description = "Quando nenhum perfil profissional for localizado.")
    }
    )
    @GetMapping("/getall")
    public ResponseEntity<Object> getProfessionalProfile() {
        return profileService.getAllProfessionalProfiles();
    }
    @Operation(description = "Atualiza o perfil profissional de um usuário pelo seu id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil profissional atualizado com sucesso."),
            @ApiResponse(responseCode = "409", description = "Quando é passo o mesmo certificado para um nova hard skill" +
                    "criada pelo usuário."),
            @ApiResponse(responseCode = "404", description = "Perfil profissional não encontrado.")
    }
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateProfessionalProfile(@PathVariable Long id, @RequestBody ProfessionalProfileModel updatedProfileModel) {
        return profileService.updateProfessionalProfile(id, updatedProfileModel);
    }

}
