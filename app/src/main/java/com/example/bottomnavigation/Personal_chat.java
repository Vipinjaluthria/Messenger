package com.example.bottomnavigation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Personal_chat extends AppCompatActivity {
    TextView text;
    EditText editsend;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    RecyclerView recyclerView;
    List<Messages> list;
    MessageAdapter messageAdapter;
    String pushkey;
    ImageButton imagesend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_chat);
        editsend=findViewById(R.id.send);
        recyclerView=findViewById(R.id.recylermessage);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        imagesend=findViewById(R.id.imagesend);
        Intent intent=getIntent();
        final String key=intent.getStringExtra("KEY");
        imagesend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messsage=editsend.getText().toString();
                Map<String,Object> data = new HashMap<>();
                data.put("TIMESTAMP", ServerValue.TIMESTAMP);
                data.put("FROM",firebaseAuth.getCurrentUser().getUid());
                data.put("MESSAGE",messsage);
                pushkey= firebaseDatabase.getReference("MESSAGES").child(firebaseAuth.getCurrentUser().getUid()).child(key).push().getKey();
                firebaseDatabase.getReference("MESSAGES").child(firebaseAuth.getCurrentUser().getUid()).child(key).child(pushkey).updateChildren(data);
                firebaseDatabase.getReference("MESSAGES").child(key).child(firebaseAuth.getCurrentUser().getUid()).child(pushkey).updateChildren(data);
                editsend.setText("");
            }
        });
        list=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseDatabase.getReference("MESSAGES").child(firebaseAuth.getCurrentUser().getUid()).child(key).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Map data= (Map) dataSnapshot.getValue();
                Messages messages=new Messages(data.get("MESSAGE").toString(),data.get("TIMESTAMP").toString(),data.get("FROM").toString());
                list.add(messages);


            messageAdapter=new MessageAdapter(list);
                messageAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(messageAdapter);
                recyclerView.setHasFixedSize(true);

        }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
