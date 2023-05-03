package com.example.myapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.R;
import com.example.myapp.view.menuTeacher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TeacherStudentsListLevelTwo extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase database;
    private DatabaseReference Teachers;
    private DatabaseReference Lessons;
    private DatabaseReference ClassRoom;
    private FirebaseAuth sAuth;
    private String lessonName;
    private String studentUID;
    private Button backbtn;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_student_list_level2);

        database = FirebaseDatabase.getInstance();
        listView=(ListView) findViewById(R.id.students_names_list);
        Teachers = database.getReference("Teachers");
        Lessons = database.getReference("Lessons");
        ClassRoom = database.getReference("ClassRoom");
        sAuth = FirebaseAuth.getInstance();

        backbtn = findViewById(R.id.backBtn);
        backbtn.setOnClickListener(this);


        ArrayList<String> names=new ArrayList<>();


        ArrayAdapter arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,names);

        Intent intent = this.getIntent();

        if(intent!=null)
        {

            String grade = intent.getStringExtra("grade");

            Teachers.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds: snapshot.getChildren()){
                        if(ds.hasChild(Objects.requireNonNull(sAuth.getUid()))) {
                            lessonName = ds.getKey();

                            Lessons.child(lessonName).child("students_CourseGrade").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot ds :snapshot.getChildren()){
                                        studentUID = ds.getKey();
                                        List<String> uid = new ArrayList();
                                        uid.add(studentUID);

                                        ClassRoom.child(grade).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                for (DataSnapshot dsUID: snapshot.getChildren()){
                                                    if(uid.contains(dsUID.getKey())){
                                                        names.add(dsUID.child("full_name").getValue().toString());
                                                    }
                                                }
                                                listView.setAdapter(arrayAdapter);
                                            }
                                            //}

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    throw error.toException(); // don't ignore errors
                                }
                            });


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
        if(v.getId() == R.id.backBtn) {
            Intent i = new Intent(getApplicationContext(), menuTeacher.class);
            startActivity(i);
            finish();
        }
    }
}
