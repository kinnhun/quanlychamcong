<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Thêm cấu hình ngày phép</title>
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

                    <h3 class="fw-bold text-primary mt-4 mb-4">Thêm cấu hình ngày phép</h3>

<form action="${pageContext.request.contextPath}/manager/leave-config-add" method="post" 
      class="border p-4 rounded bg-white shadow-sm" style="max-width: 600px; margin: 0 auto;">
    <div class="mb-3">
        <label class="form-label fw-bold">Năm</label>
        <input type="number" name="year" class="form-control form-control-lg" required min="2000" max="2100">
    </div>

    <div class="mb-3">
        <label class="form-label fw-bold">Loại nghỉ phép</label>
        <select name="leaveTypeId" class="form-control form-control-lg" required>
            <option value="">-- Chọn loại nghỉ phép --</option>
            <c:forEach var="type" items="${leaveTypes}">
                <option value="${type.leaveTypeId}">${type.leaveTypeName}</option>
            </c:forEach>
        </select>
    </div>

    <div class="mb-3">
        <label class="form-label fw-bold">Số ngày mặc định</label>
        <input type="number" name="defaultDays" class="form-control form-control-lg" required min="0">
    </div>

    <div class="text-center mt-4">
        <a href="${pageContext.request.contextPath}/manager/leave-config" class="btn btn-secondary px-4 py-2 me-2">Huỷ</a>
        <button type="submit" class="btn btn-success px-4 py-2">Thêm mới</button>
    </div>
</form>




                </div>
            </div>
        </div>

        <!-- Import footer -->
        <c:import url="/view/compomnt/footer.jsp" />

    </body>
</html>
