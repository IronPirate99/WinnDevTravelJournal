<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login • Traveling Journal</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <style>
        body, html { height: 100%; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); }
        .login-card { border-radius: 20px; box-shadow: 0 15px 35px rgba(0,0,0,0.3); }
        .btn-login { background: #667eeaaff; border: none; }
        .btn-login:hover { background: #5a67d8; }
    </style>
</head>
<body class="d-flex align-items-center">

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6 col-lg-5 col-xl-4">
            <div class="card login-card overflow-hidden">
                <div class="card-header bg-primary text-white text-center py-4">
                    <h2 class="mb-0">
                        <i class="bi bi-journal-richtext"></i> Traveling Journal
                    </h2>
                    <p class="mb-0 mt-2 opacity-75">Welcome back, traveler!</p>
                </div>

                <div class="card-body p-5">
                    <form action="${pageContext.request.contextPath}/login" method="post">
                        <div class="mb-4">
                            <label class="form-label fw-bold">Email Address</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="bi bi-envelope"></i></span>
                                <input type="email" name="email" class="form-control form-control-lg" 
                                       placeholder="you@example.com" required autofocus>
                            </div>
                        </div>

                        <div class="mb-4">
                            <label class="form-label fw-bold">Password</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="bi bi-lock"></i></span>
                                <input type="password" name="password" class="form-control form-control-lg" 
                                       placeholder="••••••••" required>
                            </div>
                        </div>

                        <%-- Show error message if login failed --%>
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">
                                <i class="bi bi-exclamation-triangle"></i> ${error}
                            </div>
                        </c:if>

                        <button type="submit" class="btn btn-primary btn-lg w-100 btn-login text-white fw-bold">
                            Sign In
                        </button>
                    </form>

                    <div class="text-center mt-4">
                        <p class="mb-0">
                            Don't have an account?
                            <a href="${pageContext.request.contextPath}/jsp/auth/register.jsp" class="fw-bold text-primary">
                                Create one free
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