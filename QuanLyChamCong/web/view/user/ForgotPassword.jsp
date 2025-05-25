<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Quên mật khẩu</title>
        <meta charset="UTF-8">
        <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="main-wrapper d-flex justify-content-center align-items-center" style="height: 100vh;">
            <div class="bg-white p-4 rounded shadow" style="width: 350px;">
                <h3 class="text-center mb-3">Khôi phục mật khẩu</h3>
                <p class="text-center">Nhập địa chỉ email bạn đã đăng ký để nhận mã khôi phục.</p>

                <c:import url="/view/compomnt/notification.jsp"/>

                <form action="${pageContext.request.contextPath}/forgot-password" method="post">
                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" name="email" class="form-control" required placeholder="Nhập email">
                    </div>
                    <button type="submit" class="btn btn-primary btn-block mt-3">Gửi mã</button>
                    <a href="${pageContext.request.contextPath}/login" class="btn btn-link d-block text-center mt-2">Quay lại đăng nhập</a>
                </form>
            </div>
        </div>
    </body>
</html>
