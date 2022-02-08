package com.sampleapps.basicbankingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;

import com.sampleapps.basicbankingapp.data.Contract;
import com.sampleapps.basicbankingapp.data.HelperClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class UserListActivity extends AppCompatActivity {


    HelperClass dbHelper;
    SQLiteDatabase db;
    ArrayList<User> users;

    private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//
            onRestart();
//
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        registerReceiver(broadcastReceiver, new IntentFilter("closeActivity"));

        getSupportActionBar().setTitle("Users");

        users = new ArrayList<>();


        dbHelper=new HelperClass(getApplicationContext());
        db=dbHelper.getReadableDatabase();


        updateScreen();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateScreen();
    }

    public void updateScreen()
    {
        users.clear();
        Cursor usercursor=db.rawQuery("SELECT * FROM "+Contract.USER_TABLE_NAME,null);
        int usernameColumn=usercursor.getColumnIndex(Contract.USER_NAME);
        int emailColumn=usercursor.getColumnIndex(Contract.EMAIL);
        int currentBalanceColumn=usercursor.getColumnIndex(Contract.CURRENT_BALANCE);

        while(usercursor.moveToNext())
        {
            String username=usercursor.getString(usernameColumn);
            String email=usercursor.getString(emailColumn);
            String currentBalance=usercursor.getString(currentBalanceColumn);

            users.add(new User(username,email,Double.parseDouble(currentBalance),this));
        }

        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        UserAdapter userAdapter=new UserAdapter(users);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userAdapter);
    }

}