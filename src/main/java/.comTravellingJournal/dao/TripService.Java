package com.travelingjournal.dao;

import com.mongodb.client.MongoCollection;
import com.travelingjournal.model.Trip;
import com.travelingjournal.util.DatabaseUtil;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class TripDAO {

    private MongoCollection<Document> getTripCollection() {
        return DatabaseUtil.getDatabase().getCollection("trips");
    }

    public void addTrip(Trip trip) {
        Document doc = new Document("user_id", new ObjectId(trip.getUserId())) // Store as ObjectId reference
                .append("title", trip.getTitle())
                .append("start_date", trip.getStartDate())
                .append("end_date", trip.getEndDate());

        getTripCollection().insertOne(doc);
    }

    public List<Trip> findAllByUserId(String userId) {
        List<Trip> trips = new ArrayList<>();
        
        // Find all documents where "user_id" matches the incoming ObjectId
        for (Document doc : getTripCollection().find(eq("user_id", new ObjectId(userId)))) {
            Trip trip = new Trip();
            trip.setId(doc.getObjectId("_id").toHexString());
            trip.setUserId(doc.getObjectId("user_id").toHexString());
            trip.setTitle(doc.getString("title"));
            trip.setStartDate(doc.getDate("start_date"));
            trip.setEndDate(doc.getDate("end_date"));
            trips.add(trip);
        }
        return trips;
    }
    
    public Trip findById(String id) {
        Document doc = getTripCollection().find(eq("_id", new ObjectId(id))).first();
        if(doc != null) {
             Trip trip = new Trip();
             trip.setId(doc.getObjectId("_id").toHexString());
             trip.setTitle(doc.getString("title"));
             // ... set other fields
             return trip;
        }
        return null;
    }
}