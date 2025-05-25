<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Danh sách tài khoản</title>
        <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
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






                    <div class="card">
                        <div class="card-body">
                            <h4 class="card-title mb-4">Chỉnh sửa tài khoản</h4>

                            <form action="${pageContext.request.contextPath}/admin/user-edit" method="post">
                                <input type="hidden" name="userId" value="${user.userId}" />

                                <div class="row">
                                    <!-- Cột trái -->
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Tài khoản</label>
                                            <input type="text" name="username" class="form-control" value="${user.username}" readonly />
                                        </div>

                                        <div class="form-group">
                                            <label>Email</label>
                                            <input type="email" name="email" class="form-control" value="${user.email}" required />
                                        </div>

                                        <div class="form-group">
                                            <label>Trạng thái</label>
                                            <select name="status" class="form-control">
                                                <option value="active" ${user.status == 'active' ? 'selected' : ''}>Đang hoạt động</option>
                                                <option value="banned" ${user.status == 'banned' ? 'selected' : ''}>Đã bị khóa</option>
                                            </select>
                                        </div>

                                        <div class="form-group">
                                            <label>Lý do khóa (nếu có)</label>
                                            <textarea name="banReason" class="form-control" rows="4">${user.banReason}</textarea>
                                        </div>
                                    </div>

                                    <!-- Cột phải -->
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Họ tên</label>
                                            <input type="text" name="fullName" class="form-control" value="${user.fullName}" required />
                                        </div>

                                        <div class="form-group">
                                            <label>Số điện thoại</label>
                                            <input type="text" name="phone" class="form-control" value="${user.phone}" />
                                        </div>

                                        <div class="form-group">
                                            <label>Vai trò</label>
                                            <select name="role" class="form-control">
                                                <option value="admin" ${user.role == 'admin' ? 'selected' : ''}>Admin</option>
                                                <option value="manager" ${user.role == 'manager' ? 'selected' : ''}>Manager</option>
                                                <option value="employee" ${user.role == 'employee' ? 'selected' : ''}>Employee</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>

                                <div class="mt-3">
                                    <button type="submit" class="btn btn-primary">Cập nhật</button>
                                    <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-secondary">⬅ Quay lại</a>
                                </div>
                            </form>
                        </div>
                    </div>








                </div>   

            </div>   
        </div>

        <!-- Import footer -->
        <c:import url="/view/compomnt/footer.jsp" />


    </div>
</body>
</html>
