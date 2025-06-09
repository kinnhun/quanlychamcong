<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Thêm mới loại nghỉ phép</title>
        <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
        <!-- Thêm Bootstrap 5 cho đẹp hơn -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
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

                    <!-- Notification -->
                    <c:import url="/view/compomnt/notification.jsp" />

                    <!-- Title + Back -->
                    <div class="d-flex justify-content-between align-items-center mt-4 mb-4">
                        <h3 class="fw-bold text-primary mb-0">Thêm mới loại nghỉ phép</h3>
                        <a href="${pageContext.request.contextPath}/manager/leave-types" class="btn btn-outline-secondary rounded-pill px-4 py-2">
                            ← Quay lại danh sách
                        </a>
                    </div>

                    <!-- Form Thêm Mới (giữa màn hình) -->
                    <div class="row justify-content-center">
                        <div class="col-md-8 col-lg-6">
                            <div class="card shadow-sm border-0 rounded-4">
                                <div class="card-body p-5">

                                    <h4 class="fw-bold text-center text-primary mb-4">Nhập thông tin loại nghỉ phép</h4>

                                    <form action="${pageContext.request.contextPath}/manager/leave-types" method="post">
                                        <input type="hidden" name="action" value="add">

                                        <div class="mb-4">
                                            <label class="form-label fw-bold">Tên loại nghỉ phép</label>
                                            <input type="text" name="leaveTypeName" class="form-control form-control-lg" required placeholder="Nhập tên loại nghỉ phép...">
                                        </div>

                                        <div class="d-flex justify-content-center mt-4">
                                            <button type="submit" class="btn btn-success rounded-pill px-5 py-2 me-3">
                                                <i class="bi bi-save"></i> Lưu
                                            </button>
                                            <a href="${pageContext.request.contextPath}/manager/leave-type" class="btn btn-outline-secondary rounded-pill px-5 py-2">
                                                Hủy
                                            </a>
                                        </div>
                                    </form>

                                </div> <!-- card-body -->
                            </div> <!-- card -->
                        </div> <!-- col -->
                    </div> <!-- row -->

                </div> <!-- container-fluid -->
            </div> <!-- page-wrapper -->

        </div> <!-- main-wrapper -->

        <!-- Import footer -->
        <c:import url="/view/compomnt/footer.jsp" />

        <!-- Bootstrap 5 JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>
