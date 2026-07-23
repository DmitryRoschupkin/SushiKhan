package me.dmitriy.sushikhan.web;

import jakarta.validation.Valid;
import me.dmitriy.sushikhan.User;
import me.dmitriy.sushikhan.data.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/profile")
public class ProfileViewController {

    private final UserRepository userRepository;

    public ProfileViewController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String myProfile(@AuthenticationPrincipal User currentUser) {
        if(currentUser == null) {
            return "redirect:/login";
        }
        return "redirect:/profile/" + currentUser.getId();
    }

    @GetMapping("/{id}")
    public String viewProfile(@PathVariable long id,
                            @AuthenticationPrincipal User currentUser,
                            Model model){

        if (currentUser == null) {
            return "redirect:/login";
        }

        checkAccessRight(id, currentUser);

        User targetUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        model.addAttribute("profileUser", targetUser);

        return "profile";
    }

    private void checkAccessRight(long targetUserId, User currentUser) {
        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to perform this operation");
        }

        boolean isOwner = currentUser.getId() == targetUserId;
        boolean isAdmin = currentUser.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isOwner && !isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied. Не дорос ещё до чужих акков");
        }
    }
}
