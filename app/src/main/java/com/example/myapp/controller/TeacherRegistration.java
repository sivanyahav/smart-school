package com.example.myapp.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapp.R;
import com.example.myapp.view.menuSecretary;
import com.example.myapp.model.Teacher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TeacherRegistration extends AppCompatActivity implements View.OnClickListener{

    private FirebaseUser fUser;
    private FirebaseDatabase database;
    private FirebaseAuth sAuth;
    private DatabaseReference ClassRoom;
    private DatabaseReference Teachers;

    private Button signIn;
    private EditText FullName , ID , Email  , Password , LessonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_registration);

        database = FirebaseDatabase.getInstance();
        sAuth = FirebaseAuth.getInstance();
        fUser = sAuth.getCurrentUser();
        ClassRoom = database.getReference("ClassRoom");
        Teachers = database.getReference("Teachers");

        signIn=findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        FullName = findViewById(R.id.FullName);
        ID = findViewById(R.id.ID);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        LessonName= findViewById(R.id.LessonName);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.signIn:
                registerUser();
                break;
        }
    }

    private void registerUser()
    {
        String full_name=FullName.getText().toString().trim();
        String id=ID.getText().toString().trim();
        String email=Email.getText().toString().trim();
        String lesson_name=LessonName.getText().toString().trim();
        String password=Password.getText().toString().trim();

        validation(full_name,id,email,lesson_name,password);

    }

    private void signUpStudent(String full_name, String id, String email, String lesson_name, String password)
    {
        sAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {
                    Toast.makeText(TeacherRegistration.this, "Registered Successfully", Toast.LENGTH_LONG).show();

                    Teacher temp = new Teacher(fUser.getUid(),full_name,Long.parseLong(id),email,lesson_name,password);
                    Teachers.child(lesson_name).child(fUser.getUid()).setValue(temp);
                    Intent i = new Intent(getApplicationContext(), menuSecretary.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Toast.makeText(TeacherRegistration.this, "Registered unsuccessfully", Toast.LENGTH_LONG).show();
                }
            }});

        database.getReference();
    }

    private void validation(String full_name, String id, String email, String lesson_name, String password)
    {

        // מינימום שמונה תווים, לפחות אות אחת ומספר אחד:
        final String PASSWORD_PATTERN ="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

        // המילה הראשונה לפחות עם 2 אותיות וחייב להיות אחריה רווח למילה השניה שגם מכילה לפחות 2 אותיות
        final String FULLNAME_PATTERN = "^([\\w]{2,})+\\s+([\\w\\s]{2,})+$";

        // 9 מספרים בדיוק
        final String ID_PATTERN="^[0-9]{9}$";

        // יש @ ואחריו תבוא .
        final String EMAIL_PATTERN="^\\S+@\\S+\\.\\S+$";

        //            Validation full name
        if (!isValid(full_name,FULLNAME_PATTERN) && !full_name.equals(null))
        {
            Toast.makeText(TeacherRegistration.this, "InValid fullName - A full name contains two words at least 2 characters long and a space between them", Toast.LENGTH_SHORT).show();
            return;
        }



        //            Validation email
        if (!isValid(email,EMAIL_PATTERN) && !email.equals(null))
        {
            Toast.makeText(TeacherRegistration.this, "InValid email - Email contains familiar marks in order such as: @.", Toast.LENGTH_SHORT).show();
            return;
        }

        //            Validation id
        if (!isValid(id,ID_PATTERN) && !id.equals(null))
        {
            Toast.makeText(TeacherRegistration.this, "InValid id - An ID card contains exactly 9 numbers", Toast.LENGTH_SHORT).show();

            return;
        }

        //            Validation password
        if (!isValid(password,PASSWORD_PATTERN) && !password.equals(null))
        {
            Toast.makeText(TeacherRegistration.this, "InValid password - A password consists of a minimum of eight characters, at least one letter and one number:", Toast.LENGTH_SHORT).show();
            return;
        }

        //            Validation LessonName
        if (!lesson_name.equals(null) && !(lesson_name.equals("Math") || lesson_name.equals("Computers") || lesson_name.equals("English") ))
        {
            Toast.makeText(TeacherRegistration.this, "InValid LessonName - You need to insert Math/Computers/English", Toast.LENGTH_SHORT).show();
            return;
        }
        signUpStudent(full_name,id,email,lesson_name,password);

    }

    public boolean isValid(final String toCheck , final String PATTERN)
    {
        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile(PATTERN);
        matcher = pattern.matcher(toCheck);
        return matcher.matches();

    }
}