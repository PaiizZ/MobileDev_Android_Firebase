package com.example.paiizz.loginwithfirebase_andriod;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class NewPost extends AppCompatActivity {

    Button selectImgBtn, postBtn;
    ImageView selectedImg;
    EditText sayEditText;

//    public static final int READ_EXTERNAL_STORAGE = 0;
    private static final int GALLERY_INTENT = 2;
    private ProgressBar progressBar;
    private Uri imageURI = null;
    private DatabaseReference databaseRef;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        selectImgBtn = (Button) findViewById(R.id.selectImgBtn);
        selectedImg = (ImageView) findViewById(R.id.selectedImg);
        sayEditText = (EditText) findViewById(R.id.sayEditText);
        postBtn = (Button) findViewById(R.id.postBtn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        selectImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accessGallery();
            }
        });

        //Initialize Firebase stuffs
        databaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginwithfirebaseandriod.firebaseio.com/").child("User_Detail").push();
        storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://loginwithfirebaseandriod.appspot.com/");

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String saySth = sayEditText.getText().toString().trim();
                if(saySth.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please, say something.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(imageURI.toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please, select an image.", Toast.LENGTH_SHORT).show();
                    return;
                }
                postBtn.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                DatabaseReference childRef = databaseRef.child("image_title");
                childRef.setValue(saySth);
                Toast.makeText(getApplicationContext(), "Your post has been published.", Toast.LENGTH_SHORT).show();
                StorageReference imgStorage = storageRef.child("images").child(imageURI.getLastPathSegment());
                imgStorage.putFile(imageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadURI = taskSnapshot.getDownloadUrl();
                        databaseRef.child("image_url").setValue(downloadURI.toString());
                        postBtn.setVisibility(View.VISIBLE);
                        postBtn.setEnabled(false);
                        progressBar.setProgress(progressBar.getMax());
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
    }

    private void accessGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_INTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            imageURI = data.getData();
            selectedImg.setImageURI(imageURI);
        }
    }
}
