package com.travelingjournal.dao;

import com.mongodb.client.MongoCollection;
import com.travelingjournal.model.JournalEntry;
import com.travelingjournal.util.DatabaseUtil;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class EntryDAO {

    private MongoCollection<Document> getEntryCollection() {
        return DatabaseUtil.getDatabase().getCollection("journal_entries");
    }

    public void addEntry(JournalEntry entry) {
        Document doc = new Document("trip_id", new ObjectId(entry.getTripId())) // Link to Trip
                .append("title", entry.getTitle())
                .append("content", entry.getContent())
                .append("entry_date", entry.getEntryDate())
                .append("photo_path", entry.getPhotoPath());

        getEntryCollection().insertOne(doc);
    }

    public List<JournalEntry> findAllByTripId(String tripId) {
        List<JournalEntry> entries = new ArrayList<>();
        
        for (Document doc : getEntryCollection().find(eq("trip_id", new ObjectId(tripId)))) {
            JournalEntry entry = new JournalEntry();
            entry.setId(doc.getObjectId("_id").toHexString());
            entry.setTripId(doc.getObjectId("trip_id").toHexString());
            entry.setTitle(doc.getString("title"));
            entry.setContent(doc.getString("content"));
            entry.setEntryDate(doc.getDate("entry_date"));
            entry.setPhotoPath(doc.getString("photo_path"));
            entries.add(entry);
        }
        return entries;
    }
    
     public boolean deleteEntry(String entryId) {
        try {
            getEntryCollection().deleteOne(eq("_id", new ObjectId(entryId)));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
