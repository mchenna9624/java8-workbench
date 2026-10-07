package com.example.workbench.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderNote {

    private String author;
    private String text;
    private String recordedAt;

    public OrderNote() {
    }

    public OrderNote(String author, String text) {
        this.author = author;
        this.text = text;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.recordedAt = format.format(new Date());
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(String recordedAt) {
        this.recordedAt = recordedAt;
    }
}
