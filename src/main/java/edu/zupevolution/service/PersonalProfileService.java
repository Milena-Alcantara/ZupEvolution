package edu.zupevolution.service;


import edu.zupevolution.model.UserModel;
import edu.zupevolution.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonalProfileService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Object> updatePersonalProfile(Long id, UserModel updatedUserModel) {
        Optional<UserModel> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isPresent()) {
            UserModel existingUser = existingUserOptional.get();
            String newPassword = updatedUserModel.getPassword();

            if (newPassword != null && newPassword.length() >= 8) {
                existingUser.setPassword(newPassword);
                userRepository.save(existingUser);
                return ResponseEntity.status(HttpStatus.OK).body("Perfil do usuário atualizado com sucesso!");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A senha fornecida não atende aos critérios de segurança.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado com o ID fornecido.");
        }
    }
    public ResponseEntity<Object> getPersonalProfile(Long id) {
        Optional<UserModel> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            UserModel user = userOptional.get();
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado com o ID fornecido.");
        }
    }
}
