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

public class presenceTeacher extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference ClassRoom;
    private String grade;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presence_teacher);


        database = FirebaseDatabase.getInstance();
        listView=(ListView) findViewById(R.id.listview);
        ClassRoom = database.getReference("ClassRoom");


        ArrayList<String> gradeList=new ArrayList<>();


        ArrayAdapter arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,gradeList);
        ClassRoom.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds :snapshot.getChildren())
                {
                    grade=ds.getKey();
                    gradeList.add(grade);

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

                Intent i = new Intent(getApplicationContext(),updatePresence_levelTwo.class);
                i.putExtra("grade",gradeList.get(position));
                startActivity(i);

            }
        });
    }
}