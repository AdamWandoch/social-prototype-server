package com.adamwandoch.socialprotoapi.controllers;

import com.adamwandoch.socialprotoapi.models.post.PostModel;
import com.adamwandoch.socialprotoapi.models.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

/**
 * @author Adam Wandoch
 */
@RestController
@CrossOrigin(origins = {"https://social-app-prototype.netlify.app/", "http://localhost:3000", "https://localhost:44347/"})
public class PostController {

    @Autowired
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/post/post")
    public ResponseEntity postPost(@RequestBody PostModel post) {
        postService.savePost(post);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/post/getall")
    public ArrayList<PostModel> getAllPosts() {
        return (ArrayList<PostModel>) postService.getAllPosts();
    }

    @GetMapping("/post/{id}")
    public Optional<PostModel> getPost(@PathVariable(value = "id") Long id) {
        return postService.getPost(id);
    }
}
