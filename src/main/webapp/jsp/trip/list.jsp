<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>My Travel Journals</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body class="bg-light">
    <%@ include file="../layout/header.jsp" %>

    <div class="container py-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1 class="h3 fw-bold text-primary">My Travel Journals</h1>
            <a href="${pageContext.request.contextPath}/trip/add" class="btn btn-success btn-lg">
                New Trip
            </a>
        </div>

        <c:if test="${empty trips}">
            <div class="text-center py-5">
                <h3 class="text-muted">No trips yet</h3>
                <p>Start documenting your first adventure!</p>
            </div>
        </c:if>

        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
            <c:forEach var="trip" items="${trips}">
                <div class="col">
                    <div class="card h-100 shadow-sm hover-shadow">
                        <div class="card-body d-flex flex-column">
                            <h5 class="card-title text-primary">${trip.title}</h5>
                            <h6 class="card-subtitle mb-2 text-muted">
                                ${trip.destination}
                            </h6>
                            <p class="text-secondary small">
                                <fmt:formatDate value="${trip.startDate}" pattern="MMM dd"/> –
                                <fmt:formatDate value="${trip.endDate}" pattern="MMM dd, yyyy"/>
                            </p>
                            <p class="card-text flex-grow-1">
                                ${trip.description != null && trip.description.length() > 80 
                                    ? trip.description.substring(0,80).concat('...') 
                                    : trip.description}
                            </p>
                            <div class="mt-auto">
                                <a href="${pageContext.request.contextPath}/trip/view?id=${trip.id}" 
                                   class="btn btn-outline-primary w-100">Open Journal</a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <%@ include file="../layout/footer.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>