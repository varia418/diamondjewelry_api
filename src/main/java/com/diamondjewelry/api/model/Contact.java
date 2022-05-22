package com.diamondjewelry.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("contacts")
public class Contact {
    @Id
    private String id;
    @Field("sender_name")
    private String senderName;
    @Field("sender_email")
    private String senderEmail;
    @Field("content")
    private String content;

    @PersistenceConstructor
    public Contact(String senderName, String senderEmail, String content) {
        this.senderName = senderName;
        this.senderEmail = senderEmail;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id='" + id + '\'' +
                ", senderName='" + senderName + '\'' +
                ", senderEmail='" + senderEmail + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
