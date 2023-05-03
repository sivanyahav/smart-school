package com.example.myapp.model;

import java.util.Map;

public class Secretary {

    private String fbUID;
    private String full_name;
    private long id;
    private String email;
    private String password;
    private final String type = "Secretary";
    private Map<Class, Student> students;
    private Map<Class, Teacher> teachers;

    public Secretary() {
    }

    public Secretary(String fbUID, String email, String password) {
        this.fbUID = fbUID;
        this.email = email;
        this.password = password;
    }

    public Secretary(String fbUID, String full_name, long id, String email, String password, Map<Class, Student> students, Map<Class, Teacher> teachers) {
        this.fbUID = fbUID;
        this.full_name = full_name;
        this.id = id;
        this.email = email;
        this.password = password;
        this.students = students;
        this.teachers = teachers;
    }

    public Map<Class, Student> getStudents() {
        return students;
    }

    public void setStudents(Map<Class, Student> students) {
        this.students = students;
    }

    public Map<Class, Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Map<Class, Teacher> teachers) {
        this.teachers = teachers;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }
}
