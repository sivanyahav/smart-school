package com.example.myapp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student extends Object {
    private String fbUID;
    private String full_name;
    private long id;
    private String email;
    private String grade;
    private String password;
    private boolean update_grade;
    private boolean update_presence;
    private Map <String,Object> courses_with_grade;
    private Map <String,String> presence;
    private final String type = "Student";


    public Student(){}
    public Student(String fbUID, String full_name, long id, String email, String name_school, String password,Map<String,Object> courses_with_grade) {
        this.fbUID = fbUID;
        this.full_name = full_name;
        this.id = id;
        this.email = email;
        this.grade = name_school;
        this.password = password;
        this.courses_with_grade = courses_with_grade;
        // Creating an empty HashMap
    }
    public Student(String fbUID, String full_name, long id, String email, String grade, String password) {
        this.fbUID = fbUID;
        this.full_name = full_name;
        this.id = id;
        this.email = email;
        this.grade = grade;
        this.password = password;
        this.courses_with_grade = new HashMap<>();
        this.presence=new HashMap<>();
        this.update_grade=false;
        this.update_presence=false;


        this.courses_with_grade.put("Math","0");
        this.courses_with_grade.put("English","0");
        this.courses_with_grade.put("Bible","0");
        this.courses_with_grade.put("Hebrew","0");
        this.courses_with_grade.put("Computers","0");

        this.presence.put("Math","0");
        this.presence.put("English","0");
        this.presence.put("Bible","0");
        this.presence.put("Hebrew","0");
        this.presence.put("Computers","0");

    }



    public String getType() {
        return type;
    }
    public String getFbUID() {
        return fbUID;
    }

    public void setFbUID(String fbUID) {
        this.fbUID = fbUID;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String get_grade() {
        return grade;
    }

    public void set_grade(String name_school) {
        this.grade = name_school;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, Object> getCourses_with_grade() {
        return courses_with_grade;
    }

    public void setCourses_with_grade(Map<String, Object> courses_with_grade) {
        this.courses_with_grade = courses_with_grade;
    }

    public Map<String, String> getPresence() {
        return presence;
    }

    public void setPresence(Map<String, String> presence) {
        this.presence = presence;
    }

    public boolean isUpdate_grade() {
        return update_grade;
    }

    public void setUpdate_grade(boolean update_grade) {
        this.update_grade = update_grade;
    }

    public boolean isUpdate_presence() {
        return update_presence;
    }

    public void setUpdate_presence(boolean update_presence) {
        this.update_presence = update_presence;
    }
}

