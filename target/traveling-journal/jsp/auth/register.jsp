<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Register • Traveling Journal</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <style>
        body, html { height: 100%; background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%); }
        .register-card { border-radius: 20px; box-shadow: 0 15px 35px rgba(0,0,0,0.3); }
        .btn-register { background: #38ef7d; border: none; color: #155724; }
        .btn-register:hover { background: #32d66f; }
    </style>
</head>
<body class="d-flex align-items-center">

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-7 col-lg-6">
            <div class="card register-card overflow-hidden">
                <div class="card-header bg-success text-white text-center py-4">
                    <h2 class="mb-0">
                        <i class="bi bi-journal-plus"></i> Join the Journey
                    </h2>
                    <p class="mb-0 mt-2 opacity-75">Create your free travel journal today</p>
                </div>

                <div class="card-body p-5">
                    <form action="${pageContext.request.contextPath}/register" method="post">
                        <div class="mb-4">
                            <label class="form-label fw-bold">Full Name</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="bi bi-person"></i></span>
                                <input type="text" name="name" class="form-control form-control-lg" 
                                       placeholder="John Doe" required>
                            </div>
                        </div>

                        <div class="mb-4">
                            <label class="form-label fw-bold">Email Address</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="bi bi-envelope"></i></span>
                                <input type="email" name="email" class="form-control form-control-lg" 
                                       placeholder="you@example.com" required>
                            </div>
                        </div>

                        <div class="mb-4">
                            <label class="form-label fw-bold">Password</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="bi bi-lock"></i></span>
                                <input type="password" name="password" class="form-control form-control-lg" 
                                       placeholder="Choose a strong password" minlength="6" required>
                            </div>
                            <small class="text-muted">At least 6 characters</small>
                        </div>

                        <%-- Show error if registration failed --%>
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">
                                <i class="bi bi-exclamation-triangle"></i> ${error}
                            </div>
                        </c:if>

                        <button type="submit" class="btn btn-success btn-lg w-100 btn-register fw-bold">
                            Create My Journal
                        </button>
                    </form>

                    <div class="text-center mt-4">
                        <p class="mb-0">
                            Already have an account?
                            <a href="${pageContext.request.contextPath}/jsp/auth/login.jsp" class="fw-bold text-success">
                                Sign in here
                            </a>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>