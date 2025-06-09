<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Thông tin cá nhân</title>

        <!-- Bootstrap 5 CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" 
              integrity="sha384-ENjdO4Dr2bkBIFxQpeo9Yz1K/3p5lfb5VVD1CZ/5VxR0ZlH3aCj28anvfW+L9BwF" crossorigin="anonymous">

        <!-- SweetAlert2 CSS -->
        <link href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css" rel="stylesheet">

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

                    <!-- Card Profile -->
                    <div class="col-lg-8 col-md-10 col-sm-12 mx-auto">
                        <div class="card shadow-sm border-0 rounded-4 mt-4 mb-4">
                            <div class="card-header bg-white text-center border-0 pb-0">
                                <h3 class="fw-bold mb-1 text-primary">Thông tin cá nhân</h3>
                                <p class="text-muted mb-0">Chi tiết tài khoản của bạn</p>
                            </div>

                            <div class="card-body px-5 py-4">
                                <div class="row">
                                    <!-- Cột trái -->
                                    <div class="col-md-6">
                                        <div class="mb-4">
                                            <label class="form-label text-muted mb-1">Họ và tên</label>
                                            <div class="fs-5 fw-semibold text-dark">${sessionScope.user.fullName}</div>
                                        </div>

                                        <div class="mb-4">
                                            <label class="form-label text-muted mb-1">Tên đăng nhập</label>
                                            <div class="fs-5 text-dark">${sessionScope.user.username}</div>
                                        </div>

                                        <div class="mb-4">
                                            <label class="form-label text-muted mb-1">Email</label>
                                            <div class="fs-5 text-dark">${sessionScope.user.email}</div>
                                        </div>

                                        <div class="mb-4">
                                            <label class="form-label text-muted mb-1">Số điện thoại</label>
                                            <div class="fs-5 text-dark">${sessionScope.user.phone}</div>
                                        </div>
                                    </div>

                                    <!-- Cột phải -->
                                    <div class="col-md-6">
                                        <div class="mb-4">
                                            <label class="form-label text-muted mb-1">Vai trò</label>
                                            <div class="fs-5 text-dark">${sessionScope.user.role}</div>
                                        </div>

                                        <div class="mb-4">
                                            <label class="form-label text-muted mb-1">Loại hợp đồng</label>
                                            <div class="fs-5 text-dark">
                                                <c:choose>
                                                    <c:when test="${not empty sessionScope.user.employmentType}">
                                                        ${sessionScope.user.employmentType}
                                                    </c:when>
                                                    <c:otherwise>
                                                        N/A
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>

                                        <div class="mb-4">
                                            <label class="form-label text-muted mb-1">Trạng thái</label>
                                            <div class="fs-5 text-dark">${sessionScope.user.status}</div>
                                        </div>

                                        <div class="mb-4">
                                            <label class="form-label text-muted mb-1">Ngày tạo</label>
                                            <div class="fs-5 text-dark">${sessionScope.user.createdAt}</div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Các nút chức năng -->
                                <div class="text-center mt-4">
                                    <a href="${pageContext.request.contextPath}/logout" 
                                       class="btn btn-outline-dark rounded-pill px-4 py-2 fs-5 mb-2">
                                        Đăng xuất
                                    </a>

                                    <a href="${pageContext.request.contextPath}/change-password" 
                                       class="btn btn-outline-primary rounded-pill px-4 py-2 fs-5 mb-2 ms-2">
                                        Đổi mật khẩu
                                    </a>

                                    <a href="${pageContext.request.contextPath}/edit-profile"
                                       class="btn btn-outline-success rounded-pill px-4 py-2 fs-5 mb-2 ms-2">
                                        Chỉnh sửa thông tin
                                    </a>






                                </div>
                            </div>
                        </div>
                    </div>

                </div> <!-- container-fluid -->
            </div> <!-- page-wrapper -->

        </div> <!-- main-wrapper -->

        <!-- Import footer -->
        <c:import url="/view/compomnt/footer.jsp" />


    </body>
</html>
