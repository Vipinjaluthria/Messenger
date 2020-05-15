package com.example.bottomnavigation;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageViewholder extends RecyclerView.ViewHolder {
    public MessageViewholder(@NonNull View itemView) {
        super(itemView);
    }

    void setMessageFromMe(String message)
    {
        TextView textView=itemView.findViewById(R.id.fromMeMessage);
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundColor(Color.WHITE);
        textView.setText(message);
    }
    void setMessageTo(String messageTo)
    {

        TextView textView=itemView.findViewById(R.id.fromMeMessage);
        textView.setTextColor(Color.BLACK);

        textView.setBackgroundColor(Color.WHITE);
        textView.setText(messageTo);
    }
}
