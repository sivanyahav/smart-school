package com.example.myapp.model;

import java.util.List;

public class Class {
    private String grade;
    private List <Lesson> lessonsList;

    public Class(String grade, List <Lesson> lessonsList) {
        this.grade = grade;
        this.lessonsList = lessonsList;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public List<Lesson> getLessonsList() {
        return lessonsList;
    }

    public void setLessonsList(List<Lesson> lessonsList) {
        this.lessonsList = lessonsList;
    }
}
