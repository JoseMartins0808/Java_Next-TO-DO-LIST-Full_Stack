package br.com.josemartins.todolist.users;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users")
public class UserController {

    private IUserRepository userRepository;

    public UserController(IUserRepository userRepository){
        this.userRepository = userRepository;
    }
    
    @PostMapping
    public ResponseEntity<UserModel> create (@RequestBody UserModel userModel) {

        final boolean userFoundByUsername = userRepository.existsByUsername(userModel.getUsername());

        final boolean userFoundByEmail = userRepository.existsByEmail(userModel.getEmail());

        if(userFoundByUsername) {
            final HashMap<String , String> errorMessage = new HashMap<String, String>();
            errorMessage.put("message", "Username already exists");
            System.out.println(errorMessage);
            return null;
        }

        if(userFoundByEmail) {
            final HashMap<String , String> errorMessage = new HashMap<String, String>();
            errorMessage.put("message", "Email already exists");
            System.out.println(errorMessage);
            return null;
        }

        final String hashPassword = BCrypt.withDefaults().hashToString(10, userModel.getPassword().toCharArray());

        userModel.setPassword(hashPassword);
        
        final UserModel createUser = this.userRepository.save(userModel);

        return ResponseEntity.status(HttpStatus.OK).body(createUser);
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> getAll () {
        final List<UserModel> allUsers = userRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(allUsers);
    }

}