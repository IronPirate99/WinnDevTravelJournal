package com.travelingjournal.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.travelingjournal.model.JournalEntry;
import com.travelingjournal.util.DatabaseUtil;
import org.bson.Document;

import java.sql.Date;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class EntryDAO {

    private MongoCollection<Document> getEntryCollection() {
        return DatabaseUtil.getDatabase().getCollection("journal_entries");
    }

    public Long save(JournalEntry entry) {
        if (entry.getId() == null) {
            entry.setId(DatabaseUtil.getNextSequence("entries"));
        }
        Document doc = new Document("id", entry.getId())
                .append("trip_id", entry.getTripId())
                .append("title", entry.getTitle())
                .append("content", entry.getContent())
                .append("location", entry.getLocation())
                .append("mood", entry.getMood())
                .append("weather", entry.getWeather());

        if (entry.getEntryDate() != null) {
            doc.append("entry_date", Date.valueOf(entry.getEntryDate()));
        }
        if (!entry.getPhotoPaths().isEmpty()) {
            doc.append("photos", entry.getPhotoPaths());
        }

        getEntryCollection().insertOne(doc);
        return entry.getId();
    }

    public List<JournalEntry> findByTripId(Long tripId) {
        List<JournalEntry> entries = new ArrayList<>();
        for (Document doc : getEntryCollection().find(Filters.eq("trip_id", tripId))) {
            entries.add(documentToEntry(doc));
        }
        return entries;
    }

    public JournalEntry findById(Long id) {
        Document doc = getEntryCollection().find(Filters.eq("id", id)).first();
        return doc == null ? null : documentToEntry(doc);
    }

    public boolean exists(Long id) {
        return getEntryCollection().countDocuments(Filters.eq("id", id)) > 0;
    }

    public boolean update(JournalEntry entry) {
        Document update = new Document();
        update.append("title", entry.getTitle())
                .append("content", entry.getContent())
                .append("location", entry.getLocation())
                .append("mood", entry.getMood())
                .append("weather", entry.getWeather());
        if (entry.getEntryDate() != null) update.append("entry_date", Date.valueOf(entry.getEntryDate()));
        if (!entry.getPhotoPaths().isEmpty()) update.append("photos", entry.getPhotoPaths());

        getEntryCollection().updateOne(Filters.eq("id", entry.getId()), new Document("$set", update));
        return true;
    }

    public boolean delete(Long id) {
        return getEntryCollection().deleteOne(Filters.eq("id", id)).getDeletedCount() > 0;
    }

    private JournalEntry documentToEntry(Document doc) {
        JournalEntry entry = new JournalEntry();
        Object idObj = doc.get("id");
        if (idObj instanceof Number idNum) entry.setId(idNum.longValue());
        Object tripObj = doc.get("trip_id");
        if (tripObj instanceof Number tripNum) entry.setTripId(tripNum.longValue());
        entry.setTitle(doc.getString("title"));
        entry.setContent(doc.getString("content"));
        entry.setLocation(doc.getString("location"));
        entry.setMood(doc.getString("mood"));
        entry.setWeather(doc.getString("weather"));
        java.util.Date d = doc.getDate("entry_date");
        if (d != null) entry.setEntryDate(d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        Object photos = doc.get("photos");
        if (photos instanceof List<?>) {
            for (Object p : (List<?>) photos) {
                if (p != null) entry.addPhoto(p.toString());
            }
        }
        return entry;
    }
}
