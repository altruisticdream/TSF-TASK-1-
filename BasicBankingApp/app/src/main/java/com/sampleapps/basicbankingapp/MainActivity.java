package com.sampleapps.basicbankingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.sampleapps.basicbankingapp.data.Contract;
import com.sampleapps.basicbankingapp.data.HelperClass;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String countQuery = "SELECT  * FROM " + Contract.USER_TABLE_NAME;
        HelperClass helperClass=new HelperClass(this);
        SQLiteDatabase db = helperClass.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        if (count==0) {
            ArrayList<User> users = new ArrayList<>();
            users.add(new User("Ashutosh", "Ashutosh@gmail.com", 555500,this));
            users.add(new User("Atul", "Atul@gmail.com", 30000,this));
            users.add(new User("Amresh", "Amresh@gmail.com", 45000,this));
            users.add(new User("Manish", "Manish@gmail.com", 24500,this));
            users.add(new User("Ashish", "Ashish@gmail.com", 25000,this));
            users.add(new User("Harsh", "Harsh@gmail.com", 42000,this));
            users.add(new User("Urishita", "Urishita@gmail.com", 12500,this));
            users.add(new User("Prerna", "Prerna@gmail.com", 42200,this));
            users.add(new User("Keshav", "Keshav@gmail.com", 12300,this));
            users.add(new User("Adarsh", "Adarsh@gmail.com", 32100,this));


            for (User i:users)
            {
                helperClass.insertUser(i.getName(),i.getEmail(),Double.toString(i.getCurrentBalance()));
            }
        }


    }

    public void displayUsers(View view)
    {
        Intent intent=new Intent(MainActivity.this,UserListActivity.class);
        startActivity(intent);
    }
    public void displayTransactions(View view)
    {
        Intent intent=new Intent(MainActivity.this,TransactionListActivity.class);
        intent.putExtra("for user","all");
        startActivity(intent);
    }

}