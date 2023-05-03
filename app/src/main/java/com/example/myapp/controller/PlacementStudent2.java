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
import com.example.myapp.model.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlacementStudent2 extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference ClassRoom;
    private Student student;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement_student2);

        database = FirebaseDatabase.getInstance();
        listView=(ListView) findViewById(R.id.listview);
        ClassRoom = database.getReference("ClassRoom");
        student=new Student();

        ArrayList<String> emailList=new ArrayList<>();
        ArrayList<String> namesList=new ArrayList<>();
        ArrayList<String> uidList=new ArrayList<>();
        Map<String,Student> studentMap=new HashMap<>();


        ArrayAdapter arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,namesList);

        Intent intent = this.getIntent();

        if(intent!=null)
        {
            String grade = intent.getStringExtra("grade");

            ClassRoom.child(grade).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds :snapshot.getChildren()){
                        student = ds.getValue(Student.class);
                        emailList.add(student.getEmail());
                        uidList.add(student.getFbUID());
                        studentMap.put(student.getFbUID(),student);
                        namesList.add(student.getFull_name());


                    }
                    listView.setAdapter(arrayAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    throw error.toException(); // don't ignore errors
                }
            });


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    Intent i = new Intent(getApplicationContext(),PlacementStudent3.class);
                    i.putExtra("grade",grade);
                    i.putExtra("uid",uidList.get(position));
                    i.putExtra("email",emailList.get(position));
                    startActivity(i);

                }
            });
        }

    }
}