package com.klu.Controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.klu.entity.Webinar;
import com.klu.repository.WebinarRepository;

@RestController
@RequestMapping("/api/webinars")
@CrossOrigin(origins = "http://localhost:5173")
public class WebinarController {

    @Autowired
    private WebinarRepository webinarRepo;

    @GetMapping
    public List<Webinar> getAllWebinars() {
        return webinarRepo.findAll();
    }

    @PostMapping
    public Webinar createWebinar(@RequestBody Webinar webinar) {
        return webinarRepo.save(webinar);
    }

    // Admin: Toggle User Mute/Unmute
    @PutMapping("/{id}/permissions/{userEmail}")
    public Webinar togglePermission(@PathVariable Long id, @PathVariable String userEmail) {
        Webinar w = webinarRepo.findById(id).orElseThrow();
        List<String> speakers = w.getAllowedSpeakers();
        if(speakers.contains(userEmail)) {
            speakers.remove(userEmail);
        } else {
            speakers.add(userEmail);
        }
        w.setAllowedSpeakers(speakers);
        return webinarRepo.save(w);
    }

    // Admin: Save Recording and End Live
    @PostMapping("/{id}/record")
    public Webinar saveRecording(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Webinar w = webinarRepo.findById(id).orElseThrow();
        w.setRecordingUrl(body.get("url"));
        w.setLive(false);
        return webinarRepo.save(w);
    }
}