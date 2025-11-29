package com.travelingjournal.service;

import com.travelingjournal.dao.EntryDAO;
import com.travelingjournal.model.JournalEntry;

import java.util.List;

public class EntryService {

    private final EntryDAO entryDAO = new EntryDAO();

    public List<JournalEntry> getEntriesByTripId(Long tripId) {
        return entryDAO.findByTripId(tripId);
    }

    public JournalEntry getEntryById(Long entryId) {
        return entryDAO.findById(entryId);
    }

    public boolean createEntry(JournalEntry entry) {
        if (entry.getTripId() == null || entry.getContent() == null || entry.getContent().trim().isEmpty()) {
            return false;
        }
        if (entry.getEntryDate() == null) {
            entry.setEntryDate(java.time.LocalDate.now());
        }

        Long id = entryDAO.save(entry);
        if (id != null) {
            entry.setId(id);
            return true;
        }
        return false;
    }

    public boolean updateEntry(JournalEntry entry) {
        if (entry.getId() == null || !entryDAO.exists(entry.getId())) {
            return false;
        }
        return entryDAO.update(entry);
    }

    public boolean deleteEntry(Long entryId, Long tripId, Long userId) {
        return entryDAO.delete(entryId);
    }
}
