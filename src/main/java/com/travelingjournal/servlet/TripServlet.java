package com.travelingjournal.servlet;

import com.travelingjournal.model.Trip;
import com.travelingjournal.model.User;
import com.travelingjournal.service.TripService;
import jakarta.servlet.ServletException;
// servlet mapping defined in web.xml
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

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
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing id");
                return;
            }
            Long id;
            try {
                id = Long.parseLong(idParam);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid id");
                return;
            }
            Trip trip = tripService.getTripById(id);
            request.setAttribute("trip", trip);
            request.getRequestDispatcher("/jsp/trip/edit.jsp").forward(request, response);

        } else if ("/view".equals(pathInfo)) {
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing id");
                return;
            }
            Long id;
            try {
                id = Long.parseLong(idParam);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid id");
                return;
            }
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
        String sdParam = request.getParameter("startDate");
        String edParam = request.getParameter("endDate");
        if (sdParam != null && !sdParam.isBlank()) {
            try {
                trip.setStartDate(LocalDate.parse(sdParam));
            } catch (DateTimeParseException e) {
                trip.setStartDate(null);
            }
        }
        if (edParam != null && !edParam.isBlank()) {
            try {
                trip.setEndDate(LocalDate.parse(edParam));
            } catch (DateTimeParseException e) {
                trip.setEndDate(null);
            }
        }
        trip.setDescription(request.getParameter("description"));

        if ("add".equals(action)) {
            tripService.createTrip(trip);
        } else if ("update".equals(action)) {
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing id");
                return;
            }
            try {
                trip.setId(Long.parseLong(idParam));
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid id");
                return;
            }
            tripService.updateTrip(trip);
        }

        response.sendRedirect(request.getContextPath() + "/trip/list");
    }
}
