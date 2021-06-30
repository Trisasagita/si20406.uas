package com.example.mycontacts;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    public ContactssAdapter mAdapter;
    public static final int CONTACTLOADER = 0;

    RecyclerView recyclerView;
    ArrayList<CONTACTS> mData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.list);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        // Read from the database

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Data").child("contacts");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("scann", " is: " + dataSnapshot.getChildrenCount());

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Log.d("scann", " is: " + dataSnapshot1);
                    String name = "", phone = "", email = "", photo = "", type = "";
                    if (dataSnapshot1.getKey().equals("name")) {
                        name = dataSnapshot1.getValue(String.class);
                        Log.d("scann", "Name is: " + name);

                    }
                    if (dataSnapshot1.getKey().equals("phone")) {
                        phone = dataSnapshot1.getValue(String.class);

                    }
                    if (dataSnapshot1.getKey().equals("email")) {
                        email = dataSnapshot1.getValue(String.class);

                    }
                    if (dataSnapshot1.getKey().equals("type")) {
                        type = dataSnapshot1.getValue(String.class);

                    }
                    if (dataSnapshot1.getKey().equals("photo")) {
                        photo = dataSnapshot1.getValue(String.class);

                    }
                    mData.add(new CONTACTS(name, phone, email, photo, type));

                }
//                String value = dataSnapshot.getValue(String.class);
//                Log.d("TAG", "Value is: " + value);
                if(dataSnapshot.getChildrenCount()==mData.size()){
                    setData(mData);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });


        // whenever we press a listview for updating
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
//                Uri newUri = ContentUris.withAppendedId(Contract.ContactEntry.CONTENT_URI, id);
//                intent.setData(newUri);
//                startActivity(intent);
//
//            }
//        });
    }

    void setData(ArrayList<CONTACTS> mData) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ContactssAdapter(this, mData);
        recyclerView.setAdapter(mAdapter);
    }


    public class ContactssAdapter extends RecyclerView.Adapter<ContactssAdapter.ViewHolder> {

        private ArrayList<CONTACTS> mData;
        private LayoutInflater mInflater;

        ContactssAdapter(Context context, ArrayList<CONTACTS> data) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.listitem, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String name = mData.get(position).getName();
            String phone = mData.get(position).getPhone();
            String email = mData.get(position).getEmail();
            String photo = mData.get(position).getPhoto();
            String type = mData.get(position).getType();
            holder.itemName.setText(name);
            holder.itemEmail.setText(email);
            holder.itemType.setText(type);
            holder.itemPhone.setText(phone);
            //we will set photo in last

        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView itemName, itemPhone, itemEmail, itemType;

            RelativeLayout relativeLayout;
            CircleImageView itemPhoto;

            ViewHolder(View itemView) {
                super(itemView);
                itemName = itemView.findViewById(R.id.textName);
                itemPhone = itemView.findViewById(R.id.textNumber);
                itemPhoto = itemView.findViewById(R.id.imageContact);
                itemType = itemView.findViewById(R.id.textTypeofContact);
                itemEmail = itemView.findViewById(R.id.textEmail);


            }
        }
    }


}