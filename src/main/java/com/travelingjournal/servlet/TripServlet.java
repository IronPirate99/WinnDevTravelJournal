package com.travelingjournal.servlet;

import com.travelingjournal.model.Trip;
import com.travelingjournal.model.User;
import com.travelingjournal.service.TripService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/trip/*")
public class TripServlet extends HttpServlet {

    private final TripService tripService = new TripService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || "/".equals(pathInfo) || "/list".equals(pathInfo)) {
            List<Trip> trips = tripService.getTripsByUserId(user.getId());
            request.setAttribute("trips", trips);
            request.getRequestDispatcher("/jsp/trip/list.jsp").forward(request, response);

        } else if ("/add".equals(pathInfo)) {
            request.getRequestDispatcher("/jsp/trip/add.jsp").forward(request, response);

        } else if ("/edit".equals(pathInfo)) {
            Long id = Long.parseLong(request.getParameter("id"));
            Trip trip = tripService.getTripById(id);
            request.setAttribute("trip", trip);
            request.getRequestDispatcher("/jsp/trip/edit.jsp").forward(request, response);

        } else if ("/view".equals(pathInfo)) {
            Long id = Long.parseLong(request.getParameter("id"));
            Trip trip = tripService.getTripById(id);
            request.setAttribute("trip", trip);
            request.setAttribute("entries", tripService.getEntriesByTripId(id));
            request.getRequestDispatcher("/jsp/trip/view.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String action = request.getParameter("action");

        Trip trip = new Trip();
        trip.setUserId(user.getId());
        trip.setTitle(request.getParameter("title"));
        trip.setDestination(request.getParameter("destination"));
        trip.setStartDate(LocalDate.parse(request.getParameter("startDate")));
        trip.setEndDate(LocalDate.parse(request.getParameter("endDate")));
        trip.setDescription(request.getParameter("description"));

        if ("add".equals(action)) {
            tripService.createTrip(trip);
        } else if ("update".equals(action)) {
            trip.setId(Long.parseLong(request.getParameter("id")));
            tripService.updateTrip(trip);
        }

        response.sendRedirect(request.getContextPath() + "/trip/list");
    }
}
