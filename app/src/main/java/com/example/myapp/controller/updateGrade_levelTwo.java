package com.example.myapp.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class updateGrade_levelTwo extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference Teachers;
    private DatabaseReference Lessons;
    private DatabaseReference ClassRoom;
    private FirebaseAuth sAuth;
    private String lessonName;
    private String studentUID;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_grades_level2);

        database = FirebaseDatabase.getInstance();
        listView=(ListView) findViewById(R.id.students_grade_list);
        Teachers = database.getReference("Teachers");
        Lessons = database.getReference("Lessons");
        ClassRoom = database.getReference("ClassRoom");
        sAuth = FirebaseAuth.getInstance();


        ArrayList<String> names=new ArrayList<>();
        ArrayList<String> uidList=new ArrayList<>();


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
                                                            uidList.add(dsUID.getKey());
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
                                    //listView.setAdapter(arrayAdapter);
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


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {

                    Intent i = new Intent(getApplicationContext(),updateGrades.class);
                    i.putExtra("uid",uidList.get(position));
                    i.putExtra("classGrade",grade);
                    i.putExtra("lessonName",lessonName);
                    i.putExtra("full_name",names.get(position));
                    startActivity(i);
                    finish();

                }
            });

        }


    }
}
