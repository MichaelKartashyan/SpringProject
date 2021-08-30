package com.example.springproject.controllers;


//import com.example.springproject.beans.BeanInterfase;
import com.example.springproject.beans.PostRepository;

import com.example.springproject.db.DBManager;
import com.example.springproject.dto.Items;
import com.example.springproject.dto.Post;
import com.example.springproject.dto.Task;
import com.example.springproject.dto.Users;
import com.example.springproject.services.PostServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Controller
public class HomeController {



   @Autowired
   private PostServices postServices;

//    @Autowired
//    private BeanInterfase beanInterfase;


    @GetMapping(value = {"/", "index"})
    public String index(Model model){
        ArrayList<Items> items = DBManager.getAllItems();
        model.addAttribute("tovary",items);
        model.addAttribute("CURRENT_USER", getUser());

        return "index";
    }

    @GetMapping(value = "/about")
    public String aboutPage(){
        return "about";
    }

    @PostMapping(value = "/additem")
    public String addItem(@RequestParam(name="name") String name,
                          @RequestParam(name="price") double price,
                          @RequestParam(name="amount")int amount){
        Items items = new Items();
        items.setName(name);
        items.setPrice(price);
        items.setAmount(amount);

        DBManager.addItem(items);

        return "redirect:/?success";
    }

    @GetMapping(value = "/details/{itemId}")
    public String details(Model model, @PathVariable(name="itemId") Long id){
        Items items = DBManager.getItem(id);
        model.addAttribute("tovar",items);
        model.addAttribute("CURRENT_USER", getUser());

        return "details";
    }

    @GetMapping(value = "/shop")
    public String shop(Model model){
        ArrayList<Items> items = DBManager.getAllItems();
        model.addAttribute("items",items);
        model.addAttribute("CURRENT_USER", getUser());

        return "shop";
    }


    @GetMapping(value = "/task")
    public String task(Model model){
        ArrayList<Task> tasks = DBManager.getTasks();
        model.addAttribute("tasks",tasks);
        model.addAttribute("CURRENT_USER", getUser());

        return "task";
    }

    @GetMapping(value = "/detailtask/{taskId}")
    public String detailtask(Model model, @PathVariable(name="taskId") Long id){
        Task task = DBManager.getTask(id);
        model.addAttribute("task",task);
        model.addAttribute("CURRENT_USER", getUser());

        return "detailstask";
    }



    @PostMapping(value = "/addtask")
    public String addTask(@RequestParam(name="name") String name,
                          @RequestParam(name="description") String description,
                          @RequestParam(name="date") String date){
        Task task = new Task();
        task.setName(name);
        task.setDescription(description);
        task.setDeadlineDate(date);
        DBManager.addTask(task);


        return "redirect:/task?success";
    }


    @PostMapping(value = "/edittask")
    public String edittask(@RequestParam(name="name") String name,
                          @RequestParam(name="description") String description,
                          @RequestParam(name="date") String date,
                          @RequestParam(name="done") String done,
                          @RequestParam(name="id") Long id){

        Task task = DBManager.getTask(id);

        task.setName(name);
        task.setDescription(description);
        task.setDeadlineDate(date);
        task.setDone(done);


        return "redirect:/task?success";
    }


    @GetMapping(value = "/deletetask/{taskId}")
    public String deletetask(Model model, @PathVariable(name="taskId") Long id){
        Task task = DBManager.getTask(id);
        DBManager.deleteTask(task);
        model.addAttribute("CURRENT_USER", getUser());

        return "redirect:/task?success";
    }

    @GetMapping(value = "/allposts")
    public String allPosts(Model model){

        //ArrayList<Post> posts = beanInterfase.getAllPosts();
        List<Post> posts = postServices.getAllPosts();
        model.addAttribute("CURRENT_USER", getUser());

        model.addAttribute("posts",posts);
        return "allposts";
    }


    @GetMapping(value = "/loginpage")
    public String loginPage(Model model){
        model.addAttribute("CURRENT_USER", getUser());
        return "loginpage";
    }


    private Users getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            return (Users) authentication.getPrincipal();
        }
        return null;
    }
}
