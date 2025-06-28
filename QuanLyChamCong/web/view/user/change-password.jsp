<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đổi mật khẩu</title>
    <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
    <style>
        .page-wrapper .container-fluid {
            padding: 20px;
        }
        .form-container {
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            max-width: 400px;
            margin: 0 auto;
        }
        .form-group { margin-bottom: 15px; }
        .form-group label { font-weight: 500; margin-bottom: 5px; display: block; }
        .form-control { width: 100%; padding: 8px; border: 1px solid #ced4da; border-radius: 4px; }
        .btn-primary { width: 100%; padding: 10px; font-weight: 600; }
        .error { color: red; font-size: 14px; margin-top: 5px; }
    </style>
    <script>
        function validateForm() {
            var oldPass = document.getElementsByName("oldPassword")[0].value;
            var newPass = document.getElementsByName("newPassword")[0].value;
            var confirmPass = document.getElementsByName("confirmPassword")[0].value;
            if (newPass.length < 6) { alert("Mật khẩu mới phải ≥ 6 ký tự."); return false; }
            if (newPass !== confirmPass) { alert("Mật khẩu không khớp."); return false; }
            if (!oldPass) { alert("Nhập mật khẩu cũ."); return false; }
            return true;
        }
    </script>
</head>
<body>
<div id="main-wrapper" data-theme="light" data-layout="vertical" data-navbarbg="skin6" data-sidebartype="full"
     data-sidebar-position="fixed" data-header-position="fixed" data-boxed-layout="full">

    <!-- Import header -->
    <c:import url="/view/compomnt/header.jsp" />

    <!-- Import sidebar -->
    <c:import url="/view/compomnt/siderbar.jsp" />

    <div class="page-wrapper">
        <div class="container-fluid">
            <!-- Thông báo -->
            <c:import url="/view/compomnt/notification.jsp" />

            <h3 class="text-center mb-3">Đổi mật khẩu</h3>
            <div class="form-container">
                <form method="post" action="${pageContext.request.contextPath}/change-password" onsubmit="return validateForm()">
                    <div class="form-group">
                        <label>Mật khẩu cũ</label>
                        <input type="password" name="oldPassword" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label>Mật khẩu mới</label>
                        <input type="password" name="newPassword" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label>Xác nhận mật khẩu</label>
                        <input type="password" name="confirmPassword" class="form-control" required>
                    </div>
                    <c:if test="${not empty error}"><div class="error">${error}</div></c:if>
                    <button type="submit" class="btn btn-primary mt-3">Đổi mật khẩu</button>
                </form>
            </div>
        </div>
    </div>

    <!-- Import footer -->
    <c:import url="/view/compomnt/footer.jsp" />
</div>
</body>
</html>