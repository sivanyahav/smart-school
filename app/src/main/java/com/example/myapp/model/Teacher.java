package com.example.myapp.model;

public class Teacher
{
    private String fbUID;
    private String full_name;
    private long id;
    private String email;
    private String name_lesson;
    private String password;
   private final String type = "Teacher";

    public Teacher() {
    }

    public Teacher(String fbUID, String full_name, long id, String email, String name_lesson, String password)
    {
        this.fbUID = fbUID;
        this.full_name = full_name;
        this.id = id;
        this.email = email;
        this.name_lesson = name_lesson;
        this.password = password;
    }

    public String getType()
    {
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

    public String getName_lesson() {
        return name_lesson;
    }

    public void setName_lesson(String name_lesson) {
        this.name_lesson = name_lesson;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
