package com.klu.entity;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Webinar {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private boolean isLive = false;
    private String recordingUrl;

    @ElementCollection
    private List<String> allowedSpeakers = new ArrayList<>();

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public boolean isLive() { return isLive; }
    public void setLive(boolean live) { isLive = live; }
    public String getRecordingUrl() { return recordingUrl; }
    public void setRecordingUrl(String recordingUrl) { this.recordingUrl = recordingUrl; }
    public List<String> getAllowedSpeakers() { return allowedSpeakers; }
    public void setAllowedSpeakers(List<String> allowedSpeakers) { this.allowedSpeakers = allowedSpeakers; }
}