package com.example.healthcare.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.healthcare.Entity.User;
import com.example.healthcare.Repository.UserRepository;
import com.example.healthcare.Service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;

    public AuthController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    // ================= REGISTER =================
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        // Check duplicate email
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email already registered. Please login.");
        }
        User savedUser = userService.register(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> loggedUser = userService.login(user.getEmail(), user.getPassword());
        if (loggedUser.isPresent()) {
            return ResponseEntity.ok(loggedUser.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }
    }

    // ================= GET ALL USERS =================
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ================= GET ALL DOCTORS =================
    @GetMapping("/doctors")
    public List<User> getAllDoctors() {
        return userRepository.findByRole("DOCTOR");
    }

    // ================= GET ALL PATIENTS =================
    @GetMapping("/patients")
    public List<User> getAllPatients() {
        return userRepository.findByRole("PATIENT");
    }

    // ================= GET USER BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ================= UPDATE USER PROFILE =================
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updated) {
        return userRepository.findById(id).map(user -> {
            if (updated.getName()           != null) user.setName(updated.getName());
            if (updated.getPhone()          != null) user.setPhone(updated.getPhone());
            if (updated.getAddress()        != null) user.setAddress(updated.getAddress());
            if (updated.getSpeciality()     != null) user.setSpeciality(updated.getSpeciality());
            if (updated.getQualification()  != null) user.setQualification(updated.getQualification());
            if (updated.getExperience()     != null) user.setExperience(updated.getExperience());
            if (updated.getConsultingFee()  != null) user.setConsultingFee(updated.getConsultingFee());
            if (updated.getDob()            != null) user.setDob(updated.getDob());
            if (updated.getAge()            != null) user.setAge(updated.getAge());
            if (updated.getBloodGroup()     != null) user.setBloodGroup(updated.getBloodGroup());
            if (updated.getAllergies()       != null) user.setAllergies(updated.getAllergies());
            if (updated.getProfileImage()   != null) user.setProfileImage(updated.getProfileImage());
            return ResponseEntity.ok(userRepository.save(user));
        }).orElse(ResponseEntity.notFound().build());
    }

    // ================= CHANGE PASSWORD =================
    @PutMapping("/change-password/{id}")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody User req) {
        return userRepository.findById(id).map(user -> {
            if (!user.getPassword().equals(req.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Current password is incorrect.");
            }
            // In real app use BCrypt — keeping plain text to match existing setup
            user.setPassword(req.getSpeciality()); // new password passed in speciality field
            userRepository.save(user);
            return ResponseEntity.ok("Password changed successfully.");
        }).orElse(ResponseEntity.notFound().build());
    }
}