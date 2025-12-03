-- Create the database (optional - you can also create it manually)
CREATE DATABASE IF NOT EXISTS travel_journal CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE travel_journal;

-- =============================================
-- Table: users
-- =============================================
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- Table: trips
-- =============================================
CREATE TABLE trips (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(150) NOT NULL,
    destination VARCHAR(100) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT chk_dates CHECK (start_date <= end_date)
);

-- =============================================
-- Table: journal_entries
-- =============================================
CREATE TABLE journal_entries (
    id INT AUTO_INCREMENT PRIMARY KEY,
    trip_id INT NOT NULL,
    entry_date DATE NOT NULL,
    title VARCHAR(150) NOT NULL,
    content TEXT NOT NULL,
    photo_path VARCHAR(255),  -- stores relative path, e.g., "uploads/12345_sunset.jpg"
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (trip_id) REFERENCES trips(id) ON DELETE CASCADE
);

-- =============================================
-- Indexes for better performance
-- =============================================
CREATE INDEX idx_trips_user ON trips(user_id);
CREATE INDEX idx_entries_trip ON journal_entries(trip_id);
CREATE INDEX idx_entries_date ON journal_entries(entry_date);

-- =============================================
-- Sample data (optional - great for testing right after setup)
-- =============================================
INSERT INTO users (username, email, password_hash) VALUES
('demo', 'demo@travelingjournal.com', '$2a$10$QJ1iK9z3fZ7xY8vW9eR2P.Kj5LmN8pXbTqUcVfGhJrEaRtDvYkZ1y');
-- Password for demo user is: "password" (hashed with BCrypt)

INSERT INTO trips (user_id, title, destination, start_date, end_date, description) VALUES
(1, 'Summer in Italy', 'Rome, Florence & Venice', '2025-06-15', '2025-06-28', 'Amazing two weeks exploring Italian culture, food, and history!'),
(1, 'Weekend in Paris', 'Paris, France', '2025-09-05', '2025-09-07', 'Romantic getaway with my partner.');

INSERT INTO journal_entries (trip_id, entry_date, title, content, photo_path) VALUES
(1, '2025-06-16', 'Arrival in Rome', 'Landed safely and headed straight to the Colosseum. The history is overwhelming!', 'uploads/rome_colosseum.jpg'),
(1, '2025-06-18', 'Pizza Night', 'Best margherita pizza of my life in a tiny trattoria. Highly recommend!', NULL),
(2, '2025-09-06', 'Eiffel Tower at Sunset', 'Climbed to the top just in time for sunset. Magical view!', 'uploads/paris_eiffel.jpg');