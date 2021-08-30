package com.example.springproject.services.impl;

import com.example.springproject.Repositories.CategoryPostRepositories;
import com.example.springproject.beans.PostRepository;
import com.example.springproject.dto.CategoriesPost;
import com.example.springproject.dto.Post;
import com.example.springproject.services.PostServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostServicesImpl implements PostServices {


    @Autowired
    private PostRepository postRepository;

    @Autowired
   private CategoryPostRepositories categoryPostRepositories;
    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPost(Long id) {
        return postRepository.getById(id);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post addPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<CategoriesPost> getAllCategories() {
        return categoryPostRepositories.findAll();
    }

    @Override
    public CategoriesPost getCategoryPost(Long id) {
        return categoryPostRepositories.findById(id).orElse(null);
    }
}
