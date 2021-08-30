package com.example.springproject.Repositories;

import com.example.springproject.dto.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepositories extends JpaRepository<Post,Long> {

}
