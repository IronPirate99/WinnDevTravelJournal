<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>
        <c:if test="${not empty pageTitle}">${pageTitle} - </c:if>Traveling Journal
    </title>

    <!-- Bootstrap 5.3 + Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">

    <!-- Custom Styles (optional - create this file or remove line below) -->
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">

    <style>
        body { 
            min-height: 100vh; 
            display: flex; 
            flex-direction: column; 
            background: #f8f9fa;
        }
        main { flex: 1; }
        .navbar-brand { font-weight: 800; }
        .nav-link { font-weight: 500; }
    </style>
</head>
<body>

<!-- Navigation Bar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm sticky-top">
    <div class="container">
        <a class="navbar-brand d-flex align-items-center gap-2" href="${pageContext.request.contextPath}/home">
            <i class="bi bi-journal-richtext fs-3"></i>
            Traveling Journal
        </a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNav">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="mainNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link ${requestScope['javax.servlet.forward.request_uri'] == '/home' ? 'active' : ''}"
                       href="${pageContext.request.contextPath}/home">
                        <i class="bi bi-house-door"></i> Home
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${requestScope['javax.servlet.forward.request_uri'].contains('/trip') ? 'active' : ''}"
                       href="${pageContext.request.contextPath}/trip/list">
                        <i class="bi bi-journal-bookmark-fill"></i> My Journals
                    </a>
                </li>
            </ul>

            <div class="d-flex align-items-center text-white">
                <span class="me-3">
                    <i class="bi bi-person-circle"></i>
                    <strong>${sessionScope.user.name}</strong>
                </span>
                <a href="${pageContext.request.contextPath}/logout" 
                   class="btn btn-outline-light btn-sm">
                    <i class="bi bi-box-arrow-right"></i> Logout
                </a>
            </div>
        </div>
    </div>
</nav>

<main>