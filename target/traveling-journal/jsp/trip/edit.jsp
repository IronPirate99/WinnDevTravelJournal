<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Trip - ${trip.title}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <%@ include file="../layout/header.jsp" %>

    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6">
                <div class="card shadow">
                    <div class="card-header bg-warning text-dark">
                        <h4 class="mb-0">Edit Trip</h4>
                    </div>
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/trip/edit" method="post">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="id" value="${trip.id}">

                            <div class="mb-3">
                                <label class="form-label fw-bold">Trip Title</label>
                                <input type="text" name="title" value="${trip.title}" class="form-control" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label fw-bold">Destination</label>
                                <input type="text" name="destination" value="${trip.destination}" class="form-control" required>
                            </div>

                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label class="form-label fw-bold">Start Date</label>
                                    <input type="date" name="startDate" value="${trip.startDate}" class="form-control" required>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label fw-bold">End Date</label>
                                    <input type="date" name="endDate" value="${trip.endDate}" class="form-control" required>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label class="form-label fw-bold">Description</label>
                                <textarea name="description" rows="4" class="form-control">${trip.description}</textarea>
                            </div>

                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <a href="${pageContext.request.contextPath}/trip/view?id=${trip.id}" class="btn btn-secondary me-md-2">Cancel</a>
                                <button type="submit" class="btn btn-warning">Update Trip</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="../layout/footer.jsp" %>
</body>
</html>