package com.example.myapp.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.R;
import com.example.myapp.model.Teacher;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;


public class profileTeacher extends AppCompatActivity  {

    private FirebaseDatabase database;
    private FirebaseAuth sAuth;
    private DatabaseReference Teachers;


    private TextView fullName, id, lessonName, email;
    private  Button changeProfilePicture;
    private ImageView profileImage;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_teacher);

        database = FirebaseDatabase.getInstance();
        sAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("users/+"+sAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });

        Teachers = database.getReference("Teachers");
        fullName = findViewById(R.id.fill1);
        id = findViewById(R.id.fill2);
        lessonName = findViewById(R.id.fill3);
        email = findViewById(R.id.fill4);

        profileImage = findViewById(R.id.profileImage);
        changeProfilePicture = findViewById(R.id.ChangePicture);

        Teachers.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    if(ds.hasChild(Objects.requireNonNull(sAuth.getUid()))){
                        Teacher t = new Teacher();
                        fullName.setText(Objects.requireNonNull(ds.child(sAuth.getUid()).getValue(Teacher.class)).getFull_name());
                        id.setText((int)Objects.requireNonNull(ds.child(sAuth.getUid()).getValue(Teacher.class)).getId()+"");
                        lessonName.setText(Objects.requireNonNull(ds.child(sAuth.getUid()).getValue(Teacher.class)).getName_lesson());
                        email.setText(Objects.requireNonNull(ds.child(sAuth.getUid()).getValue(Teacher.class)).getEmail());

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        changeProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //open gallery
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI ); //first - going to pick some data, second- from where (return the uri of pic that user click on.
                startActivityForResult(openGalleryIntent, 1000);//picking and extracting the data
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){ //means our gallery intent is invoking this on this activity result
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri =  data.getData(); //get the URI of Image the user selected
                //profileImage.setImageURI(imageUri);// set the Image
                uploadImageToFirebase(imageUri);
            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri){
        //upload images to firebase storage
        StorageReference fileRef = storageReference.child("users/+"+sAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImage);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(profileTeacher.this, "Image Uploaded failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}