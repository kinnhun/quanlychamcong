<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Báo cáo Chấm Công</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- CSS tùy chỉnh -->
    <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
</head>
<body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<div id="main-wrapper" data-theme="light" data-layout="vertical" data-navbarbg="skin6" data-sidebartype="full"
     data-sidebar-position="fixed" data-header-position="fixed" data-boxed-layout="full">

    <!-- Import header -->
    <c:import url="/view/compomnt/header.jsp" />

    <!-- Import sidebar -->
    <c:import url="/view/compomnt/siderbar.jsp" />

    <div class="page-wrapper">
        <div class="container-fluid py-4">
            <!-- Thông báo -->
            <c:import url="/view/compomnt/notification.jsp" />

            <!-- Dòng thứ hai: Nội dung chính -->
            <div class="row">
                <!-- Cột trái: Lọc báo cáo chấm công -->
                <div class="col-md-4">
                    <!-- Form lọc báo cáo -->
                    <div class="card mb-4">
                        <div class="card-header bg-primary-custom text-dark">Lọc Báo Cáo Chấm Công</div>
                        <div class="card-body">
                            <form method="get" action="${pageContext.request.contextPath}/admin/attendance-report">
                                <div class="mb-3">
                                    <label for="fromDate" class="form-label">Từ Ngày</label>
                                    <input type="date" id="fromDate" name="fromDate" class="form-control" value="${param.fromDate}">
                                </div>
                                <div class="mb-3">
                                    <label for="toDate" class="form-label">Đến Ngày</label>
                                    <input type="date" id="toDate" name="toDate" class="form-control" value="${param.toDate}">
                                </div>
                                <div class="mb-3">
                                    <label for="employeeId" class="form-label">Nhân Viên</label>
                                    <select name="employeeId" class="form-control" id="employeeId">
                                        <option value="">Tất Cả</option>
                                        <c:forEach items="${usersList}" var="user">
                                            <option value="${user.userId}" ${user.userId == param.employeeId ? 'selected' : ''}>${user.fullName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="mb-3">
                                    <label for="status" class="form-label">Trạng Thái</label>
                                    <select name="status" class="form-control" id="status">
                                        <option value="">Tất Cả</option>
                                        <option value="present" ${'present' == param.status ? 'selected' : ''}>Có mặt</option>
                                        <option value="absent" ${'absent' == param.status ? 'selected' : ''}>Vắng</option>
                                        <option value="late" ${'late' == param.status ? 'selected' : ''}>Tăng ca</option>
                                    </select>
                                </div>
                                <button type="submit" class="btn btn-primary">Lọc</button>
                            </form>
                        </div>
                    </div>
                </div>

                <!-- Cột giữa: Danh sách chấm công -->
                <div class="col-md-8">
                    <div class="card mb-4">
                        <div class="card-header">Danh sách chấm công</div>
                        <div class="card-body">
                            <table class="table table-bordered">
                                <thead>
                                    <tr>
                                        <th>Nhân Viên</th>
                                        <th>Ngày</th>
                                        <th>Giờ vào</th>
                                        <th>Giờ ra</th>
                                        <th>Trạng thái</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${attendanceList}" var="attendance">
                                        <tr>
                                            <td>${attendance.user.fullName}</td>
                                            <td>${attendance.date}</td>
                                            <td>${attendance.checkinTime != null ? attendance.checkinTime : 'Chưa chấm công'}</td>
                                            <td>${attendance.checkoutTime != null ? attendance.checkoutTime : 'Chưa chấm công'}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${attendance.checkinTime != null && attendance.checkoutTime != null}">Có mặt</c:when>
                                                    <c:when test="${attendance.checkinTime == null && attendance.checkoutTime == null}">Vắng</c:when>
                                                    <c:otherwise>Tăng ca</c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <!-- Nút xuất Excel -->
                            <form method="post" action="${pageContext.request.contextPath}/admin/attendance-report">
                                <input type="hidden" name="fromDate" value="${param.fromDate}">
                                <input type="hidden" name="toDate" value="${param.toDate}">
                                <input type="hidden" name="employeeId" value="${param.employeeId}">
                                <input type="hidden" name="status" value="${param.status}">
                                <button type="submit" class="btn btn-success">Xuất Excel</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

<!-- Import footer -->
<c:import url="/view/compomnt/footer.jsp" />

</body>
</html>
