package com.example.springproject.controllers;

import com.example.springproject.beans.PostRepository;

import com.example.springproject.db.DBManager;
import com.example.springproject.dto.CategoriesPost;
import com.example.springproject.dto.Post;
import com.example.springproject.dto.Task;
import com.example.springproject.dto.Users;
import com.example.springproject.services.PostServices;
import com.example.springproject.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Controller
public class AdminPostsController {


    @Autowired
    private PostServices postServices;

    @Autowired
    UserServices userServices;

    @Autowired
    PasswordEncoder passwordEncoder;


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/adminPosts")
    public String adminPosts(Model model){

        List<Post> posts = postServices.getAllPosts();
        model.addAttribute("CURRENT_USER", getUser());

        model.addAttribute("posts",posts);
        return "adminPosts";
    }




    @PostMapping(value = "/addPost")
    public String addTask(@RequestParam(name="title") String title,
                          @RequestParam(name="shortContent") String shortContent,
                          @RequestParam(name="content") String content,
                          @RequestParam(name="pictureUrl") String pictureUrl){
        Post post = new Post();
        post.setTitle(title);
        post.setShortContent(shortContent);
        post.setContent(content);
        post.setPictureURL(pictureUrl);

        postServices.savePost(post);


        return "redirect:/adminPosts?success";
    }


    @GetMapping(value = "/deletePost/{postId}")
    public String deletePost(Model model, @PathVariable(name="postId") Long id){

        postServices.deletePost(id);

        model.addAttribute("CURRENT_USER", getUser());

        return "redirect:/adminPosts?success";
    }


    @GetMapping(value = "/detailPost/{postId}")
    public String detailPost(Model model, @PathVariable(name="postId") Long id){
        Post post = postServices.getPost(id);

        model.addAttribute("post",post);
        List<CategoriesPost> categoriesPosts = postServices.getAllCategories();
        List<CategoriesPost> postCategories = post.getCategoriesPost();
        categoriesPosts.removeAll(postCategories);
        model.addAttribute("categoriesPost",categoriesPosts);
        model.addAttribute("CURRENT_USER", getUser());

        return "detailPost";
    }


    @PostMapping(value = "/editPost")
    public String addTask(@RequestParam(name="title") String title,
                          @RequestParam(name="shortContent") String shortContent,
                          @RequestParam(name="content") String content,
                          @RequestParam(name="pictureUrl") String pictureUrl,
                          @RequestParam(name="id")Long id){
        Post post = postServices.getPost(id);
        post.setTitle(title);
        post.setShortContent(shortContent);
        post.setContent(content);
        post.setPictureURL(pictureUrl);
        postServices.savePost(post);



        return "redirect:/adminPosts?success";
    }


    @PostMapping(value = "/assigncategory")
    public String assignCategory(@RequestParam(name = "categoryId")Long categoryId,
                                @RequestParam(name="postId")Long postId)
    {
        CategoriesPost categoriesPost = postServices.getCategoryPost(categoryId);
        if(categoriesPost!=null){
            Post post = postServices.getPost(postId);
            if(post!=null){
                List<CategoriesPost> categoriesPosts = post.getCategoriesPost();

                if(categoriesPosts == null){
                    categoriesPosts = new ArrayList<>();
                }

                categoriesPosts.add(categoriesPost);
                post.setCategoriesPost(categoriesPosts);

                postServices.savePost(post);
                return "redirect:/detailPost/"+postId;
            }
        }

        return "adminPosts";
    }



    @PostMapping(value = "/removecategory")
    public String removeCategory(@RequestParam(name = "categoryId")Long categoryId,
                                 @RequestParam(name="postId")Long postId)
    {
        CategoriesPost categoriesPost = postServices.getCategoryPost(categoryId);
        if(categoriesPost!=null){
            Post post = postServices.getPost(postId);
            if(post!=null){
                List<CategoriesPost> categoriesPosts = post.getCategoriesPost();

                if(categoriesPosts == null){
                    categoriesPosts = new ArrayList<>();
                }

                categoriesPosts.remove(categoriesPost);
                post.setCategoriesPost(categoriesPosts);

                postServices.savePost(post);
                return "redirect:/detailPost/"+postId;
            }
        }

        return "adminPosts";
    }

    @GetMapping(value = "profile")
    public String profile(Model model){
        model.addAttribute("CURRENT_USER", getUser());
        return "profile";
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/updateprofile")
    public String updateProfile(@RequestParam(name = "full_name")String fullName){
        Users currentUser = getUser();
        currentUser.setFullName(fullName);
        userServices.updateUser(currentUser);
        return "redirect:/profile";

    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/updatepassword")
    public String updatePassword(@RequestParam(name = "old_password")String oldPassword,
                                 @RequestParam(name = "new_password")String newPassword,
                                @RequestParam(name = "confirm_new_password")String confirmNewPassword){
        Users currentUser = getUser();
        if(passwordEncoder.matches(oldPassword,currentUser.getPassword())&& newPassword.equals(confirmNewPassword)){
            currentUser.setPassword(passwordEncoder.encode(newPassword));
            userServices.updateUser(currentUser);
            return "redirect:/profile?success";
        }
        return "redirect:/profile?error";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping(value = "registerpage")
    public String registerpage(){
        return "registerpage";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping(value = "register")
    public String register(@RequestParam(name = "full_name")String fullName,
                            @RequestParam(name = "email")String email,
                            @RequestParam(name = "new_password")String newPassword,
                            @RequestParam(name = "confirm_new_password")String confirmNewPassword){
        Users users = new Users();
        if(userServices.isFreeEmail(email)&&newPassword.equals(confirmNewPassword)){

            users.setFullName(fullName);
            users.setPassword(passwordEncoder.encode(newPassword));
            users.setEmail(email);
            userServices.addUser(users);
            return "/index";

        }else{
            return "redirect:/registerpage?error";
        }

    }


    private Users getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            return (Users) authentication.getPrincipal();
        }
        return null;
    }
}
