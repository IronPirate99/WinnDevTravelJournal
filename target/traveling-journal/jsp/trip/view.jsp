<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${trip.title} - Journal</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <%@ include file="../layout/header.jsp" %>

    <div class="container py-5">
        <!-- Trip Header -->
        <div class="card mb-4 shadow-sm">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-start">
                    <div>
                        <h1 class="h2 fw-bold text-primary">${trip.title}</h1>
                        <h5 class="text-muted">${trip.destination}</h5>
                        <p class="text-secondary">
                            <fmt:formatDate value="${trip.startDate}" pattern="MMMM dd"/> –
                            <fmt:formatDate value="${trip.endDate}" pattern="MMMM dd, yyyy"/>
                        </p>
                        <p>${trip.description}</p>
                    </div>
                    <div>
                        <a href="${pageContext.request.contextPath}/entry/add?tripId=${trip.id}" 
                           class="btn btn-success me-2">New Entry</a>
                        <a href="${pageContext.request.contextPath}/trip/edit?id=${trip.id}" 
                           class="btn btn-outline-warning">Edit Trip</a>
                    </div>
                </div>
            </div>
        </div>

        <!-- Journal Entries Timeline -->
        <c:forEach var="entry" items="${entries}">
            <div class="card mb-3 shadow-sm">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-start mb-3">
                        <div>
                            <h5 class="card-title mb-1">${entry.title}</h5>
                            <small class="text-muted">
                                <fmt:formatDate value="${entry.entryDate}" pattern="EEEE, MMMM dd, yyyy"/>
                                <c:if test="${not empty entry.location}"> • ${entry.location}</c:if>
                                <c:if test="${not empty entry.weather}"> • ${entry.weather}</c:if>
                                <c:if test="${not empty entry.mood}"> • Feeling: ${entry.mood}</c:if>
                            </small>
                        </div>
                    </div>

                    <p class="card-text">${entry.content}</p>

                    <!-- Photos (if any) -->
                    <c:if test="${not empty entry.photoPaths}">
                        <div class="row mt-3">
                            <c:forEach var="photo" items="${entry.photoPaths}">
                                <div class="col-6 col-md-4 col-lg-3 mb-3">
                                    <img src="${pageContext.request.contextPath}/images/uploads/${photo}" 
                                         class="img-fluid rounded shadow-sm" alt="Travel photo">
                                </div>
                            </c:forEach>
                        </div>
                    </c:if>
                </div>
            </div>
        </c:forEach>

        <c:if test="${empty entries}">
            <div class="text-center py-5 text-muted">
                <h4>No entries yet</h4>
                <p>Start writing about your first day!</p>
                <a href="${pageContext.request.contextPath}/entry/add?tripId=${trip.id}" class="btn btn-primary">Write First Entry</a>
            </div>
        </c:if>
    </div>

    <%@ include file="../layout/footer.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>