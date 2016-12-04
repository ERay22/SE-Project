package com.ssms.se.seapp;

public class MessageData {

    private String sender;
    private String subject;
    private String body;
    private int ttl;
    private long created_at;
    private long time_of_death;

    public MessageData(String sender, String subject, String body, int ttl, long created_at){
        this.sender=sender;
        this.subject=subject;
        this.body=body;
        this.ttl=ttl;
        this.created_at=created_at;

        time_of_death = created_at + (this.ttl * 1000);
    }

    public String getSender() {
        return sender;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public int getTtl() {
        return ttl;
    }

    public long getCreated_at() {
        return created_at;
    }

    public long getTime_of_death(){
        return time_of_death;
    }

    public void setTtl(int ttl){
        this.ttl=ttl;
    }
}