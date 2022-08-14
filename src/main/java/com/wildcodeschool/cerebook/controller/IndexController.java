package com.wildcodeschool.cerebook.controller;

import com.wildcodeschool.cerebook.entity.CerebookUser;
import com.wildcodeschool.cerebook.repository.CerebookUserRepository;
import com.wildcodeschool.cerebook.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.time.LocalDate;
import java.time.Period;

@Controller
public class IndexController extends AbstractCrudLongController<CerebookUser> {

    @Autowired
    private CerebookUserRepository cerebookUserRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostController postController;

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("cerebookUser", getCurrentCerebookUser(principal));
        return "index";
    }

    @GetMapping("/profiles/{id}")
    public String getById(Model model, @PathVariable Long id) {
        model.addAttribute("cerebookUser", cerebookUserRepository.findCerebookUserById(id));

        return getControllerRoute() + "/getById";
    }

    @GetMapping("/login")
    public String login(Principal principal) {
        if (principal != null) {
            return "redirect:/";
        }
        return "login";
    }

    @Override
    protected JpaRepository<CerebookUser, Long> getRepository() {
        return cerebookUserRepository;
    }

    @Override
    protected String getControllerRoute() {
        return "profiles";
    }

    @Override
    protected String[] getElementFields() {
        return new String[]{"profilImage", "background", "superPowers", "genre", "bio", "membership", "user", "birthDate"};
    }

    @Override
    protected Class<CerebookUser> getElementClass() {
        return null;
    }

    /* Creation of the method to calculate age */
    public int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        return Period.between(birthDate, currentDate).getYears();
    }
}

