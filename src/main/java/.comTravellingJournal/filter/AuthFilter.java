package com.travelingjournal.filter;

import com.travelingjournal.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Filters all requests to protected resources.
 * If user is not logged in → redirect to login page
 */
@WebFilter(urlPatterns = {
        "/home",
        "/trip/*",
        "/entry/*",
        "/upload-photo",
        "/logout"
})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false); // don't create new session

        // Check if user is logged in
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

        // Allow access to login/register pages even when not logged in
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();

        if (isLoggedIn || 
            requestURI.equals(contextPath + "/login") ||
            requestURI.equals(contextPath + "/register") ||
            requestURI.startsWith(contextPath + "/jsp/auth/") ||
            requestURI.equals(contextPath + "/") ||
            requestURI.endsWith(".css") || 
            requestURI.endsWith(".js") || 
            requestURI.endsWith(".png") || 
            requestURI.endsWith(".jpg") || 
            requestURI.endsWith(".jpeg") ||
            requestURI.contains("/images/")) {

            // User is authenticated OR accessing public resources → continue
            chain.doFilter(request, response);
        } else {
            // Not logged in and trying to access protected page → redirect to login
            String loginPage = contextPath + "/jsp/auth/login.jsp";

            // Optional: save the original destination so we can redirect after login
            if (session != null) {
                session.setAttribute("redirectAfterLogin", requestURI);
            }

            response.sendRedirect(loginPage);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No initialization needed
    }

    @Override
    public void destroy() {
        // Cleanup if needed
    }
}