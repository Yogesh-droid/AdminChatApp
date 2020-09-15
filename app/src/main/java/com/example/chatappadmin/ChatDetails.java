package com.example.chatappadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatDetails extends AppCompatActivity {

    EditText et1;
    FloatingActionButton fb;
    RecyclerView recyclerView;
    DatabaseReference mRef;
    ArrayList<ModelClass>arrayList;
    DetailAdapter adapter;
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_details);
        s=getIntent().getExtras().getString("userName");
        initViews();
        displayMessage();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reply();
            }
        });
    }

    private void reply() {
        mRef.push().setValue(new ModelClass(et1.getText().toString(),"You"));
        FirebaseDatabase.getInstance().getReference().child("Users").child(s).push().setValue(new ModelClass(et1.getText().toString(),"Doctor"));
        et1.setText("");
    }

    private void displayMessage() {
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot cd:snapshot.getChildren()){
                    ModelClass modelClass=cd.getValue(ModelClass.class);
                    arrayList.add(modelClass);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void initViews() {
        et1=findViewById(R.id.editText);
        fb=findViewById(R.id.send);
        arrayList=new ArrayList<>();
        recyclerView=findViewById(R.id.recyle);
        mRef= FirebaseDatabase.getInstance().getReference().child("doctor").child("chat").child(s);
        adapter=new DetailAdapter(getApplicationContext(),arrayList);

    }

}