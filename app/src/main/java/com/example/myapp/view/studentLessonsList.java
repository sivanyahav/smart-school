package com.example.myapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.myapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class studentLessonsList extends AppCompatActivity {
    private FirebaseDatabase database;
    private FirebaseAuth sAuth;
    private DatabaseReference Lessons;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_lessons_list);

        database = FirebaseDatabase.getInstance();
        sAuth = FirebaseAuth.getInstance();

        Lessons = database.getReference("Lessons");
        listView = (ListView) findViewById(R.id.lessonslistview);

        ArrayList<String> lesson_names=new ArrayList<>();
        List<Map<String, String>> listArray = new ArrayList<>();
        ArrayAdapter arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,lesson_names);

        Lessons.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    if(ds.child("students_CourseGrade").hasChild(Objects.requireNonNull(sAuth.getUid()))){
                        lesson_names.add(ds.getKey());
                    }
                }

                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}