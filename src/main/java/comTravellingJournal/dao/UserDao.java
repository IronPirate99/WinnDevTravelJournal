package com.travelingjournal.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.travelingjournal.model.User;
import com.travelingjournal.util.DatabaseUtil;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;

public class UserDAO {

    private MongoCollection<Document> getUserCollection() {
        return DatabaseUtil.getDatabase().getCollection("users");
    }

    public boolean registerUser(User user) {
        Document doc = new Document("username", user.getUsername())
                .append("email", user.getEmail())
                .append("password", user.getPassword()); // Remember: Hash this first!

        try {
            getUserCollection().insertOne(doc);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User findByEmail(String email) {
        // "eq" is a filter helper: WHERE email = email
        Document doc = getUserCollection().find(eq("email", email)).first();

        if (doc != null) {
            User user = new User();
            // Retrieve the ObjectId and convert to Hex String for the POJO
            user.setId(doc.getObjectId("_id").toHexString()); 
            user.setUsername(doc.getString("username"));
            user.setEmail(doc.getString("email"));
            user.setPassword(doc.getString("password"));
            return user;
        }
        return null;
    }
}