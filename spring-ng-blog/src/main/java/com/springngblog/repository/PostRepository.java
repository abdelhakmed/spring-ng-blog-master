package com.springngblog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springngblog.model.Post;


public interface PostRepository extends JpaRepository <Post, Long>{
	
List<Post> findByUsername(String username);
	 
	

	
}