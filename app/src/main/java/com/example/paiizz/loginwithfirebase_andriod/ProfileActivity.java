package com.example.paiizz.loginwithfirebase_andriod;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView emailText;
    private Button signOutBtn, newPostBtn, viewPostsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        emailText = (TextView) findViewById(R.id.emailText);
        signOutBtn = (Button) findViewById(R.id.signOutBtn);
        newPostBtn = (Button) findViewById(R.id.newPostBtn);
        viewPostsBtn = (Button) findViewById(R.id.viewPostsBtn);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String email = user.getEmail();
        emailText.setText(email);

        newPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), NewPost.class);
            startActivity(intent);
            }
        });
        viewPostsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewPosts.class);
                startActivity(intent);
            }
        });
    }

    public void signOut() {
        mAuth.signOut();
        finish();
    }
}