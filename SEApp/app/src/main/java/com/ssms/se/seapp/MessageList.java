package com.ssms.se.seapp;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MessageList extends AppCompatActivity implements View.OnClickListener {

    private ListView lv;

    // Listview Adapter
    ArrayAdapter<String> adapter;
    MainListAdapter mainListAdapter;
    Button reset;
    Context context;

    ArrayList<MessageData> temp = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_message_list);
        

        temp.add(new MessageData("AAAAA", "Meeting", "Meeting today at 4:30", 30, System.currentTimeMillis()));
        temp.add(new MessageData("BBBBB", "Hey", "Meeting today at 4:30", 100, System.currentTimeMillis()));
        temp.add(new MessageData("CCCCC", "Program", "Meeting today at 4:30", 200, System.currentTimeMillis()));

        mainListAdapter = new MainListAdapter(temp,this);
        lv = (ListView) findViewById(R.id.list_view);
        lv.setAdapter(mainListAdapter);

        reset = (Button) findViewById(R.id.reset_list);
        reset.setOnClickListener(this);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.reset_list:
                ArrayList<MessageData> temp = new ArrayList<>();
                temp.add(new MessageData("AAAAA", "Meeting", "Meeting today at 4:30", 30, System.currentTimeMillis()));
                temp.add(new MessageData("BBBBB", "Hey", "Meeting today at 4:30", 100, System.currentTimeMillis()));
                temp.add(new MessageData("CCCCC", "Program", "Meeting today at 4:30", 200, System.currentTimeMillis()));
                mainListAdapter = new MainListAdapter(temp,this);
                lv.setAdapter(mainListAdapter);
                break;
        }
    }
}
