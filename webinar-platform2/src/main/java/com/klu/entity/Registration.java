package com.klu.entity;

import jakarta.persistence.*;

@Entity
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long webinarId;
    private String userEmail;
    private String userName;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getWebinarId() { return webinarId; }
    public void setWebinarId(Long webinarId) { this.webinarId = webinarId; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
}