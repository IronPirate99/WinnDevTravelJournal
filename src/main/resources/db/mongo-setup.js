// This script sets up the necessary indexes and seeds the database with sample data
// for the Traveling Journal application.
//
// To run: mongosh "mongodb://localhost:27017/travel_journal" mongo-setup.js

console.log("Connecting to database: travel_journal");
db = db.getSiblingDB('travel_journal');

console.log("Creating indexes for collections...");

db.users.createIndex({ "email": 1 }, { unique: true });
db.trips.createIndex({ "user_id": 1 });
db.journal_entries.createIndex({ "trip_id": 1 });
db.journal_entries.createIndex({ "entry_date": 1 });

console.log("Indexes created successfully.");

// Seeding the database
console.log("Seeding database with sample data...");

// Clear existing sample data to prevent duplication
db.users.deleteOne({ email: "demo@travelingjournal.com" });
db.trips.deleteMany({ $or: [{id: 1}, {id: 2}] });
db.journal_entries.deleteMany({ $or: [{id: 1}, {id: 2}, {id: 3}] });
// Note: Deleting by your custom ID is less efficient than using Mongo's default _id

db.users.insertOne({
    id: 1,
    username: "demo",
    email: "demo@travelingjournal.com",
    password_hash: "$2a$10$QJ1iK9z3fZ7xY8vW9eR2P.Kj5LmN8pXbTqUcVfGhJrEaRtDvYkZ1y",
    createdAt: new Date()
});

db.trips.insertMany([
    { id: 1, user_id: 1, title: "Summer in Italy", destination: "Rome, Florence & Venice", start_date: new Date("2025-06-15"), end_date: new Date("2025-06-28"), description: "Amazing two weeks exploring Italian culture, food, and history!" },
    { id: 2, user_id: 1, title: "Weekend in Paris", destination: "Paris, France", start_date: new Date("2025-09-05"), end_date: new Date("2025-09-07"), description: "Romantic getaway with my partner." }
]);

db.journal_entries.insertMany([
    { id: 1, trip_id: 1, entry_date: new Date("2025-06-16"), title: "Arrival in Rome", content: "Landed safely and headed straight to the Colosseum. The history is overwhelming!", photos: ["uploads/rome_colosseum.jpg"] },
    { id: 2, trip_id: 1, entry_date: new Date("2025-06-18"), title: "Pizza Night", content: "Best margherita pizza of my life in a tiny trattoria. Highly recommend!", photos: [] },
    { id: 3, trip_id: 2, entry_date: new Date("2025-09-06"), title: "Eiffel Tower at Sunset", content: "Climbed to the top just in time for sunset. Magical view!", photos: ["uploads/paris_eiffel.jpg"] }
]);

console.log("Database seeded successfully.");