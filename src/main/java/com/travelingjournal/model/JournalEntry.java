package com.travelingjournal.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JournalEntry {
    private Long id;
    private Long tripId;
    private LocalDate entryDate;
    private String title;
    private String content;
    private String location;            // e.g., "Eiffel Tower, Paris"
    private String mood;                // happy, excited, tired, etc.
    private String weather;             // sunny, rainy, 25°C
    private final List<String> photoPaths = new ArrayList<>();  // Multiple photos

    // Constructors
    public JournalEntry() {}

    public JournalEntry(Long tripId, LocalDate entryDate, String title, String content) {
        this.tripId = tripId;
        this.entryDate = entryDate;
        this.title = title;
        this.content = content;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getTripId() { return tripId; }
    public void setTripId(Long tripId) { this.tripId = tripId; }

    public LocalDate getEntryDate() { return entryDate; }
    public void setEntryDate(LocalDate entryDate) { this.entryDate = entryDate; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getMood() { return mood; }
    public void setMood(String mood) { this.mood = mood; }

    public String getWeather() { return weather; }
    public void setWeather(String weather) { this.weather = weather; }

    public List<String> getPhotoPaths() { return photoPaths; }
    public void addPhoto(String photoPath) { this.photoPaths.add(photoPath); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JournalEntry)) return false;
        JournalEntry that = (JournalEntry) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "JournalEntry{" +
                "id=" + id +
                ", tripId=" + tripId +
                ", date=" + entryDate +
                ", title='" + title + '\'' +
                ", photos=" + photoPaths.size() +
                '}';
    }
}
