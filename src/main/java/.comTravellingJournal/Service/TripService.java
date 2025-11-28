package com.travelingjournal.service;

import com.travelingjournal.dao.TripDAO;
import com.travelingjournal.model.JournalEntry;
import com.travelingjournal.model.Trip;

import java.util.List;

public class TripService {

    private final TripDAO tripDAO = new TripDAO();

    public List<Trip> getTripsByUserId(Long userId) {
        return tripDAO.findByUserId(userId);
    }

    public Trip getTripById(Long tripId) {
        return tripDAO.findById(tripId);
    }

    public List<JournalEntry> getEntriesByTripId(Long tripId) {
        return tripDAO.findEntriesByTripId(tripId);
    }

    public boolean createTrip(Trip trip) {
        if (trip.getTitle() == null || trip.getTitle().trim().isEmpty()) {
            return false;
        }
        if (trip.getStartDate() == null || trip.getEndDate() == null ||
            trip.getStartDate().isAfter(trip.getEndDate())) {
            return false;
        }

        Long id = tripDAO.save(trip);
        if (id != null) {
            trip.setId(id);
            return true;
        }
        return false;
    }

    public boolean updateTrip(Trip trip) {
        if (trip.getId() == null || !tripDAO.exists(trip.getId())) {
            return false;
        }
        return tripDAO.update(trip);
    }

    public boolean deleteTrip(Long tripId, Long userId) {
        Trip trip = tripDAO.findById(tripId);
        if (trip != null && trip.getUserId().equals(userId)) {
            return tripDAO.delete(tripId);
        }
        return false;
    }
}