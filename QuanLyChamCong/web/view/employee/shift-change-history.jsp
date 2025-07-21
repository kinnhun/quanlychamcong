<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lịch sử đổi ca</title>
    <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
</head>
<body>
    <div id="main-wrapper" data-theme="light" data-layout="vertical" data-navbarbg="skin6"
         data-sidebartype="full" data-sidebar-position="fixed" data-header-position="fixed"
         data-boxed-layout="full">

        <!-- Import header -->
        <c:import url="/view/compomnt/header.jsp"/>

        <!-- Import sidebar -->
        <c:import url="/view/compomnt/siderbar.jsp"/>

        <div class="page-wrapper">
            <div class="container-fluid">

                <c:import url="/view/compomnt/notification.jsp"/>

                <!-- Lịch sử đổi ca -->
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title mb-4">Lịch sử yêu cầu đổi ca</h4>
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped">
                                <thead class="thead-light">
                                    <tr>
                                        <th>Ngày</th>
                                        <th>Ca hiện tại</th>
                                        <th>Ca muốn đổi</th>
                                        <th>Lý do</th>
                                        <th>Trạng thái</th>
                                        <th>Thời gian gửi</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="r" items="${requests}">
                                        <tr>
                                            <td><fmt:formatDate value="${r.date}" pattern="dd/MM/yyyy"/></td>
                                            <td>${r.fromShiftId.shiftName} (${r.fromShiftId.startTime} - ${r.fromShiftId.endTime})</td>
                                            <td>${r.toShiftId.shiftName} (${r.toShiftId.startTime} - ${r.toShiftId.endTime})</td>
                                            <td>${r.reason}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${r.status == 'approved'}">
                                                        <span class="badge bg-success">Đã duyệt</span>
                                                    </c:when>
                                                    <c:when test="${r.status == 'rejected'}">
                                                        <span class="badge bg-danger">Từ chối</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-warning text-dark">Đang chờ</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td><fmt:formatDate value="${r.createdAt}" pattern="dd/MM/yyyy HH:mm"/></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>

                            <c:if test="${empty requests}">
                                <div class="text-center text-muted mt-3">Bạn chưa gửi yêu cầu đổi ca nào.</div>
                            </c:if>
                        </div>
                    </div>
                </div>

            </div>
        </div>

        <!-- Import footer -->
        <c:import url="/view/compomnt/footer.jsp"/>
    </div>
</body>
</html>
