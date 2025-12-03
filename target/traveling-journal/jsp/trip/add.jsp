<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Add New Trip</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <%@ include file="../layout/header.jsp" %>

    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6">
                <div class="card shadow">
                    <div class="card-header bg-primary text-white">
                        <h4 class="mb-0">Start a New Travel Journal</h4>
                    </div>
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/trip/add" method="post">
                            <input type="hidden" name="action" value="add">

                            <div class="mb-3">
                                <label class="form-label fw-bold">Trip Title</label>
                                <input type="text" name="title" class="form-control" required placeholder="e.g., Summer in Italy 2025">
                            </div>

                            <div class="mb-3">
                                <label class="form-label fw-bold">Destination</label>
                                <input type="text" name="destination" class="form-control" required placeholder="Paris, France">
                            </div>

                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label class="form-label fw-bold">Start Date</label>
                                    <input type="date" name="startDate" class="form-control" required>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label fw-bold">End Date</label>
                                    <input type="date" name="endDate" class="form-control" required>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label class="form-label fw-bold">Description (optional)</label>
                                <textarea name="description" rows="4" class="form-control" 
                                          placeholder="What are you excited about?"></textarea>
                            </div>

                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <a href="${pageContext.request.contextPath}/trip/list" class="btn btn-secondary me-md-2">Cancel</a>
                                <button type="submit" class="btn btn-success">Create Trip</button>
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