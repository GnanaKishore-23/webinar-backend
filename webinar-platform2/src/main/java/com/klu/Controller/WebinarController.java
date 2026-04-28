package com.klu.Controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        // Set default values if needed
        if (webinar.getAllowedSpeakers() == null) {
            webinar.setAllowedSpeakers(new ArrayList<>());
        }
        return webinarRepo.save(webinar);
    }

    // Fixed: Only ONE saveRecording method
    @PostMapping("/{id}/record")
    public ResponseEntity<?> saveRecording(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return webinarRepo.findById(id).map(webinar -> {
            String url = body.get("url");
            
            System.out.println("Updating Webinar ID: " + id + " with Recording URL: " + url);

            webinar.setRecordingUrl(url);
            webinar.setLive(false); // Sets is_live to 0 in DB
            
            webinarRepo.save(webinar);
            return ResponseEntity.ok("Recording saved successfully!");
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWebinar(@PathVariable Long id) {
        return webinarRepo.findById(id).map(webinar -> {
            webinarRepo.delete(webinar);
            return ResponseEntity.ok("Webinar deleted successfully!");
        }).orElse(ResponseEntity.notFound().build());
    }
    
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
}