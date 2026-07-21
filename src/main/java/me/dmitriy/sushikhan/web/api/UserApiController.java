package me.dmitriy.sushikhan.web.api;


import me.dmitriy.sushikhan.User;
import me.dmitriy.sushikhan.data.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;


@RestController
@RequestMapping(value = "/api/users", produces = "application/json")
@CrossOrigin("https://localhost:8443")
public class UserApiController {

    private final UserRepository userRepository;

    public UserApiController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User updateUser(@PathVariable long id, @RequestBody User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        existingUser.setFullName(user.getName());
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setRole(user.getRole());
        existingUser.setCity(user.getCity());
        existingUser.setRegion(user.getRegion());
        existingUser.setStreet(user.getStreet());
        return userRepository.save(existingUser);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> toggleUserStatus(
            @PathVariable("id") long id,
            @RequestBody Map<String, Boolean> statusUpdate,
            @AuthenticationPrincipal User currentUser) {

        if (currentUser != null && currentUser.getId() == id) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "You cannot ban or disable your own account!"));
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Boolean enabled = statusUpdate.get("enabled");
        if (enabled != null) {
            user.setEnabled(enabled);
            userRepository.save(user);
        }

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable long id, @AuthenticationPrincipal User currentUser) {
        if (currentUser != null && currentUser.getId() == id) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "You cannot delete your own account!"));
        }
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
