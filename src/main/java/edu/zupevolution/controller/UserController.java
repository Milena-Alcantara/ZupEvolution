package edu.zupevolution.controller;

import edu.zupevolution.model.AccessTypeModel;
import edu.zupevolution.model.UserModel;
import edu.zupevolution.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zupevolution/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Operation(description = "Salva um novo usuário no banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário salvo com sucesso."),
            @ApiResponse(responseCode = "409", description = "Verifique os dados informados.")
    }
    )
    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody UserModel userModel){
        return userService.createUser(userModel);
    }
    @Operation(description = "Deleta um usuário através do e-mail.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário deletado."),
            @ApiResponse(responseCode = "404", description = "Usuário não localizado."),
            @ApiResponse(responseCode = "409", description = "E-mail inválido."),
    }
    )
    @DeleteMapping("/delete/{email}")
    public ResponseEntity<Object> deleteUser(@PathVariable String email){
        return userService.deleteUser(email);
    }
    @Operation(description = "Busca um usuário através do e-mail.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o usuário."),
            @ApiResponse(responseCode = "404", description = "Usuário não localizado."),
            @ApiResponse(responseCode = "409", description = "E-mail inválido."),
    }
    )
    @GetMapping("/getUser/{email}")
    public ResponseEntity<Object> findUserByEmail(@PathVariable String email){
        return userService.findUserByEmail(email);
    }
    @Operation(description = "Retorna todos os usuários.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna os usuários."),
            @ApiResponse(responseCode = "404", description = "Nenhum Usuário localizado.")
    }
    )
    @GetMapping("/getAll")
    public ResponseEntity<Object> getAllUsers() {
        return userService.getAllUsers();
    }
    @Operation(description = "Atualiza o tipo de acesso de um usuário através do seu id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o usuário atualizado."),
            @ApiResponse(responseCode = "404", description = "Usuário não localizado."),
            @ApiResponse(responseCode = "400", description = "Tipo de acesso novo é inválido (não esta no banco de dados)."),
    }
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateAccessTypeUserByID(@PathVariable Long id, @RequestBody AccessTypeModel accessTypeModel) {
        return userService.updateAccessTypeUserByID(id, accessTypeModel);
    }
}
