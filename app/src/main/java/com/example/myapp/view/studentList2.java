package com.example.myapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.myapp.R;
import com.example.myapp.model.Student;
import com.example.myapp.view.menuSecretary;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class studentList2 extends AppCompatActivity implements View.OnClickListener{

    private FirebaseDatabase database;
    private DatabaseReference ClassRoom;
    private Student student;
    ListView listView;
    private Button backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list2);

        database = FirebaseDatabase.getInstance();
        listView=(ListView) findViewById(R.id.listview);
        ClassRoom = database.getReference("ClassRoom");
        student=new Student();

        backbtn = findViewById(R.id.backBtn);
        backbtn.setOnClickListener(this);

        ArrayList<String> names=new ArrayList<>();
        ArrayList<String> uidList=new ArrayList<>();


        ArrayAdapter arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,names);

        Intent intent = this.getIntent();

        if(intent!=null) {
            String grade = intent.getStringExtra("grade");

            ClassRoom.child(grade).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        student = ds.getValue(Student.class);
                        names.add(student.getFull_name());
                        uidList.add(student.getFbUID());


                    }
                    listView.setAdapter(arrayAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    throw error.toException(); // don't ignore errors
                }
            });
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.backBtn) {
            Intent i = new Intent(getApplicationContext(), menuSecretary.class);
            startActivity(i);
            finish();
        }
    }
}