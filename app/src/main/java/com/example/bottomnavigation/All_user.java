package com.example.bottomnavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class All_user extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    ListView listView;
    String userid;
    List<model> USERS;
    List<String> Allusers;
    ArrayAdapter adapter,Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        listView = findViewById(R.id.Showalluser);
        USERS = new ArrayList<>();
        Allusers=new ArrayList<>();
        userid = firebaseAuth.getCurrentUser().getUid();
        ShowUser();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),Personal_chat.class);
                intent.putExtra("KEY",USERS.get(position).getKEY());
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }




    private void ShowUser() {
        firebaseDatabase.getReference("USERS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    Map show= (Map) dataSnapshot1.getValue();

                    model model=new model(show.get("NAME").toString(),show.get("KEY").toString());
                  USERS.add(model);
                  Allusers.add(model.getNAME());

                }
                adapter= new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, USERS);
                Adapter= new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, Allusers);
                listView.setAdapter(Adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        return super.onOptionsItemSelected(item);
    }
}

