package com.travelingjournal.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.travelingjournal.model.JournalEntry;
import com.travelingjournal.model.Trip;
import com.travelingjournal.util.DatabaseUtil;
import org.bson.Document;

import java.sql.Date;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class TripDAO {

    private MongoCollection<Document> getTripCollection() {
        return DatabaseUtil.getDatabase().getCollection("trips");
    }

    /** Save trip. If id is null, generates a new numeric id. Returns id. */
    public Long save(Trip trip) {
        if (trip.getId() == null) {
            trip.setId(DatabaseUtil.getNextSequence("trips"));
        }
        Document doc = new Document("id", trip.getId())
                .append("user_id", trip.getUserId())
                .append("title", trip.getTitle())
                .append("destination", trip.getDestination())
                .append("description", trip.getDescription())
                .append("cover_photo", trip.getCoverPhoto());

        if (trip.getStartDate() != null) {
            doc.append("start_date", Date.valueOf(trip.getStartDate()));
        }
        if (trip.getEndDate() != null) {
            doc.append("end_date", Date.valueOf(trip.getEndDate()));
        }

        getTripCollection().insertOne(doc);
        return trip.getId();
    }

    public List<Trip> findByUserId(Long userId) {
        List<Trip> trips = new ArrayList<>();
        for (Document doc : getTripCollection().find(Filters.eq("user_id", userId))) {
            Trip trip = documentToTrip(doc);
            trips.add(trip);
        }
        return trips;
    }

    public Trip findById(Long id) {
        Document doc = getTripCollection().find(Filters.eq("id", id)).first();
        return doc == null ? null : documentToTrip(doc);
    }

    public boolean exists(Long id) {
        return getTripCollection().countDocuments(Filters.eq("id", id)) > 0;
    }

    public boolean update(Trip trip) {
        Document update = new Document();
        if (trip.getTitle() != null) update.append("title", trip.getTitle());
        update.append("destination", trip.getDestination());
        update.append("description", trip.getDescription());
        update.append("cover_photo", trip.getCoverPhoto());
        if (trip.getStartDate() != null) update.append("start_date", Date.valueOf(trip.getStartDate()));
        if (trip.getEndDate() != null) update.append("end_date", Date.valueOf(trip.getEndDate()));

        if (update.isEmpty()) return false;

        getTripCollection().updateOne(Filters.eq("id", trip.getId()), new Document("$set", update));
        return true;
    }

    public boolean delete(Long id) {
        return getTripCollection().deleteOne(Filters.eq("id", id)).getDeletedCount() > 0;
    }

    public List<JournalEntry> findEntriesByTripId(Long tripId) {
        EntryDAO entryDAO = new EntryDAO();
        return entryDAO.findByTripId(tripId);
    }

    private Trip documentToTrip(Document doc) {
        Trip trip = new Trip();
        Object idObj = doc.get("id");
        if (idObj instanceof Number idNum) trip.setId(idNum.longValue());
        Object userObj = doc.get("user_id");
        if (userObj instanceof Number userNum) trip.setUserId(userNum.longValue());
        trip.setTitle(doc.getString("title"));
        trip.setDestination(doc.getString("destination"));
        trip.setDescription(doc.getString("description"));
        trip.setCoverPhoto(doc.getString("cover_photo"));

        java.util.Date sd = doc.getDate("start_date");
        if (sd != null) trip.setStartDate(sd.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        java.util.Date ed = doc.getDate("end_date");
        if (ed != null) trip.setEndDate(ed.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        return trip;
    }
}
