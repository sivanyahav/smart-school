package com.example.myapp.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Lesson implements Serializable
{
    private String details;
    private Map<String,Map<String,String>> studentsUID_Grade_Absence;
    private Map<String,String> teacher_LessonName;

    public Lesson(){}

    public Lesson(String details, Map<String, Map<String, String>> studentsUID_Grade_Absence, Map<String, String> teacher_LessonName) {
        this.details = details;
        this.studentsUID_Grade_Absence = studentsUID_Grade_Absence;
        this.teacher_LessonName = teacher_LessonName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Map<String, Map<String, String>> getStudentsUID_Grade_Absence() {
        return studentsUID_Grade_Absence;
    }

    public void setStudentsUID_Grade_Absence(Map<String, Map<String, String>> studentsUID_Grade_Absence) {
        this.studentsUID_Grade_Absence = studentsUID_Grade_Absence;
    }

    public Map<String, String> getTeacher_LessonName() {
        return teacher_LessonName;
    }

    public void setTeacher_LessonName(Map<String, String> teacher_LessonName) {
        this.teacher_LessonName = teacher_LessonName;
    }
}