package com.example.springproject.db;

import com.example.springproject.dto.Items;
import com.example.springproject.dto.Task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class DBManager {
    private static ArrayList<Items> tovary = new ArrayList<>();
    private  static Long id =7L;
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static Long idTask = 5L;
    private static Connection connection;

    static {
        tovary.add(new Items(1L,"phone 12 pro","6/128 gb",20,4500000));
        tovary.add(new Items(2L,"iphone","8/256 gb",30,30000));
        tovary.add(new Items(3L,"iphone","8/256 gb",30,30000));
        tovary.add(new Items(4L,"iphone","8/256 gb",30,30000));
        tovary.add(new Items(5L,"iphone","8/256 gb",30,30000));
        tovary.add(new Items(6L,"iphone","8/256 gb",30,30000));
        tasks.add(new Task(1L,"Task1","need to do...","10/21","yes"));
        tasks.add(new Task(2L,"Task2","need to do...","11/21","no"));
        tasks.add(new Task(3L,"Task3","need to do...","12/21","no"));
        tasks.add(new Task(4L,"Task4","need to do...","9/21","no"));


        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/spring_lessons?serverTimezone=UTC","root","");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void addItem(Items items){
        items.setId(id);
        tovary.add(items);

    }

    public static ArrayList<Items> getAllItems(){
        return tovary;
    }

    public static Items getItem(Long id){
        for(Items i:tovary){
            if(i.getId()==id) return i;
        }
        return null;
    }


    public static void addTask(Task task){
        task.setId(idTask);
        task.setDone("no");
        tasks.add(task);
        idTask++;
    }

    public static ArrayList<Task> getTasks() {
        return tasks;
    }

    public static Task getTask(Long id){
        for(Task t : tasks){
            if(t.getId()==id){
                return t;
            }
        }
        return null;
    }
    public static void deleteTask(Task task){
        tasks.remove(task);

    }

    public static void setTasks(ArrayList<Task> tasks) {
        DBManager.tasks = tasks;
    }

    public static Long getIdTask() {
        return idTask;
    }

    public static void setIdTask(Long idTask) {
        DBManager.idTask = idTask;
    }



}
