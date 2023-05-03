
package com.example.myapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.myapp.R;
import com.example.myapp.controller.gradesTeacher;
import com.example.myapp.controller.presenceTeacher;
import com.google.firebase.auth.FirebaseAuth;

public class menuTeacher extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout Grades ,  Presence , Profile,  StudentsList;
    Button LogoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_teacher);

        Grades=findViewById(R.id.Grades) ;
        Grades.setOnClickListener(this);

        Presence=findViewById(R.id.Presence);
        Presence.setOnClickListener(this);

        Profile= findViewById(R.id.Profile);
        Profile.setOnClickListener(this);

        StudentsList = findViewById(R.id.StudentsList);
        StudentsList.setOnClickListener(this);

        LogoutBtn = findViewById(R.id.LogoutBtn);
        LogoutBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.Grades:
                startActivity(new Intent(this, gradesTeacher.class));
                break;
            case R.id.Presence:
                startActivity(new Intent(this, presenceTeacher.class));
                break;
            case R.id.Profile:
                startActivity(new Intent(this, profileTeacher.class));
                break;
            case R.id.StudentsList:
                startActivity(new Intent(this, TeacherStudentsList.class));
                break;
            case R.id.LogoutBtn:
                AlertDialog.Builder builder=new AlertDialog.Builder(menuTeacher.this); //Home is name of the activity
                builder.setMessage("Do you want to exit?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        Intent i=new Intent(menuTeacher.this , signIn.class);
                        i.putExtra("finish", true);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                        startActivity(i);
                        finish();

                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert=builder.create();
                alert.show();


                break;

        }
    }

}