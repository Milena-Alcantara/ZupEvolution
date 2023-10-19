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
            existingUser.setPassword(updatedUserModel.getPassword());

            userRepository.save(existingUser);
            return ResponseEntity.status(HttpStatus.OK).body("Perfil do usuário atualizado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado com o ID fornecido.");
        }
    }
}
