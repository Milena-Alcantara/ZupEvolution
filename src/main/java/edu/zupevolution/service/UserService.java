package edu.zupevolution.service;

import edu.zupevolution.model.AccessTypeModel;
import edu.zupevolution.model.UserModel;
import edu.zupevolution.repository.AccessTypeRepository;
import edu.zupevolution.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccessTypeRepository accessTypeRepository;

    public ResponseEntity<Object> createUser(@RequestBody UserModel userModel){
        if (dataValidate(userModel)) {
            userRepository.save(userModel);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário salvo com sucesso.");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Verifique os dados informados.");
    }
    private boolean dataValidate(UserModel userModel) {
        String EMAIL_PATTERN =
                "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        String namePattern = "^(?![0-9 ]*$).+";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Pattern pattern1 = Pattern.compile(namePattern);

        if (userModel.getName() == null || !userModel.getName().matches(String.valueOf(pattern1))
                || userModel.getBirthday() == null || userModel.getPassword().length() < 8 ||
                userModel.getPassword() == null || userModel.getEmail() == null
                || !userModel.getEmail().matches(String.valueOf(pattern))) {
            return false;
        }
        return true;
    }
    public ResponseEntity<Object> deleteUser(@Param("email") String email){
        if (email!=null){
            Optional<UserModel> userLocated = userRepository.findByEmail(email);
            if (userLocated.isPresent()){
                userRepository.deleteById(userLocated.get().getId());
                return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado.");
            }else {
              return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não localizado.");
            }
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("E-mail inválido.");
    }
    public ResponseEntity<Object> findUserByEmail(String email){
        if (email!=null){
            Optional<UserModel> locatedUser = userRepository.findByEmail(email);
            if (locatedUser.isPresent())
                return  ResponseEntity.ok(locatedUser.get());
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não localizado.");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("E-mail inválido.");
    }
    public ResponseEntity<Object> getAllUsers() {
        List<UserModel> users = userRepository.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não localizado.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(users);
        }
    }

    public ResponseEntity<Object> updateAccessTypeUserByID(Long id, AccessTypeModel accessTypeModel) {
        if (id != null && accessTypeModel != null) {
            if (accessTypeModel.getId()>=4)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Esse tipo de acesso é inválido.");
            else {
                Optional<UserModel> locatedUser = userRepository.findById(id);
                Optional<AccessTypeModel> locatedAcessType = accessTypeRepository.findById(accessTypeModel.getId());
                if (locatedUser.isPresent() && locatedAcessType.isPresent()) {
                    accessTypeModel.setType(locatedAcessType.get().getType());
                    accessTypeModel.setId(locatedAcessType.get().getId());
                    accessTypeRepository.save(accessTypeModel);

                    UserModel userUpdate = locatedUser.get();
                    userUpdate.setAccess_type(accessTypeModel);
                    userRepository.save(userUpdate);
                    return ResponseEntity.ok(userUpdate);
                } else
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não localizado.");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Revise os dados informados");
    }
}
