package com.travelingjournal.filter;

import com.travelingjournal.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

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

        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

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

            chain.doFilter(request, response);
        } else {
            String loginPage = contextPath + "/jsp/auth/login.jsp";

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
