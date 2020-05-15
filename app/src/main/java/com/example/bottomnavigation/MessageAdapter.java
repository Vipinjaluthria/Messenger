package com.example.bottomnavigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageViewholder> {
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();

    private List<Messages> list;

    public MessageAdapter(List<Messages> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MessageViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.messagesend, parent, false);
        return new MessageViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewholder holder, int position) {
        if(list.get(position).getFROM().equals(firebaseAuth.getCurrentUser().getUid())) {

            holder.setMessageTo(list.get(position).getMESSAGE());
        }
        else
        {

            holder.setMessageTo(list.get(position).getMESSAGE());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
