package com.wildcodeschool.cerebook.controller.crud_rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wildcodeschool.cerebook.entity.CerebookUser;
import com.wildcodeschool.cerebook.entity.Post;
import com.wildcodeschool.cerebook.repository.CerebookUserRepository;
import com.wildcodeschool.cerebook.repository.PostRepository;
import com.wildcodeschool.cerebook.service.tweeterApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostRestController extends AbstractCrudRestLongController<Post> {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CerebookUserRepository cerebookUserRepository;

    @GetMapping("/{CerebookUser.id}/getAllByAuthorOrByAuthorFriends")
    public List<JsonNode> getAllPostsByCerebookUserFriendsOrByAuthor(@PathVariable("CerebookUser.id") String id) throws IOException, URISyntaxException {
        CerebookUser cerebookUser = cerebookUserRepository.findById(parseId(id)).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND, getNotFoundMessage(id)
                ));
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        List<JsonNode> cerebookPosts = postRepository.findAllByAuthorOrByAuthorFriends(cerebookUser)
                .stream().map(p -> (JsonNode)(objectMapper.valueToTree(p))).collect(Collectors.toList());
        List<JsonNode> tweetPosts = tweeterApi.getPostFromTweet();
        List<JsonNode> posts = new ArrayList<JsonNode>();
        posts.addAll(cerebookPosts);
        posts.addAll(tweetPosts);
        Collections.sort(posts,new Comparator<JsonNode>() {
            public int compare(JsonNode post, JsonNode post2) {
                return post2.get("createdAt").textValue().compareTo(post.get("createdAt").textValue());
            }});
        return posts;
    }

    @Override
    protected JpaRepository<Post, Long> getRepository() {
        return postRepository;
    }

    @Override
    protected String getControllerRoute() {
        return "/api/posts";
    }

    @Override
    protected String[] getElementFields() {
        return new String[]{"content", "video"};
    }

    @Override
    protected Class<Post> getElementClass() {
        return Post.class;
    }
}
