<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" value="New Journal Entry" scope="request"/>
<%@ include file="../layout/header.jsp" %>

<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-lg-10">
            <div class="card shadow-lg border-0 rounded-4">
                <div class="card-header bg-gradient bg-primary text-white text-center py-4 rounded-top-4">
                    <h2 class="mb-0">
                        <i class="bi bi-journal-plus"></i> Write a New Entry
                    </h2>
                    <p class="mb-0 mt-2 opacity-90">Capture today's adventure</p>
                </div>

                <div class="card-body p-5">
                    <form action="${pageContext.request.contextPath}/entry/add" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="tripId" value="${param.tripId}">

                        <div class="row g-4">
                            <!-- Date & Title -->
                            <div class="col-md-6">
                                <label class="form-label fw-bold text-primary">Date</label>
                                <input type="date" name="entryDate" class="form-control form-control-lg" 
                                       value="<%= java.time.LocalDate.now() %>" required>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label fw-bold text-primary">Entry Title</label>
                                <input type="text" name="title" class="form-control form-control-lg" 
                                       placeholder="e.g., Sunrise at Santorini" required>
                            </div>

                            <!-- Location & Weather -->
                            <div class="col-md-6">
                                <label class="form-label fw-bold">Location</label>
                                <input type="text" name="location" class="form-control" 
                                       placeholder="Venice Canals, Italy">
                            </div>
                            <div class="col-md-6">
                                <label class="form-label fw-bold">Weather</label>
                                <input type="text" name="weather" class="form-control" 
                                       placeholder="Sunny, 28°C">
                            </div>

                            <!-- Mood -->
                            <div class="col-12">
                                <label class="form-label fw-bold">How are you feeling?</label>
                                <select name="mood" class="form-select form-select-lg">
                                    <option value="">Select your mood</option>
                                    <option value="excited">Excited</option>
                                    <option value="happy">Happy & Grateful</option>
                                    <option value="relaxed">Relaxed</option>
                                    <option value="adventurous">Adventurous</option>
                                    <option value="amazed">Amazed</option>
                                    <option value="tired">Tired but Happy</option>
                                    <option value="inspired">Inspired</option>
                                </select>
                            </div>

                            <!-- Story Content -->
                            <div class="col-12">
                                <label class="form-label fw-bold text-primary">Your Story</label>
                                <textarea name="content" rows="12" class="form-control form-control-lg" 
                                          placeholder="What did you see? Who did you meet? How did it feel? Let the memories flow..." required></textarea>
                            </div>

                            <!-- Photo Upload -->
                            <div class="col-12">
                                <label class="form-label fw-bold">Add Photos</label>
                                <input type="file" name="photos" multiple accept="image/*" 
                                       class="form-control form-control-lg">
                                <div class="form-text mt-2">
                                    <i class="bi bi-info-circle"></i> Hold Ctrl/Cmd to select multiple photos
                                </div>
                            </div>
                        </div>

                        <hr class="my-5">

                        <div class="d-flex justify-content-between">
                            <a href="${pageContext.request.contextPath}/trip/view?id=${param.tripId}" 
                               class="btn btn-outline-secondary btn-lg px-5">
                                Cancel
                            </a>
                            <button type="submit" class="btn btn-success btn-lg px-5 shadow">
                                <i class="bi bi-check2"></i> Save Entry
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../layout/footer.jsp" %>  