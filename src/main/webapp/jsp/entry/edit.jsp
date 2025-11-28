<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="pageTitle" value="Edit Entry" scope="request"/>
<%@ include file="../layout/header.jsp" %>

<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-lg-10">
            <div class="card shadow-lg border-0">
                <div class="card-header bg-warning text-dark text-center py-4">
                    <h2 class="mb-0">
                        <i class="bi bi-pencil-square"></i> Edit Journal Entry
                    </h2>
                </div>

                <div class="card-body p-5">
                    <form action="${pageContext.request.contextPath}/entry/edit" method="post">
                        <input type="hidden" name="id" value="${entry.id}">
                        <input type="hidden" name="tripId" value="${entry.tripId}">

                        <div class="row g-4">
                            <div class="col-md-6">
                                <label class="form-label fw-bold">Date</label>
                                <input type="date" name="entryDate" value="${entry.entryDate}" 
                                       class="form-control form-control-lg" required>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label fw-bold">Title</label>
                                <input type="text" name="title" value="${entry.title}" 
                                       class="form-control form-control-lg" required>
                            </div>

                            <div class="col-md-6">
                                <label class="form-label">Location</label>
                                <input type="text" name="location" value="${entry.location}" class="form-control">
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Weather</label>
                                <input type="text" name="weather" value="${entry.weather}" class="form-control">
                            </div>

                            <div class="col-12">
                                <label class="form-label fw-bold">Mood</label>
                                <select name="mood" class="form-select">
                                    <option value="">Select mood</option>
                                    <option value="excited" ${entry.mood == 'excited' ? 'selected' : ''}>Excited</option>
                                    <option value="happy" ${entry.mood == 'happy' ? 'selected' : ''}>Happy & Grateful</option>
                                    <!-- Add other options as needed -->
                                </select>
                            </div>

                            <div class="col-12">
                                <label class="form-label fw-bold">Your Story</label>
                                <textarea name="content" rows="10" class="form-control form-control-lg" required>${entry.content}</textarea>
                            </div>
                        </div>

                        <hr class="my-5">

                        <div class="d-flex justify-content-between">
                            <a href="${pageContext.request.contextPath}/trip/view?id=${entry.tripId}" 
                               class="btn btn-outline-secondary btn-lg">Cancel</a>
                            <button type="submit" class="btn btn-warning btn-lg px-5">
                                <i class="bi bi-save"></i> Update Entry
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../layout/footer.jsp" %>