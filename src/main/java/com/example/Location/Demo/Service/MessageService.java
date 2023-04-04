package com.example.Location.Demo.Service;

import java.util.List;

import com.example.Location.Demo.Model.Message;

public interface MessageService {

    void sendMessage(Message message);

    List<Message> getAllMessages();

    void clearAllMessages();

    void startListening();

    void stopListening();
}