package com.example.registration;
public class EmailDetails {
    private int id;
    private String sender;
    private String title;
    private String summary;
    private String time;
    private String mailbox;

    public EmailDetails(){}
    public EmailDetails(String sender, String title, String summary, String time, String mailbox) {
        this.sender = sender;
        this.title = title;
        this.summary = summary;
        this.time = time;
        this.mailbox = mailbox;
    }
    public int getId(){return id;}

    public void setId(int id){this.id = id;}

    public String getMailbox(){return mailbox;}

    public String getSender() {
        return sender;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getTime() {
        return time;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setTime(String time) {
        this.time = time;
    }
}