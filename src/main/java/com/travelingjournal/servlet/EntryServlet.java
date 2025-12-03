package com.travelingjournal.servlet;

import com.travelingjournal.model.JournalEntry;
import com.travelingjournal.model.User;
import com.travelingjournal.service.EntryService;
import jakarta.servlet.ServletException;
// servlet mapping defined in web.xml
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class EntryServlet extends HttpServlet {

    private final EntryService entryService = new EntryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String path = request.getPathInfo();
        if ("/add".equals(path)) {
            request.setAttribute("tripId", request.getParameter("tripId"));
            request.getRequestDispatcher("/jsp/entry/add.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");

        JournalEntry entry = new JournalEntry();
        String tripIdParam = request.getParameter("tripId");
        if (tripIdParam == null || tripIdParam.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing tripId");
            return;
        }
        try {
            entry.setTripId(Long.parseLong(tripIdParam));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid tripId");
            return;
        }

        String entryDateParam = request.getParameter("entryDate");
        if (entryDateParam != null && !entryDateParam.isBlank()) {
            try {
                entry.setEntryDate(LocalDate.parse(entryDateParam));
            } catch (DateTimeParseException e) {
                // If date can't be parsed, set to null and allow service to default it
                entry.setEntryDate(null);
            }
        } else {
            entry.setEntryDate(null);
        }
        entry.setTitle(request.getParameter("title"));
        entry.setContent(request.getParameter("content"));
        entry.setMood(request.getParameter("mood"));
        entry.setWeather(request.getParameter("weather"));
        entry.setLocation(request.getParameter("location"));

        entryService.createEntry(entry);
        response.sendRedirect(request.getContextPath() + "/trip/view?id=" + entry.getTripId());
    }
}
