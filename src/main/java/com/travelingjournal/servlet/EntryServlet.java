package com.travelingjournal.servlet;

import com.travelingjournal.model.JournalEntry;
import com.travelingjournal.model.User;
import com.travelingjournal.service.EntryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/entry/*")
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
        entry.setTripId(Long.parseLong(request.getParameter("tripId")));
        entry.setEntryDate(LocalDate.parse(request.getParameter("entryDate")));
        entry.setTitle(request.getParameter("title"));
        entry.setContent(request.getParameter("content"));
        entry.setMood(request.getParameter("mood"));
        entry.setWeather(request.getParameter("weather"));
        entry.setLocation(request.getParameter("location"));

        entryService.createEntry(entry);
        response.sendRedirect(request.getContextPath() + "/trip/view?id=" + entry.getTripId());
    }
}
