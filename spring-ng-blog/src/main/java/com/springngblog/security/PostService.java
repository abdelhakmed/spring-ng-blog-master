package com.springngblog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springngblog.dto.PostDto;
import com.springngblog.exception.PostNotFoundException;
import com.springngblog.model.Post;
import com.springngblog.repository.PostRepository;
import com.springngblog.service.AuthService;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

import java.text.SimpleDateFormat;

@Service
public class PostService {

    @Autowired
    private AuthService authService;
    @Autowired
    private PostRepository postRepository;

    @Transactional
    public List<PostDto> showAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }

    @Transactional
    public void createPost(PostDto postDto) {
        Post post = mapFromDtoToPost(postDto);
        postRepository.save(post);
    }

    @Transactional
    public PostDto readSinglePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
        return mapFromPostToDto(post);
    }

    private PostDto mapFromPostToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setUsername(post.getUsername());
        if(post.getUpdatedOn() != null) {
        	  Date myDate = Date.from((post.getUpdatedOn()));
              SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy");
              String formattedDate = formatter.format(myDate);	
              postDto.setUpdatedOn(formattedDate);
        }
      
       
        return postDto;
    }

    private Post mapFromDtoToPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        post.setCreatedOn(Instant.now());
        post.setUsername(loggedInUser.getUsername());
        post.setUpdatedOn(Instant.now());
        return post;
    }
    
    @Transactional
    public List<PostDto> showPostsByUsername(String username) {
        List<Post> posts = postRepository.findByUsername(username);
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }
    
    @Transactional
    public void deletePost(Long id) {
    	postRepository.deleteById(id);
    }
}
