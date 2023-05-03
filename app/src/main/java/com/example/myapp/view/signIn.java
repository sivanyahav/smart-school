package com.example.myapp.view;

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
import com.example.myapp.model.Secretary;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class signIn extends AppCompatActivity implements View.OnClickListener {

    private TextView  signInBtn;

    private FirebaseUser fUser;
    private FirebaseDatabase database;
    private FirebaseAuth sAuth;

    private DatabaseReference ClassRoom;
    private DatabaseReference Teachers;
    private DatabaseReference Secretariat;

    private EditText  Email , Password ;
    private String email , pass ;
    private boolean isTeacher, value1 , value2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        database = FirebaseDatabase.getInstance();
        sAuth = FirebaseAuth.getInstance();
        fUser = sAuth.getCurrentUser();


        signInBtn=(Button) findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(this);


        Email = (EditText)findViewById(R.id.email);
        Password = (EditText)findViewById(R.id.PasswordTeacher);
        isTeacher=false;


        Secretariat=database.getReference("Secretariat");
        ClassRoom = database.getReference("ClassRoom");
        Teachers = database.getReference("Teachers");


    }

    @Override
    public void onClick(View v) {
        email = Email.getText().toString();
        pass = Password.getText().toString();

        switch (v.getId())
        {
            case R.id.signInBtn:
                if(email.equals("sivan@gmail.com")|| email.equals("yoel@gmail.com") || email.equals("koral@gmail.com"))
                {
                    loginSecretariat();
                }
                else
                { loginOther(); }
                break;
        }
    }


    private void loginOther()
    {
        sAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    fUser = sAuth.getCurrentUser();
                    Teachers.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds: snapshot.getChildren()){
                                if (fUser != null) {
                                    if (ds.hasChild(fUser.getUid()))
                                        isTeacher = true;
                                }
                            }

                            if(isTeacher) {
                                Toast.makeText(signIn.this, "Login Teacher Successfully " , Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getApplicationContext(), menuTeacher.class);
                                startActivity(i);
                            }

                            else { //its a student
                                Toast.makeText(signIn.this, "Login Student Successfully " , Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getApplicationContext(), menuStudent.class);
                                startActivity(i);
                            }
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
                else
                {
                    Toast.makeText(signIn.this, "Login Unsuccessfully - Go to the Secretary  " , Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void loginSecretariat()
    {
        sAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                fUser = sAuth.getCurrentUser();
                if (fUser != null) {
                    Secretary temp = new Secretary(fUser.getUid(), email, pass);
                    if (task.isSuccessful()) {
                        Toast.makeText(signIn.this, "Login Secretariat Successfully ", Toast.LENGTH_LONG).show();
                        Secretariat.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (!snapshot.hasChild(fUser.getUid())) {
                                    Secretariat.child(fUser.getUid()).setValue(temp);
                                    Secretariat.child(fUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                Intent i = new Intent(getApplicationContext(), menuSecretary.class);
                                                startActivity(i);
                                                finish();
                                            }
                                        }
                                    });
                                } else {
                                    Secretariat.child(fUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                Intent i = new Intent(getApplicationContext(), menuSecretary.class);
                                                startActivity(i);
                                                finish();
                                            }
                                        }
                                    });


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    } else {
                        Toast.makeText(signIn.this, "Login Unsuccessfully", Toast.LENGTH_LONG).show();
                    }
                }
            }

        });
        database.getReference();
    }
}