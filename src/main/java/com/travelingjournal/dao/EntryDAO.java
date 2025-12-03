package com.travelingjournal.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.travelingjournal.model.JournalEntry;
import com.travelingjournal.util.DatabaseUtil;
import org.bson.Document;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EntryDAO {

    private MongoCollection<Document> getEntryCollection() {
        return DatabaseUtil.getDatabase().getCollection("journal_entries");
    }

    public Long save(JournalEntry entry) {
        if (entry.getId() == null) {
            entry.setId(DatabaseUtil.getNextSequence("entries"));
        }
        // Use MongoDB document id (`_id`) to avoid having two different id fields
        Document doc = new Document("_id", entry.getId())
                .append("trip_id", entry.getTripId())
                .append("title", entry.getTitle())
                .append("content", entry.getContent())
                .append("location", entry.getLocation())
                .append("mood", entry.getMood())
                .append("weather", entry.getWeather());

        if (entry.getEntryDate() != null) {
            // Store as standard java.util.Date (BSON Date) to avoid SQL-specific types
            Date mongoDate = Date.from(entry.getEntryDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
            doc.append("entry_date", mongoDate);
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
        // Query against MongoDB _id (we store numeric id as the document _id)
        Document doc = getEntryCollection().find(Filters.eq("_id", id)).first();
        return doc == null ? null : documentToEntry(doc);
    }

    public boolean exists(Long id) {
        return getEntryCollection().countDocuments(Filters.eq("_id", id)) > 0;
    }

    public boolean update(JournalEntry entry) {
        Document update = new Document();
        update.append("title", entry.getTitle())
                .append("content", entry.getContent())
                .append("location", entry.getLocation())
                .append("mood", entry.getMood())
                .append("weather", entry.getWeather());
        if (entry.getEntryDate() != null) {
            Date mongoDate = Date.from(entry.getEntryDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
            update.append("entry_date", mongoDate);
        }
        if (!entry.getPhotoPaths().isEmpty()) update.append("photos", entry.getPhotoPaths());

        getEntryCollection().updateOne(Filters.eq("_id", entry.getId()), new Document("$set", update));
        return true;
    }

    public boolean delete(Long id) {
        return getEntryCollection().deleteOne(Filters.eq("_id", id)).getDeletedCount() > 0;
    }

    private JournalEntry documentToEntry(Document doc) {
        JournalEntry entry = new JournalEntry();
        // _id holds the document id (we wrote numeric ids into _id)
        Object idObj = doc.get("_id");
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
