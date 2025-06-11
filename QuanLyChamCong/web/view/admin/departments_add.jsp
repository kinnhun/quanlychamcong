<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>➕ Thêm phòng ban mới</title>
        <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
        <style>
            .form-wrapper {
                max-width: 700px;
                margin: 0 auto;
                margin-top: 40px;
                background-color: #fefefe;
                border: 1px solid #e3e6ea;
                border-radius: 12px;
                padding: 30px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
            }

            .form-wrapper h3 {
                font-size: 24px;
            }

            .form-control, textarea {
                border-radius: 10px;
                font-size: 15px;
                padding: 10px 14px;
                transition: 0.2s ease-in-out;
            }

            .form-control:focus {
                border-color: #6c63ff;
                box-shadow: 0 0 0 0.15rem rgba(108, 99, 255, 0.2);
            }

            button.btn-primary {
                background-color: #6c63ff;
                border-color: #6c63ff;
            }

            button.btn-primary:hover {
                background-color: #5a52db;
                border-color: #5a52db;
            }
        </style>
    </head>
    <body>
        <div id="main-wrapper" data-theme="light" data-layout="vertical" data-navbarbg="skin6"
             data-sidebartype="full" data-sidebar-position="fixed" data-header-position="fixed"
             data-boxed-layout="full">

            <!-- Header và Sidebar -->
            <c:import url="/view/compomnt/header.jsp" />
            <c:import url="/view/compomnt/siderbar.jsp" />

            <div class="page-wrapper">
                <div class="container-fluid" style="background-color: #ffffff">

                    <!-- Thông báo -->
                    <c:import url="/view/compomnt/notification.jsp" />

                    <div class="form-wrapper">
                        <div class="d-flex justify-content-between align-items-center mb-4">
                            <h3 class="text-primary fw-bold mb-0">➕ Thêm phòng ban mới</h3>
                            <a href="${pageContext.request.contextPath}/admin/location-departments" class="btn btn-outline-secondary">
                                ⬅️ Quay lại
                            </a>
                        </div>



                        <form action="${pageContext.request.contextPath}/admin/departments-add" method="post">
                            <div class="mb-3">
                                <label class="form-label fw-semibold">Tên phòng ban <span class="text-danger">*</span></label>
                                <input type="text" name="name" class="form-control" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label fw-semibold">Mã phòng ban <span class="text-danger">*</span></label>
                                <input type="text" name="code" class="form-control" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label fw-semibold">Mô tả</label>
                                <textarea name="description" class="form-control" rows="3"></textarea>
                            </div>

                            <div class="d-flex justify-content-end">
                                <button type="submit" class="btn btn-primary">
                                    💾 Lưu phòng ban
                                </button>
                            </div>
                        </form>
                    </div>

                </div>
            </div>

            <!-- Footer -->
            <c:import url="/view/compomnt/footer.jsp" />
        </div>
    </body>
</html>
