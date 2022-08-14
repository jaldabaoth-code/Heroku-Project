package com.wildcodeschool.cerebook.controller;

import com.wildcodeschool.cerebook.entity.CerebookUser;
import com.wildcodeschool.cerebook.entity.User;
import com.wildcodeschool.cerebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

public abstract class AbstractCrudController<E, EK> {
    @Autowired
    private UserRepository userRepository;
    protected abstract JpaRepository<E, EK> getRepository();
    protected abstract String getControllerRoute();
    protected abstract EK parseId(String id);
    protected abstract String[] getElementFields();
    protected abstract Class<E> getElementClass();

    @GetMapping("")
    public String getAll(Model model) {
        model.addAttribute("allElements", getRepository().findAll());
        model.addAttribute("elementFields", getElementFields());
        return getControllerRoute() + "/getAll";
    }

    public CerebookUser getCurrentCerebookUser(Principal principal) {
        User user = userRepository.getUserByUsername(principal.getName());
        return user.getCerebookUser();
    }

    @GetMapping("/create")
    public String create(HttpServletRequest hsr, Model model) {
        model.addAttribute("elementFields", getElementFields());
        return getControllerRoute() + "/create";
    }

    @PostMapping("/create")
    public String create(HttpServletRequest hsr, @ModelAttribute E e) {
        preProcessElement(e, hsr);
        getRepository().save(e);
        return "redirect:/" + getControllerRoute();
    }

    @GetMapping("/{id}/update")
    public String updateGet(@PathVariable("id") String id, Model model) {
        E e = getElement(id);
        postProcessElementForUpdateGet(e);
        model.addAttribute("element", e);
        model.addAttribute("elementFields", getElementFields());
        model.addAttribute("controllerRoute", getControllerRoute());
        return getControllerRoute() + "/update";
    }

    @PostMapping("/{id}/update")
    public String update(HttpServletRequest hsr, @PathVariable("id") String id, @ModelAttribute E e) {
        preProcessElement(e, hsr);
        getRepository().save(e);
        return "redirect:/";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") String id) {
        getRepository().deleteById(parseId(id));
        return "redirect:/";
    }

    protected void preProcessElement(E e, HttpServletRequest hsr) {}

    protected void postProcessElementForUpdateGet(E e) {}

    protected E getElement(String id) {
        return getRepository().getById(parseId(id));
    }
}
