package com.example.myapp.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.R;
import com.example.myapp.view.menuTeacher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class updatePresence extends AppCompatActivity implements View.OnClickListener{


    private FirebaseDatabase database;
    private DatabaseReference ClassRoom;
    private DatabaseReference Lessons;
    private FirebaseAuth sAuth;


    private Button updateBtn, backbtn , minus , plus;
    private TextView fullName, id ,absences;
    String lesson_name, uid , abs ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_presence);


        database = FirebaseDatabase.getInstance();
        ClassRoom = database.getReference("ClassRoom");
        Lessons = database.getReference("Lessons");
        sAuth = FirebaseAuth.getInstance();

        updateBtn=findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(this);

        backbtn = findViewById(R.id.backBtn);
        backbtn.setOnClickListener(this);

        minus = findViewById(R.id.minus);
        minus.setOnClickListener(this);

        plus = findViewById(R.id.plus);
        plus.setOnClickListener(this);

        fullName = findViewById(R.id.fillName);
        id = findViewById(R.id.fillID);
        absences = findViewById(R.id.to_fill);


        Intent intent = this.getIntent();

        if(intent!=null)
        {


            String full_name = intent.getStringExtra("full_name");
            String classGrade = intent.getStringExtra("classGrade");
            uid = intent.getStringExtra("uid");
            lesson_name = intent.getStringExtra("lessonName");

            fullName.setText(full_name);



            Lessons.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds: snapshot.getChildren()){
                        if(ds.child("students_CourseGrade").hasChild(Objects.requireNonNull(uid)))
                        {
                            abs=Objects.requireNonNull(ds.child("students_CourseGrade").
                                    child(Objects.requireNonNull(uid)).child("Absence").getValue().toString());
                            absences.setText(abs);


                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });





            ClassRoom.child(classGrade).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds: snapshot.getChildren()){
                        if(ds.getKey().equals(uid)){
                            id.setText(ds.child("id").getValue().toString());
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

    }


    @Override
    public void onClick(View v) {
        int new_abs= Integer.parseInt(abs);
        String finalABS="";
        switch (v.getId())
        {
            case R.id.plus:
                new_abs = Integer.parseInt(abs) +1;
                finalABS = new_abs+"";
                Lessons.child(lesson_name).child("students_CourseGrade").child(uid).child("Absence").setValue(finalABS);
                break;

            case R.id.minus:
                new_abs = Integer.parseInt(abs) -1;
                if(new_abs<0)
                {
                    Toast.makeText(updatePresence.this, "Can not be negative! " , Toast.LENGTH_SHORT).show();
                    Toast.makeText(updatePresence.this, "upgrade UnSuccessfully " , Toast.LENGTH_SHORT).show();
                    return;
                }
                finalABS = new_abs+"";
                Lessons.child(lesson_name).child("students_CourseGrade").child(uid).child("Absence").setValue(finalABS);
                break;
            case R.id.updateBtn: //update student grade
                Toast.makeText(updatePresence.this, "upgrade Successfully " , Toast.LENGTH_SHORT).show();
                break;

            case R.id.backBtn:
                Intent i = new Intent(getApplicationContext(), menuTeacher.class);
                startActivity(i);
                finish();
                break;
        }
    }
}