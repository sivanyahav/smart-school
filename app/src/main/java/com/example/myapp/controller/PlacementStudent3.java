package com.example.myapp.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PlacementStudent3 extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference Teachers;
    private String name_lesson;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement_student3);

        database = FirebaseDatabase.getInstance();
        listView=(ListView) findViewById(R.id.listview);
        Teachers = database.getReference("Teachers");

        ArrayList<String> LessonList=new ArrayList<>();
        ArrayList<String> uidList=new ArrayList<>();


        Intent intent = this.getIntent();

        if(intent!=null) {
            String grade = intent.getStringExtra("grade");
            String uid=intent.getStringExtra("uid");
            String email=intent.getStringExtra("email");

            ArrayList<String> gradeList=new ArrayList<>();


            ArrayAdapter arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,LessonList);
            Teachers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds :snapshot.getChildren())
                    {
                        name_lesson=ds.getKey();
                        LessonList.add(name_lesson);

                    }
                    listView.setAdapter(arrayAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {

                    Intent i = new Intent(getApplicationContext(),PlacementStudent4.class);
                    i.putExtra("grade",grade);
                    i.putExtra("uidStudent",uid);
                    i.putExtra("emailStudent",email);
                    i.putExtra("Lesson_name",LessonList.get(position));



                    startActivity(i);

                }
            });
        }
    }
}