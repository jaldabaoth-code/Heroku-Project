package com.wildcodeschool.cerebook.controller;

import com.wildcodeschool.cerebook.entity.CerebookUser;
import com.wildcodeschool.cerebook.entity.User;
import com.wildcodeschool.cerebook.repository.UserRepository;
import com.wildcodeschool.cerebook.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;

@Controller
public class RegisterController {
    @Autowired
    private UserRepository userRepo;

    private UserDetailsServiceImpl userService;

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/process_register")
    public String processRegister(@Valid User user, BindingResult bindingResult, Model model) {
        if (userRepo.findByUsername(user.getUsername()) != null) {
            bindingResult.rejectValue("username", "error.username", "Username already exists!");
        }
        if (userRepo.findByEmail(user.getEmail()) != null) {
            bindingResult.rejectValue("email", "error.email", "An account is already registered with your email address. Please log in or use another one.");
        }
        if (bindingResult.hasErrors() || bindingResult.hasFieldErrors() || bindingResult.hasGlobalErrors()) {
            return "registration";
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            user.setRole("ROLE_USER");
            CerebookUser cerebookUser = new CerebookUser();
            user.setCerebookUser(cerebookUser);
            cerebookUser.setUser(user);
            cerebookUser.setProfilImage("");
            cerebookUser.setBackground("");
            userRepo.save(user);
            return "register_success";
        }
    }
}
