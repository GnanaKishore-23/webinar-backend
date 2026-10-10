
package com.klu.Controller;

import com.klu.entity.User;
import com.klu.repository.UserRepository;
import com.klu.security.JwtService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(
            UserRepository userRepo,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public record LoginResponse(
            Long id,
            String email,
            String role,
            String fullName,
            String profilePic,
            String dob,
            String phoneNumber,
            String education,
            String qualification,
            String token) {
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody User user) {

        if (user.getEmail() == null || user.getEmail().isBlank()
                || user.getPassword() == null
                || user.getPassword().isBlank()) {
            return ResponseEntity.badRequest()
                    .body("Email and password are required.");
        }

        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email already exists!");
        }

        // Public registration can only create USER accounts
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Account created!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        if (user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest()
                    .body("Email and password are required.");
        }

        User foundUser = userRepo.findByEmail(user.getEmail())
                .orElse(null);

        if (foundUser == null || foundUser.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password.");
        }

        String storedPassword = foundUser.getPassword();
        String submittedPassword = user.getPassword();
        boolean matches;

        if (storedPassword.startsWith("$2")) {
            matches = passwordEncoder.matches(
                    submittedPassword, storedPassword);
        } else if (storedPassword.equals(submittedPassword)) {
            // Migrate a legacy plain-text password after successful login
            matches = true;
            foundUser.setPassword(
                    passwordEncoder.encode(submittedPassword));
            userRepo.save(foundUser);
        } else {
            matches = false;
        }

        if (!matches) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password.");
        }

        String token = jwtService.generateToken(
                foundUser.getEmail(), foundUser.getRole());

        LoginResponse response = new LoginResponse(
                foundUser.getId(),
                foundUser.getEmail(),
                foundUser.getRole(),
                foundUser.getFullName(),
                foundUser.getProfilePic(),
                foundUser.getDob(),
                foundUser.getPhoneNumber(),
                foundUser.getEducation(),
                foundUser.getQualification(),
                token
        );

        return ResponseEntity.ok(response);
    }
}
