package br.com.josemartins.todolist.users;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private IUserRepository userRepository;

    public UserController(IUserRepository userRepository){
        this.userRepository = userRepository;
    }
    
    @PostMapping()
    public ResponseEntity<UserModel> create (@RequestBody UserModel userModel) {

        final boolean userFoundByUsername = userRepository.existsByUsername(userModel.getUsername());

        final boolean userFoundByEmail = userRepository.existsByEmail(userModel.getEmail());

        if(userFoundByUsername) {
            final HashMap<String , String> errorMessage = new HashMap<String, String>();
            errorMessage.put("message", "Username already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        if(userFoundByEmail) {
            final HashMap<String , String> errorMessage = new HashMap<String, String>();
            errorMessage.put("message", "Email already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
        }
        
        final UserModel createUser = this.userRepository.save(userModel);

        return ResponseEntity.status(HttpStatus.OK).body(createUser);
    }

}