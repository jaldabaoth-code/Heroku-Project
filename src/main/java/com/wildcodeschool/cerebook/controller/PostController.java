package com.wildcodeschool.cerebook.controller;

import com.wildcodeschool.cerebook.entity.Post;
import com.wildcodeschool.cerebook.repository.CerebookUserRepository;
import com.wildcodeschool.cerebook.repository.PostRepository;
import com.wildcodeschool.cerebook.service.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/posts")
public class PostController extends AbstractCrudLongController<Post> {
    @Autowired
    private PostRepository postRepositoryDAO;

    @Autowired
    private CerebookUserRepository cerebookUserRepository;

    @Override
    @GetMapping("")
    public String getAll(Model model) {
        return getControllerRoute() + "/getAllByAuthor";
    }

    @GetMapping("/{CerebookUser.id}/getAllByAuthor")
    public String getAllPostsByAuthor(Model model, Principal principal, @PathVariable("CerebookUser.id") String id) {
        model.addAttribute("allElements", postRepositoryDAO.findAllByAuthorOrderByCreatedAtDesc(getCurrentCerebookUser(principal)));
        model.addAttribute("elementFields", getElementFields());
        model.addAttribute("currentUser", getCurrentCerebookUser(principal));
        return getControllerRoute() + "/getAllByAuthor";
    }

    @GetMapping("/{CerebookUser.id}/getAllByAuthorOrByAuthorFriends")
    public String getAllPostsByCerebookUserFriendsOrByAuthor(Model model, @PathVariable("CerebookUser.id") String id, Principal principal) {
        model.addAttribute("currentUser", getCurrentCerebookUser(principal));
        return getControllerRoute() + "/getAllByAuthorOrByAuthorFriends";
    }

    @Override
    @PostMapping("/create")
    public String create(HttpServletRequest hsr, Post post) {
        try {
            Part picture = hsr.getPart("createPostPicture");
            String fileName = Paths.get(picture.getSubmittedFileName()).getFileName().toString();
            post.setPicture(fileName);
            String uploadDir = "src/main/resources/public/images/Posts/";
            FileUploadUtil.saveFile(uploadDir, fileName, picture);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        preProcessElement(post, hsr);
        getRepository().save(post);
        return "redirect:/";
    }

    @Override
    @PostMapping("/{id}/update")
    public String update(HttpServletRequest hsr, @PathVariable("id") String id, @ModelAttribute Post post) {
        try {
            Part picture = hsr.getPart("updatePostPicture");
            String fileName = Paths.get(picture.getSubmittedFileName()).getFileName().toString();
            post.setPicture(fileName);
            String uploadDir = "src/main/resources/public/images/Posts/";
            FileUploadUtil.saveFile(uploadDir, fileName, picture);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        preProcessElement(post, hsr);
        getRepository().save(post);
        return "redirect:/";
    }

    @Override
    protected JpaRepository<Post, Long> getRepository() {
        return postRepositoryDAO;
    }

    @Override
    protected String getControllerRoute() {
        return "posts";
    }

    @Override
    public String[] getElementFields() {
        return new String[]{"content", "video", "picture"};
    }

    @Override
    protected Class<Post> getElementClass() {
        return Post.class;
    }

    @Override
    protected void preProcessElement(Post post, HttpServletRequest hsr) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.now();
        post.setCreatedAt(LocalDate.from(date));
        post.setTweetos(false);
        post.setAuthor(getCurrentCerebookUser(hsr.getUserPrincipal()));
        if(post.getVideo().isEmpty()) {
            post.setVideo(null);
        }
        if(post.getPicture().isEmpty()) {
            post.setPicture(null);
        }
    }
}
