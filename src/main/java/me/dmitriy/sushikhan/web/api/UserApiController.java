package me.dmitriy.sushikhan.web.api;


import me.dmitriy.sushikhan.User;
import me.dmitriy.sushikhan.data.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @PreAuthorize("hasRole('ADMIN')")
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id, @AuthenticationPrincipal User currentUser) {
        checkOwnerOrAdmin(id, currentUser);
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable long id, @RequestBody User userDetails,  @AuthenticationPrincipal User currentUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));

        existingUser.setFullName(userDetails.getName());
        existingUser.setUsername(userDetails.getUsername());
        existingUser.setCity(userDetails.getCity());
        existingUser.setRegion(userDetails.getRegion());
        existingUser.setStreet(userDetails.getStreet());

        if (currentUser.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            if (userDetails.getRole() != null) {
                existingUser.setRole(userDetails.getRole());
            }
        }
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

    private void checkOwnerOrAdmin(long targetId, User currentUser) {
        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        boolean isOwner = currentUser.getId() == targetId;
        boolean isAdmin = currentUser.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isOwner && !isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
    }
}
