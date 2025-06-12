<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>➕ Thêm ca làm</title>
    <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
    <style>
        .form-wrapper {
            max-width: 700px;
            margin: 0 auto;
            margin-top: 30px;
            background-color: #ffffff;
            border-radius: 12px;
            padding: 30px;
            box-shadow: 0 3px 10px rgba(0, 0, 0, 0.05);
        }

        .form-wrapper label {
            font-weight: 600;
        }

        .form-control {
            border-radius: 10px;
            font-size: 15px;
            padding: 10px 14px;
        }

        .form-control:focus {
            border-color: #6c63ff;
            box-shadow: 0 0 0 0.15rem rgba(108, 99, 255, 0.2);
        }

        .btn-success {
            background-color: #6c63ff;
            border-color: #6c63ff;
        }

        .btn-success:hover {
            background-color: #5a52db;
            border-color: #5a52db;
        }
    </style>
</head>
<body>
<div id="main-wrapper" data-theme="light" data-layout="vertical" data-navbarbg="skin6"
     data-sidebartype="full" data-sidebar-position="fixed" data-header-position="fixed"
     data-boxed-layout="full">

    <c:import url="/view/compomnt/header.jsp" />
    <c:import url="/view/compomnt/siderbar.jsp" />

    <div class="page-wrapper">
        <div class="container-fluid">

            <c:import url="/view/compomnt/notification.jsp" />

            <div class="form-wrapper">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h3 class="text-primary fw-bold mb-0">➕ Thêm ca làm mới</h3>
                    <a href="${pageContext.request.contextPath}/admin/shifts" class="btn btn-outline-secondary">
                        ⬅️ Quay lại
                    </a>
                </div>

                <form method="post" action="${pageContext.request.contextPath}/admin/shifts-add">
                    <div class="mb-3">
                        <label class="form-label">Tên ca làm <span class="text-danger">*</span></label>
                        <input type="text" name="shift_name" class="form-control" required placeholder="Ví dụ: Ca sáng, Ca đêm..." />
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Giờ bắt đầu (24h) <span class="text-danger">*</span></label>
                        <input type="time" name="start_time" class="form-control" required />
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Giờ kết thúc (24h) <span class="text-danger">*</span></label>
                        <input type="time" name="end_time" class="form-control" required />
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Mô tả</label>
                        <textarea name="description" class="form-control" rows="3" placeholder="Ghi chú về ca làm nếu có..."></textarea>
                    </div>

                    <div class="d-flex justify-content-end">
                        <button type="submit" class="btn btn-success">
                            💾 Lưu ca làm
                        </button>
                    </div>
                </form>
            </div>

        </div>
    </div>

    <c:import url="/view/compomnt/footer.jsp" />
</div>
</body>
</html>
