package com.ssms.se.seapp;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Gunner on 6/23/2016.
 */
public class MainListAdapter extends ArrayAdapter<MessageData> implements ListAdapter {

    public final static String EXTRA_MESSAGE = "com.example.gunner.projectp1";

    private List<ListElement> elements;
    private LayoutInflater lf;
    MainListAdapter mainListAdapter;
    private Handler mHandler = new Handler();
    private Runnable updateRemainingTimeRunnable = new Runnable() {
        @Override
        public void run() {
            synchronized (elements) {
                long currentTime;
                for (ListElement listElement : elements) {
                    currentTime = System.currentTimeMillis();
                    listElement.updateTimeRemaining(currentTime);
                    if(listElement.messageData.getTtl() == -1){
                        mainListAdapter.remove(listElement.messageData);
                    }
                }
            }
        }
    };

    public MainListAdapter(List<MessageData> list, Context context) {
        super(context, 0, list);
        lf = LayoutInflater.from(context);
        elements = new ArrayList<>();
        mainListAdapter=this;
        startUpdateTimer();
    }

    private void startUpdateTimer() {
        Timer tmr = new Timer();
        tmr.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(updateRemainingTimeRunnable);
            }
        }, 1000, 1000);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ListElement listElement = null;
        if (convertView == null) {
            listElement = new ListElement();
            convertView = lf.inflate(R.layout.list_item, parent, false);
            listElement.sender = (TextView) convertView.findViewById(R.id.message_list_item_sender);
            listElement.subject = (TextView) convertView.findViewById(R.id.message_list_item_subject);
            listElement.ttl = (TextView) convertView.findViewById(R.id.message_list_item_ttl);
            RelativeLayout item = (RelativeLayout) convertView.findViewById(R.id.message_list_item);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO
                }
            });
            convertView.setTag(listElement);
            synchronized (elements){
                elements.add(listElement);
            }
        } else {
            listElement = (ListElement) convertView.getTag();
        }
        listElement.setMessageData(getItem(position));
        return convertView;
    }


    private class ListElement {
        TextView sender;
        TextView subject;
        TextView ttl;
        MessageData messageData;

        public MessageData setMessageData(MessageData messageData){
            this.messageData=messageData;
            sender.setText(messageData.getSender());
            subject.setText(messageData.getSubject());
            updateTimeRemaining(System.currentTimeMillis());
            return messageData;
        }

        public void updateTimeRemaining(long currentTime) {
            long timeDiff = messageData.getTime_of_death() - currentTime;
            if (timeDiff > 0) {
                int seconds = (int) (timeDiff / 1000);
                int minutes = seconds / 60;
                int hours = minutes / 60;
                seconds = seconds % 60;
                ttl.setText(String.format("%d:%02d:%02d",hours, minutes, seconds));
                messageData.setTtl((int) timeDiff/1000);
            } else {
                ttl.setText("Expired");
                subject.setText("Expired");
                sender.setText("Expired");
                messageData.setTtl(-1);
            }
        }
    }

}