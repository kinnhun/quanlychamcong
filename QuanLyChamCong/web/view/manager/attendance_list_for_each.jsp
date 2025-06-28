<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Danh sách chấm công từng người</title>
    <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
    <style>
        .filter-form {
            background: #fff;
            border-radius: 10px;
            margin-bottom: 20px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
            padding: 20px;
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
            align-items: flex-end;
        }
        .filter-form label {
            font-weight: 600;
            color: #495057;
            margin-bottom: 6px;
        }
        .filter-form select {
            min-width: 180px;
            border-radius: 6px;
            border: 1px solid #ddd;
            padding: 8px;
        }
        .report-card {
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
            padding: 20px;
            margin-bottom: 20px;
            text-align: center;
        }
        .report-card .report-item {
            display: inline-block;
            margin: 0 15px;
            padding: 10px 20px;
            background: #f8f9fa;
            border-radius: 6px;
        }
        .table-responsive {
            margin-bottom: 20px;
        }
        @media (max-width: 768px) {
            .filter-form {
                flex-direction: column;
                align-items: stretch;
            }
            .report-card .report-item {
                display: block;
                margin: 10px 0;
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

                <!-- Tiêu đề và nội dung -->
                <h3 class="mb-4 text-primary fw-bold fs-3">📊 Danh sách chấm công từng người</h3>

                <!-- FILTER FORM -->
                <form method="get" class="filter-form" action="${pageContext.request.contextPath}/manager/attendance-each-employee">
                    <div>
                        <label>Nhân viên:</label>
                        <select name="userId" class="form-select" onchange="this.form.submit()">
                            <option value="">-- Chọn nhân viên --</option>
                            <c:forEach var="emp" items="${employeeList}">
                                <option value="${emp.userId}" <c:if test="${selectedUserId eq emp.userId}">selected</c:if>>
                                    ${emp.fullName}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div>
                        <input type="hidden" name="page" value="1" />
                    </div>
                </form>

                <!-- BÁO CÁO TỔNG SỐ -->
                <c:if test="${not empty selectedUserId}">
                    <div class="report-card">
                        <h4>Báo cáo tổng số</h4>
                        <div class="report-item">
                            <strong>Ngày làm:</strong> ${totalWorkingDays}
                        </div>
                        <div class="report-item">
                            <strong>Ngày vắng:</strong> ${totalAbsentDays}
                        </div>
                        <div class="report-item">
                            <strong>Ngày đi muộn:</strong> ${totalLateDays}
                        </div>
                    </div>
                </c:if>

                <!-- BẢNG CHẤM CÔNG CÁ NHÂN -->
                <c:if test="${not empty attendanceList}">
                    <div class="table-responsive shadow-sm bg-white p-4 rounded">
                        <table class="table table-hover table-bordered text-center align-middle rounded">
                            <thead class="table-light">
                                <tr class="fw-bold text-secondary">
                                    <th>#</th>
                                    <th>Nhân viên</th>
                                    <th>Ngày</th>
                                    <th>Giờ vào</th>
                                    <th>Ảnh vào</th>
                                    <th>Giờ ra</th>
                                    <th>Ảnh ra</th>
                                    <th>Địa điểm</th>
                                    <th>Trạng thái</th>
                                    <th>Khóa</th>
                                    <th>Ngày ghi nhận</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="a" items="${attendanceList}" varStatus="stt">
                                    <tr>
                                        <td>${(currentPage - 1) * pageSize + stt.index + 1}</td>
                                        <td class="text-start fw-semibold">${a.user.fullName}</td>
                                        <td><fmt:formatDate value="${a.date}" pattern="dd/MM/yyyy"/></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${a.checkinTime != null}">
                                                    <fmt:formatDate value="${a.checkinTime}" pattern="HH:mm:ss"/>
                                                </c:when>
                                                <c:otherwise><i>--</i></c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty a.checkinImageUrl}">
                                                    <a href="${a.checkinImageUrl}" target="_blank">
                                                        <img src="${a.checkinImageUrl}" style="height:32px;border-radius:4px;">
                                                    </a>
                                                </c:when>
                                                <c:otherwise><i>--</i></c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${a.checkoutTime != null}">
                                                    <fmt:formatDate value="${a.checkoutTime}" pattern="HH:mm:ss"/>
                                                </c:when>
                                                <c:otherwise><i>--</i></c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty a.checkoutImageUrl}">
                                                    <a href="${a.checkoutImageUrl}" target="_blank">
                                                        <img src="${a.checkoutImageUrl}" style="height:32px;border-radius:4px;">
                                                    </a>
                                                </c:when>
                                                <c:otherwise><i>--</i></c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${a.location != null}">
                                                    ${a.location.name}
                                                </c:when>
                                                <c:otherwise><i>--</i></c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${a.checkinTime != null && a.checkoutTime != null}">
                                                    <span class="badge-present">Đủ công</span>
                                                </c:when>
                                                <c:when test="${(a.checkinTime != null && a.checkoutTime == null) || (a.checkinTime == null && a.checkoutTime != null)}">
                                                    <span class="badge-partial">Thiếu</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge-absent">Vắng</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${a.isLocked}">
                                                    <span class="badge bg-secondary">Đã khóa</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-light text-muted">Chưa khóa</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${a.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty attendanceList}">
                                    <tr>
                                        <td colspan="11" class="text-center text-muted">Không có dữ liệu chấm công nào</td>
                                    </tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>

                    <!-- PAGINATION -->
                    <nav class="mt-3 mb-3">
                        <ul class="pagination justify-content-center">
                            <li class="page-item <c:if test='${currentPage == 1}'>disabled</c:if>">
                                <a class="page-link" href="?userId=${selectedUserId}&page=${currentPage - 1}">«</a>
                            </li>
                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <li class="page-item <c:if test='${i == currentPage}'>active</c:if>">
                                    <a class="page-link" href="?userId=${selectedUserId}&page=${i}">${i}</a>
                                </li>
                            </c:forEach>
                            <li class="page-item <c:if test='${currentPage == totalPages}'>disabled</c:if>">
                                <a class="page-link" href="?userId=${selectedUserId}&page=${currentPage + 1}">»</a>
                            </li>
                        </ul>
                        <div class="text-end text-muted">
                            Tổng số bản ghi: <strong>${totalRecords}</strong>
                        </div>
                    </nav>
                </c:if>

              
            </div>
        </div>
    </div>

    <c:import url="/view/compomnt/footer.jsp"/>
    <style>
        .badge-present {
            background: #e4f8e8;
            color: #357a38;
            border-radius: 20px;
            font-weight: 600;
            padding: 7px 18px;
            font-size: 14px;
        }
        .badge-partial {
            background: #fffbe5;
            color: #b79820;
            border-radius: 20px;
            font-weight: 600;
            padding: 7px 18px;
            font-size: 14px;
        }
        .badge-absent {
            background: #fde6e6;
            color: #c0392b;
            border-radius: 20px;
            font-weight: 600;
            padding: 7px 18px;
            font-size: 14px;
        }
    </style>
</body>
</html>