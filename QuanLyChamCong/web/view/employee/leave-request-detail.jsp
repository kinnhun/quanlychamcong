<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                <div class="container-fluid" style="background-color: #ffffff">
                    <!-- Thông báo -->
                    <c:import url="/view/compomnt/notification.jsp" />









                    <h3>📄 Chi tiết đơn xin nghỉ phép</h3>
                    <table class="table table-bordered">
                        <tr><th>Người gửi</th><td>${requestDetail.user.fullName}</td></tr>
                        <tr><th>Loại nghỉ</th><td>${requestDetail.leaveType}</td></tr>
                        <tr>
                            <th>Thời gian</th>
                            <td>
                        <fmt:formatDate value="${requestDetail.startDate}" pattern="dd/MM/yyyy" /> -
                        <fmt:formatDate value="${requestDetail.endDate}" pattern="dd/MM/yyyy" />
                        </td>
                        </tr>
                        <tr><th>Số ngày</th><td>${requestDetail.daysCount}</td></tr>
                        <tr><th>Lý do</th><td>${requestDetail.reason}</td></tr>
                        <tr><th>Trạng thái</th>
                            <td>
                                <c:choose>
                                    <c:when test="${requestDetail.status == 'approved'}">✅ Đã duyệt</c:when>
                                    <c:when test="${requestDetail.status == 'rejected'}">❌ Từ chối</c:when>
                                    <c:when test="${requestDetail.status == 'canceled'}">🚫 Đã hủy</c:when>
                                    <c:otherwise>⏳ Đang chờ</c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr><th>Ngày tạo</th><td><fmt:formatDate value="${requestDetail.createdAt}" pattern="dd/MM/yyyy HH:mm" /></td></tr>
                        <tr><th>Người duyệt</th><td>${requestDetail.approvedBy != null ? requestDetail.approvedBy.fullName : 'Chưa có'}</td></tr>
                        <tr><th>Ghi chú</th><td>${requestDetail.approveComment}</td></tr>
                    </table>
                    <a href="${pageContext.request.contextPath}/employee/leave-requests" class="btn btn-secondary">🔙 Quay lại</a>







                </div>   

            </div>   
        </div>

        <!-- Import footer -->
        <c:import url="/view/compomnt/footer.jsp" />


    </div>
</body>
</html>
