package com.klu.Controller;

import com.klu.entity.User;
import com.klu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/signup")
    public String signUp(@RequestBody User user) {
        if(userRepo.findByEmail(user.getEmail()).isPresent()) {
            return "Email already exists!";
        }
        userRepo.save(user);
        return "Account created!";
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        User foundUser = userRepo.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (foundUser.getPassword().equals(user.getPassword())) {
            return foundUser;
        }
        throw new RuntimeException("Invalid Password");
    }
}