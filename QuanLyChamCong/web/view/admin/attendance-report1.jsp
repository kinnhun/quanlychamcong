<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Báo cáo tổng hợp chấm công</title>
    <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
</head>
<body>
<div id="main-wrapper" data-theme="light" data-layout="vertical" data-navbarbg="skin6" data-sidebartype="full"
     data-sidebar-position="fixed" data-header-position="fixed" data-boxed-layout="full">

    <c:import url="/view/compomnt/header.jsp"/>
    <c:import url="/view/compomnt/siderbar.jsp"/>

    <div class="page-wrapper">
        <div class="container-fluid">

            <c:import url="/view/compomnt/notification.jsp"/>

            <!-- Bộ lọc -->
 <!-- Bộ lọc -->
<form method="get" action="attendance-report1" class="row mb-4">
    <div class="col-md-3">
        <label for="fromDate">Từ ngày:</label>
        <input type="date" id="fromDate" name="fromDate" class="form-control" value="${fromDate}">
    </div>
    <div class="col-md-3">
        <label for="toDate">Đến ngày:</label>
        <input type="date" id="toDate" name="toDate" class="form-control" value="${toDate}">
    </div>
    <div class="col-md-4">
        <label for="employeeId">Nhân viên:</label>
        <select name="employeeId" id="employeeId" class="form-control">
            <option value="">-- Tất cả --</option>
            <c:forEach var="u" items="${usersList}">
                <option value="${u.userId}" ${u.userId == employeeId ? 'selected' : ''}>${u.fullName}</option>
            </c:forEach>
        </select>
    </div>
    <div class="col-md-2 align-self-end">
        <button type="submit" class="btn btn-primary w-100">Lọc</button>
    </div>
</form>

<form method="post" action="attendance-report1" class="mt-3">
    <input type="hidden" name="fromDate" value="${fromDate}">
    <input type="hidden" name="toDate" value="${toDate}">
    <input type="hidden" name="employeeId" value="${employeeId}">
    <input type="hidden" name="status" value="${status}">
    <button type="submit" class="btn btn-success">Xuất Excel</button>
</form>

            <!-- Bảng báo cáo -->
            <div class="card">
                <div class="card-body">
                    <h4 class="card-title">Báo cáo tổng hợp chấm công</h4>
                    <div class="table-responsive">
                        <table class="table table-bordered table-striped">
                            <thead>
                            <tr>
                                <th>STT</th>
                                <th>Nhân viên</th>
                                <th>Tổng giờ làm</th>
                                <th>Đi muộn</th>
                                <th>Về sớm</th>
                                <th>Nghỉ có phép</th>
                                <th>Nghỉ không phép</th>
                                <th>Ngày công</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="u" items="${usersList}" varStatus="loop">
                                <c:set var="summary" value="${summaryMap[u.userId]}" />
                                <tr>
                                    <td>${loop.index + 1}</td>
                                    <td>${u.fullName}</td>
                                    <td><fmt:formatNumber value="${summary[0]}" type="number" maxFractionDigits="2" /></td>
                                    <td>${summary[1]}</td>
                                    <td>${summary[2]}</td>
                                    <td>${summary[3]}</td>
                                    <td>${summary[4]}</td>
                                    <td>${summary[5]}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <c:import url="/view/compomnt/footer.jsp"/>
</div>
</body>
</html>
