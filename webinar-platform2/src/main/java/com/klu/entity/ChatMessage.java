package com.klu.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ChatMessage {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long webinarId;
    private String senderEmail;
    private String content;
    private LocalDateTime timestamp = LocalDateTime.now();

    // Getters and Setters
    public Long getWebinarId() { return webinarId; }
    public void setWebinarId(Long webinarId) { this.webinarId = webinarId; }
    public String getSenderEmail() { return senderEmail; }
    public void setSenderEmail(String senderEmail) { this.senderEmail = senderEmail; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}