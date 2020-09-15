package com.example.chatappadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AppointmentActivity extends AppCompatActivity {
ListView listView;
DatabaseReference ref;
ArrayList<String>arrayList;
ArrayAdapter<String>adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        final DateFormat format=new SimpleDateFormat("dd MM yyyy");
        arrayList=new ArrayList<>();
        listView=findViewById(R.id.list);
        adapter=new ArrayAdapter<>(AppointmentActivity.this,R.layout.support_simple_spinner_dropdown_item,arrayList);
        listView.setAdapter(adapter);

        ref= FirebaseDatabase.getInstance().getReference().child("doctor").child("appointments");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot cd:snapshot.getChildren()){
                    String date=cd.getKey();
                    arrayList.add(date);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String key=arrayList.get(position);
                ref.child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot cd:snapshot.getChildren()){
                            String appoint=cd.getValue(String.class);
                            showMessage(appoint);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

    private void showMessage(String appoint) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage(appoint)
                .create().show();
    }
}