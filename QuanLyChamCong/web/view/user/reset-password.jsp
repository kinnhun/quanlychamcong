<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đặt lại mật khẩu</title>
    <meta charset="UTF-8">
    <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
</head>
<body>
<div class="main-wrapper d-flex justify-content-center align-items-center" style="height: 100vh;">
    <div class="bg-white p-4 rounded shadow" style="width: 400px;">
        <h3 class="text-center mb-3">Đặt lại mật khẩu</h3>

        <c:import url="/view/compomnt/notification.jsp"/>

        <form method="post" action="${pageContext.request.contextPath}/reset-password">
            <input type="hidden" name="token" value="${param.token}" />

            <div class="form-group">
                <label>Mật khẩu mới</label>
                <input type="password" name="newPassword" class="form-control" required>
            </div>
            <div class="form-group">
                <label>Xác nhận mật khẩu</label>
                <input type="password" name="confirmPassword" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-primary btn-block mt-3">Cập nhật mật khẩu</button>
        </form>
    </div>
</div>
</body>
</html>
