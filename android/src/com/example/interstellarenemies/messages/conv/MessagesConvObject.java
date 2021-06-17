package com.example.interstellarenemies.messages.conv;

public class MessagesConvObject  {
    String userName;
    String sender, receiver, message;

    public MessagesConvObject(String sender, String receiver, String message, String userName) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.userName = userName;
    }
}