<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>✏️ Sửa ca làm</title>
    <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
</head>
<body>
<div id="main-wrapper" data-theme="light" data-layout="vertical" data-navbarbg="skin6"
     data-sidebartype="full" data-sidebar-position="fixed" data-header-position="fixed"
     data-boxed-layout="full">

    <c:import url="/view/compomnt/header.jsp"/>
    <c:import url="/view/compomnt/siderbar.jsp"/>

    <div class="page-wrapper">
        <div class="container-fluid">
            <c:import url="/view/compomnt/notification.jsp"/>

            <div class="d-flex justify-content-between align-items-center mb-3">
                <h3 class="text-primary fw-bold fs-3 mb-0">✏️ Sửa ca làm</h3>
                <a href="${pageContext.request.contextPath}/admin/shifts" class="btn btn-outline-secondary">⬅️ Quay lại</a>
            </div>

            <form method="post" action="${pageContext.request.contextPath}/admin/shifts-edit" class="bg-white border rounded p-4 shadow-sm">
                <input type="hidden" name="shiftId" value="${shift.shiftId}" />

                <div class="mb-3">
                    <label class="form-label fw-bold">Tên ca</label>
                    <input type="text" name="shiftName" class="form-control" value="${shift.shiftName}" required />
                </div>

                <div class="row mb-3">
                    <div class="col">
                        <label class="form-label fw-bold">Bắt đầu</label>
                        <input type="time" name="startTime" class="form-control" value="${shift.startTime}" required />
                    </div>
                    <div class="col">
                        <label class="form-label fw-bold">Kết thúc</label>
                        <input type="time" name="endTime" class="form-control" value="${shift.endTime}" required />
                    </div>
                </div>

                <div class="mb-3">
                    <label class="form-label fw-bold">Mô tả</label>
                    <textarea name="description" class="form-control">${shift.description}</textarea>
                </div>

                <button type="submit" class="btn btn-success">💾 Cập nhật</button>
                <a href="${pageContext.request.contextPath}/admin/shifts" class="btn btn-secondary ms-2">Hủy</a>
            </form>
        </div>
    </div>

    <c:import url="/view/compomnt/footer.jsp"/>
</div>
</body>
</html>
