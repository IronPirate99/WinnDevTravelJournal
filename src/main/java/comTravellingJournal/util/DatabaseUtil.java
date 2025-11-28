package com.travelingjournal.util;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

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
}