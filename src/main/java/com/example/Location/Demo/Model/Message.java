package com.example.Location.Demo.Model;

import java.io.Serializable;

public class Message implements Serializable{
    private static final long serialVersionUID = 1L;

    private String content;

    public Message(String string, String string2) {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
