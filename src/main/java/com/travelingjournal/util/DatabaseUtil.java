package com.travelingjournal.util;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;

public class DatabaseUtil {
    
    // Connection String (Standard Localhost)
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DB_NAME = "travel_journal";
    
    private static MongoClient mongoClient = null;

    // Singleton pattern to ensure we only create one client
    public static MongoDatabase getDatabase() {
        if (mongoClient == null) {
            try {
                mongoClient = MongoClients.create(CONNECTION_STRING);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error connecting to MongoDB");
            }
        }
        return mongoClient.getDatabase(DB_NAME);
    }

    /**
     * Simple sequence generator stored in a `counters` collection.
     * Returns next sequence value for given name (collection/key).
     */
    public static long getNextSequence(String name) {
        try {
            MongoCollection<Document> counters = getDatabase().getCollection("counters");
            Document filter = new Document("_id", name);
            Document update = new Document("$inc", new Document("seq", 1));
            FindOneAndUpdateOptions opts = new FindOneAndUpdateOptions().upsert(true).returnDocument(ReturnDocument.AFTER);
            Document result = counters.findOneAndUpdate(filter, update, opts);
            if (result != null && result.containsKey("seq")) {
                Object seq = result.get("seq");
                if (seq instanceof Number) return ((Number) seq).longValue();
            }
            throw new RuntimeException("Unable to get sequence for " + name);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
