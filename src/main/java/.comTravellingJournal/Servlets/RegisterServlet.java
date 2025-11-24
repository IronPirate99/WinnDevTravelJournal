package com.travelingjournal.servlet;

import com.travelingjournal.model.User;
import com.travelingjournal.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/auth/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (userService.emailExists(email)) {
            request.setAttribute("error", "Email already registered");
            request.getRequestDispatcher("/jsp/auth/register.jsp").forward(request, response);
            return;
        }

        User newUser = userService.registerUser(name, email, password);
        if (newUser != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", newUser);
            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            request.setAttribute("error", "Registration failed");
            request.getRequestDispatcher("/jsp/auth/register.jsp").forward(request, response);
        }
    }
}