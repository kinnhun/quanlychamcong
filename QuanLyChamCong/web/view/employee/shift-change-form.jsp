<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gửi yêu cầu đổi ca</title>
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
            <!-- Thông báo -->
            <c:import url="/view/compomnt/notification.jsp"/>

            <!-- Nội dung form đổi ca -->
            <div class="card">
                <div class="card-body">
                    <h4 class="card-title">Gửi yêu cầu đổi ca</h4>
                    <form action="${pageContext.request.contextPath}/employee/shift-change-request" method="post">

                        <!-- userId (ẩn) -->
                        <input type="hidden" name="userId" value="${sessionScope.user.userId}"/>

                        <!-- Ngày đổi ca -->
                        <div class="form-group">
                            <label for="date">Ngày cần đổi</label>
                            <input type="date" name="date" id="date" class="form-control" required/>
                        </div>

                        <!-- Ca hiện tại -->
                        <div class="form-group">
                            <label for="fromShiftId">Ca hiện tại</label>
                            <select name="fromShiftId" id="fromShiftId" class="form-control" required>
                                <option value="">-- Chọn ca --</option>
                                <c:forEach var="s" items="${shifts}">
                                    <option value="${s.shiftId}">
                                        ${s.shiftName} (${s.startTime} - ${s.endTime})
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <!-- Ca muốn đổi sang -->
                        <div class="form-group">
                            <label for="toShiftId">Đổi sang ca</label>
                            <select name="toShiftId" id="toShiftId" class="form-control" required>
                                <option value="">-- Chọn ca --</option>
                                <c:forEach var="s" items="${shifts}">
                                    <option value="${s.shiftId}">
                                        ${s.shiftName} (${s.startTime} - ${s.endTime})
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <!-- Lý do -->
                        <div class="form-group">
                            <label for="reason">Lý do đổi ca</label>
                            <textarea name="reason" id="reason" class="form-control" rows="3" required></textarea>
                        </div>

                        <button type="submit" class="btn btn-primary">Gửi yêu cầu</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Import footer -->
    <c:import url="/view/compomnt/footer.jsp"/>
</div>
</body>
</html>
