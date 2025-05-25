<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Thêm tài khoản mới</title>
    <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
</head>
<body>
<div id="main-wrapper" data-theme="light" data-layout="vertical" data-navbarbg="skin6"
     data-sidebartype="full" data-sidebar-position="fixed" data-header-position="fixed" data-boxed-layout="full">

    <!-- Header -->
    <c:import url="/view/compomnt/header.jsp"/>

    <!-- Sidebar -->
    <c:import url="/view/compomnt/siderbar.jsp"/>

    <div class="page-wrapper">
        <div class="container-fluid">
            <!-- Thông báo -->
            <c:import url="/view/compomnt/notification.jsp"/>

            <div class="card">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <h4 class="card-title mb-0">Thêm tài khoản mới</h4>
                        <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-secondary">⬅ Quay lại</a>
                    </div>

                    <form action="${pageContext.request.contextPath}/admin/user-add" method="post">
                        <div class="row">
                            <!-- Nửa trái -->
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Email</label>
                                    <input type="email" name="email" class="form-control" required
                                           placeholder="nhập email hợp lệ..." oninput="autoFillUsername(this.value)">
                                </div>

                                <div class="form-group">
                                    <label>Tài khoản</label>
                                    <input type="text" name="username" class="form-control" id="username" readonly required>
                                </div>

                                <div class="form-group">
                                    <label>Mật khẩu</label>
                                    <input type="text" name="password" class="form-control" id="password">
                                </div>
                            </div>

                            <!-- Nửa phải -->
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Họ tên</label>
                                    <input type="text" name="fullName" class="form-control" required>
                                </div>

                                <div class="form-group">
                                    <label>Số điện thoại</label>
                                    <input type="text" name="phone" class="form-control">
                                </div>

                                <div class="form-group">
                                    <label>Vai trò</label>
                                    <select name="role" class="form-control">
                                        <option value="admin">Admin</option>
                                        <option value="manager">Manager</option>
                                        <option value="employee">Employee</option>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <div class="text-right">
                            <button type="submit" class="btn btn-primary">Tạo tài khoản</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <c:import url="/view/compomnt/footer.jsp"/>
</div>

<script>
    function autoFillUsername(email) {
        if (email.includes('@')) {
            const username = email.split('@')[0];
            document.getElementById("username").value = username;

            // Tự động gán mật khẩu random
            const password = generateRandomPassword(8);
            document.getElementById("password").value = password;
        }
    }

    function generateRandomPassword(length) {
        const chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        let result = "";
        for (let i = 0; i < length; i++) {
            result += chars.charAt(Math.floor(Math.random() * chars.length));
        }
        return result;
    }
</script>
</body>
</html>
