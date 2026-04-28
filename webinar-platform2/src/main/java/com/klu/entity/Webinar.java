package com.klu.entity;

import jakarta.persistence.*;
import java.util.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "webinar")
public class Webinar {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "date_time")
    @JsonProperty("dateTime") // Matches React "dateTime"
    private LocalDateTime dateTime;

    @Column(name = "meeting_link")
    @JsonProperty("meetingLink") // Matches React "meetingLink"
    private String meetingLink;

    @Column(name = "recording_url", length = 500)
    private String recordingUrl;

    @Column(name = "resource_material")
    private String resourceMaterial;

    @Column(name = "is_live")
    private boolean isLive = false;

    @ElementCollection
    @CollectionTable(name = "webinar_allowed_speakers", joinColumns = @JoinColumn(name = "webinar_id"))
    @Column(name = "speaker_email")
    private List<String> allowedSpeakers = new ArrayList<>();

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    public String getMeetingLink() { return meetingLink; }
    public void setMeetingLink(String meetingLink) { this.meetingLink = meetingLink; }
    public String getRecordingUrl() { return recordingUrl; }
    public void setRecordingUrl(String recordingUrl) { this.recordingUrl = recordingUrl; }
    public String getResourceMaterial() { return resourceMaterial; }
    public void setResourceMaterial(String resourceMaterial) { this.resourceMaterial = resourceMaterial; }
    public boolean isLive() { return isLive; }
    public void setLive(boolean live) { isLive = live; }
    public List<String> getAllowedSpeakers() { return allowedSpeakers; }
    public void setAllowedSpeakers(List<String> allowedSpeakers) { this.allowedSpeakers = allowedSpeakers; }
}