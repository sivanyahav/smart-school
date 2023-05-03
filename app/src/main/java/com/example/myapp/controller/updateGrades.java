package com.example.myapp.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.R;
import com.example.myapp.view.menuTeacher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class updateGrades extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase database;
    private DatabaseReference ClassRoom;
    private DatabaseReference Lessons;

    private Button updateBtn, backbtn;
    private TextView fullName, id ;
    private EditText  grade;
    String lesson_name, uid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_grades);

        database = FirebaseDatabase.getInstance();
        ClassRoom = database.getReference("ClassRoom");
        Lessons = database.getReference("Lessons");

        updateBtn=findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(this);

        backbtn = findViewById(R.id.backBtn);
        backbtn.setOnClickListener(this);

        fullName = findViewById(R.id.fillName);
        id = findViewById(R.id.fillID);
        grade = (EditText)findViewById(R.id.fillGrade);


        Intent intent = this.getIntent();

        if(intent!=null)
        {


            String full_name = intent.getStringExtra("full_name");
            String classGrade = intent.getStringExtra("classGrade");
            uid = intent.getStringExtra("uid");
            lesson_name = intent.getStringExtra("lessonName");

            fullName.setText(full_name);

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

        switch (v.getId())
        {
            case R.id.updateBtn: //update student grade
                String given_grade = grade.getText().toString();
                if(given_grade.equals("")){
                    Toast.makeText(updateGrades.this, "nothing entered " , Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(given_grade)<0)
                {

                    Toast.makeText(updateGrades.this, "Grade can't be negative! " , Toast.LENGTH_SHORT).show();
                    Toast.makeText(updateGrades.this, "upgrade failed " , Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Lessons.child(lesson_name).child("students_CourseGrade").child(uid).child("Grade").setValue(given_grade);
                    Toast.makeText(updateGrades.this, "upgrade grade Successfully ", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.backBtn:
                Intent i = new Intent(getApplicationContext(), menuTeacher.class);
                startActivity(i);
                finish();
                break;
        }
    }
}