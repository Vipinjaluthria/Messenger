package com.example.bottomnavigation;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Fragment_Status extends Fragment {
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    FloatingActionButton addstatusflaotingbtn;
    int requestcodeintent=1;
    FirebaseStorage firebaseStorage;
    String userid;
    StorageReference storageReference;
    Uri Imagestatus;
    CardView setstaus;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       firebaseAuth=FirebaseAuth.getInstance();
       firebaseDatabase=FirebaseDatabase.getInstance();
       firebaseStorage=FirebaseStorage.getInstance();
        View view=inflater.inflate(R.layout.fragment_status,container,false);

        setstaus=view.findViewById(R.id.setstatus);
        addstatusflaotingbtn=view.findViewById(R.id.addstatusfloatingbtn);
       userid=firebaseAuth.getCurrentUser().getUid();
        setstaus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              checkfilepermission();

            }
        });
        addstatusflaotingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Addstatus();
            }
        });
        return view;




    }

    private void checkfilepermission() {
        if(ContextCompat.checkSelfPermission(getContext(),Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(getContext(),Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {

            Addstatus();


        }
        else {
            ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},12);


        }
        }

    private void Addstatus() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,requestcodeintent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==requestcodeintent  && data!=null && data.getData()!=null)
        {
            Imagestatus =data.getData();
            UploadStatus();
        }

    }

    private String getFileExtension(Uri uri) {

        ContentResolver contentResolver = getContext().getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getMimeTypeFromExtension(contentResolver.getType(uri));

    }

    private void UploadStatus() {

        String pushkey=firebaseDatabase.getReference("STATUS").child(userid).push().getKey();
        storageReference= firebaseStorage.getReference("STATUS").child(userid).child(System.currentTimeMillis()+"."+getFileExtension(Imagestatus));
        storageReference.putFile(Imagestatus).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
            }
        });
        Map<String, Object>Images = new HashMap<>();
        Images.put("Images","Imagestatus");
        firebaseDatabase.getReference("STATUS").child(userid).child(pushkey).updateChildren((Images));


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==12)
        {
            if(grantResults[0]+grantResults[1]==PackageManager.PERMISSION_GRANTED)
            {

            }
            else {
                ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},12);


            }
        }
    }
}
