package com.travelingjournal.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.travelingjournal.model.User;
import com.travelingjournal.util.DatabaseUtil;
import org.bson.Document;

import java.sql.Timestamp;
import java.time.ZoneId;

public class UserDAO {

    private MongoCollection<Document> getUserCollection() {
        return DatabaseUtil.getDatabase().getCollection("users");
    }

    public Long save(User user) {
        if (user.getId() == null) {
            user.setId(DatabaseUtil.getNextSequence("users"));
        }
        Document doc = new Document("id", user.getId())
                .append("name", user.getName())
                .append("email", user.getEmail())
                .append("password_hash", user.getPasswordHash());

        if (user.getCreatedAt() != null) {
            doc.append("created_at", Timestamp.from(user.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant()));
        }

        getUserCollection().insertOne(doc);
        return user.getId();
    }

    public User findByEmail(String email) {
        Document doc = getUserCollection().find(Filters.eq("email", email)).first();
        if (doc == null) return null;
        User user = new User();
        Object idObj = doc.get("id");
        if (idObj instanceof Number) user.setId(((Number) idObj).longValue());
        user.setName(doc.getString("name"));
        user.setEmail(doc.getString("email"));
        user.setPasswordHash(doc.getString("password_hash"));
        java.util.Date ca = doc.getDate("created_at");
        if (ca != null) user.setCreatedAt(ca.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        return user;
    }

    public User findById(Long id) {
        Document doc = getUserCollection().find(Filters.eq("id", id)).first();
        if (doc == null) return null;
        return findByEmail(doc.getString("email"));
    }
}
