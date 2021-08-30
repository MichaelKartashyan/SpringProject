package com.example.springproject.services;

import com.example.springproject.dto.CategoriesPost;
import com.example.springproject.dto.Post;

import java.util.List;

public interface PostServices {
    List<Post> getAllPosts();
    Post getPost(Long id);
    void deletePost(Long id);
    Post savePost(Post post);
    Post addPost(Post post);

    List<CategoriesPost> getAllCategories();
    CategoriesPost getCategoryPost(Long id);

}
