<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Quản lý khiếu nại</title>
    <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
    <style>
        .card {
            border: none;
            border-radius: 12px;
            box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
            margin-bottom: 24px;
            background: linear-gradient(135deg, #ffffff, #f8f9fa);
        }
        .card-header {
            background: #fff;
            border-bottom: 1px solid #e9ecef;
            padding: 16px 24px;
            border-top-left-radius: 12px;
            border-top-right-radius: 12px;
            display: flex;
            align-items: center;
        }
        .card-title {
            margin: 0;
            font-size: 1.5rem;
            font-weight: 600;
            color: #1a73e8;
        }
        .table-responsive {
            margin-bottom: 20px;
            border-radius: 8px;
            overflow: hidden;
        }
        .table {
            margin-bottom: 0;
            background: #fff;
        }
        .table th, .table td {
            vertical-align: middle;
            padding: 12px;
            font-size: 14px;
        }
        .table th {
            background: #f1f3f5;
            color: #495057;
            font-weight: 600;
        }
        .table td {
            color: #343a40;
        }
        .badge {
            padding: 8px 16px;
            border-radius: 16px;
            font-weight: 600;
            font-size: 13px;
            text-transform: capitalize;
            transition: background-color 0.3s ease;
        }
        .badge-warning {
            background: #fff3cd;
            color: #856404;
        }
        .badge-success {
            background: #d4edda;
            color: #155724;
        }
        .badge-danger {
            background: #f8d7da;
            color: #721c24;
        }
        .badge:hover {
            filter: brightness(90%);
        }
        a[target="_blank"] {
            color: #007bff;
            text-decoration: none;
            transition: color 0.3s ease;
        }
        a[target="_blank"]:hover {
            color: #0056b3;
            text-decoration: underline;
        }
        .text-muted {
            font-style: italic;
        }
        .filter-form {
            background: #fff;
            border-radius: 10px;
            margin-bottom: 20px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
            padding: 20px;
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            align-items: flex-end;
        }
        .filter-form label {
            font-weight: 600;
            color: #495057;
            margin-bottom: 6px;
            font-size: 14px;
        }
        .filter-form input, .filter-form select {
            min-width: 180px;
            max-width: 220px;
            border-radius: 8px;
            border: 1px solid #ced4da;
            padding: 8px 12px;
            font-size: 14px;
        }
        .filter-form input:focus, .filter-form select:focus {
            border-color: #007bff;
            box-shadow: 0 0 5px rgba(0, 123, 255, 0.3);
            outline: none;
        }
        .filter-form .btn {
            border-radius: 8px;
            padding: 8px 20px;
            font-weight: 600;
            background-color: #007bff;
            border: none;
            color: #fff;
        }
        .filter-form .btn:hover {
            background-color: #0056b3;
        }
        @media (max-width: 768px) {
            .card {
                margin-bottom: 16px;
            }
            .card-header {
                padding: 12px 16px;
            }
            .card-title {
                font-size: 1.25rem;
            }
            .table th, .table td {
                font-size: 13px;
                padding: 8px;
            }
            .badge {
                padding: 6px 12px;
                font-size: 12px;
            }
            .filter-form {
                padding: 15px;
                gap: 15px;
            }
            .filter-form input, .filter-form select {
                min-width: 150px;
                max-width: 100%;
            }
            .filter-form .btn {
                width: 100%;
            }
        }
    </style>
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
                <!-- Thông báo -->
                <c:import url="/view/compomnt/notification.jsp" />

                <!-- Form tìm kiếm và lọc -->
                <form method="get" class="filter-form" action="${pageContext.request.contextPath}/manager/dispute-list">
                    <div>
                        <label>Tìm kiếm:</label>
                        <input type="text" name="search" value="${search}" placeholder="Tìm theo tên, lý do..." class="form-control"/>
                    </div>
                    <div>
                        <label>Trạng thái:</label>
                        <select name="status" class="form-select">
                            <option value="">-- Tất cả --</option>
                            <option value="pending" ${status eq 'pending' ? 'selected' : ''}>Đang chờ</option>
                            <option value="approved" ${status eq 'approved' ? 'selected' : ''}>Đã duyệt</option>
                            <option value="rejected" ${status eq 'rejected' ? 'selected' : ''}>Đã từ chối</option>
                        </select>
                    </div>
                    <div>
                        <label>Ngày tạo:</label>
                        <input type="date" name="createdDate" value="${createdDate}" class="form-control"/>
                    </div>
                    <div>
                        <button type="submit" class="btn btn-primary">Lọc</button>
                    </div>
                </form>

                <!-- Danh sách khiếu nại -->
                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">📋 Quản lý khiếu nại</h4>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-striped table-bordered text-center align-middle">
                                        <thead class="table-light">
                                            <tr>
                                                <th>ID Khiếu nại</th>
                                                <th>ID Chấm công</th>
                                                <th>Nhân viên</th>
                                                <th>Lý do</th>
                                                <th>Trạng thái</th>
                                                <th>Bình luận quản lý</th>
                                                <th>Ngày tạo</th>
                                                <th>Ngày giải quyết</th>
                                                <th>Loại vấn đề</th>
                                                <th>Đính kèm</th>
                                                <th>Lịch sử</th>
                                                <th>Ngày cập nhật</th>
                                                <th>Cập nhật bởi</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="dispute" items="${disputeList}" varStatus="stt">
                                                <tr>
                                                    <td>${dispute.disputeId}</td>
                                                    <td>${dispute.attendanceId.attendanceId}</td>
                                                    <td>
                                                        <c:forEach var="emp" items="${employeeList}">
                                                            <c:if test="${emp.userId eq dispute.userId}">
                                                                ${emp.fullName}
                                                            </c:if>
                                                        </c:forEach>
                                                    </td>
                                                    <td>${dispute.reason}</td>
                                                    <td>
                                                        <span class="badge ${dispute.status == 'pending' ? 'badge-warning' : 
                                                                       dispute.status == 'approved' ? 'badge-success' : 'badge-danger'}">
                                                            ${dispute.status}
                                                        </span>
                                                    </td>
                                                    <td>${dispute.managerComment}</td>
                                                    <td><fmt:formatDate value="${dispute.createdAt}" pattern="dd/MM/yyyy HH:mm"/></td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty dispute.resolvedAt}">
                                                                <fmt:formatDate value="${dispute.resolvedAt}" pattern="dd/MM/yyyy HH:mm"/>
                                                            </c:when>
                                                            <c:otherwise><i>--</i></c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>${dispute.issueType}</td>
                                                    <td>
                                                        <c:if test="${not empty dispute.attachmentPath}">
                                                            <a href="${dispute.attachmentPath}" target="_blank">Xem tệp</a>
                                                        </c:if>
                                                        <c:if test="${empty dispute.attachmentPath}"><i>--</i></c:if>
                                                    </td>
                                                    <td>${dispute.history}</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty dispute.updatedAt}">
                                                                <fmt:formatDate value="${dispute.updatedAt}" pattern="dd/MM/yyyy HH:mm"/>
                                                            </c:when>
                                                            <c:otherwise><i>--</i></c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty dispute.lastUpdatedBy}">
                                                                <c:forEach var="emp" items="${employeeList}">
                                                                    <c:if test="${emp.userId eq dispute.lastUpdatedBy}">
                                                                        ${emp.fullName}
                                                                    </c:if>
                                                                </c:forEach>
                                                            </c:when>
                                                            <c:otherwise><i>--</i></c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            <c:if test="${empty disputeList}">
                                                <tr>
                                                    <td colspan="13" class="text-center text-muted">Không có khiếu nại nào</td>
                                                </tr>
                                            </c:if>
                                        </tbody>
                                    </table>
                                </div>

                                <!-- Phân trang -->
                                <nav class="mt-3 mb-3">
                                    <ul class="pagination justify-content-center">
                                        <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                            <a class="page-link" href="?search=${search}&status=${status}&createdDate=${createdDate}&page=${currentPage - 1}">«</a>
                                        </li>
                                        <c:forEach begin="1" end="${totalPages}" var="i">
                                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                                <a class="page-link" href="?search=${search}&status=${status}&createdDate=${createdDate}&page=${i}">${i}</a>
                                            </li>
                                        </c:forEach>
                                        <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                            <a class="page-link" href="?search=${search}&status=${status}&createdDate=${createdDate}&page=${currentPage + 1}">»</a>
                                        </li>
                                    </ul>
                                    <div class="text-end text-muted">
                                        Tổng số bản ghi: <strong>${totalRecords}</strong>
                                    </div>
                                </nav>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Import footer -->
        <c:import url="/view/compomnt/footer.jsp" />
    </div>
</body>
</html>