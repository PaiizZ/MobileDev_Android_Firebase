package com.example.paiizz.loginwithfirebase_andriod;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewPosts extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference databaseRef;
    private ArrayList<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_posts);

        posts = new ArrayList<Post>();

        databaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginwithfirebaseandriod.firebaseio.com/User_Detail");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("DataSnapshot", String.valueOf(dataSnapshot.getChildrenCount()));
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot user_detail : children) {
//                    Log.i("DataSnapshot", user_detail.getValue().toString());
                    ArrayList<String> items = new ArrayList<String>();
                    for(DataSnapshot item : user_detail.getChildren()) {
                        items.add((String) item.getValue());
                    }
                    Post post = new Post(items.get(1), items.get(0));
                    Log.i("Post", post.toString());
                    posts.add(post);
                }

                adapter = new PostAdapter(posts, getApplicationContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("DataSnapshot", databaseError.toString());
            }
        });
//
        recyclerView = (RecyclerView) findViewById(R.id.posts);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }
}
