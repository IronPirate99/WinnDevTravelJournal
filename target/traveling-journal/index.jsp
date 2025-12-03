<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Smart root page redirect logic

    // If user is already logged in → go straight to the dashboard
    if (session.getAttribute("user") != null) {
        response.sendRedirect(request.getContextPath() + "/home");
        return;
    }

    // If not logged in → show the beautiful login page
    response.sendRedirect(request.getContextPath() + "/jsp/auth/login.jsp");
    return;
%>