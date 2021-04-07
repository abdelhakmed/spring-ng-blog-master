package com.springngblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springngblog.dto.PostDto;
import com.springngblog.security.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/posts/")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity createPost(@RequestBody PostDto postDto) {
        postService.createPost(postDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> showAllPosts() {
        return new ResponseEntity<>(postService.showAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PostDto> getSinglePost(@PathVariable @RequestBody Long id) {
        return new ResponseEntity<>(postService.readSinglePost(id), HttpStatus.OK);
    }
    
    @GetMapping("/getByUsername/{username}")
    public ResponseEntity<List<PostDto>> getPostsByusername(@PathVariable @RequestBody String username) {
        return new ResponseEntity<>(postService.showPostsByUsername(username), HttpStatus.OK);
    }
    
 
    
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity deletePost(@PathVariable Long id) {
    	postService.deletePost(id);
    	 return new ResponseEntity(HttpStatus.OK);
    }
    
}
