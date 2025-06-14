<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <title>Danh sách chấm công</title>
        <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
        <style>
            .filter-form {
                background: #fff;
                border-radius: 10px;
                margin-bottom: 16px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.03);
                padding: 18px 24px 0 24px;
                display: flex;
                flex-wrap: wrap;
                gap: 30px 18px;
                align-items: end;
            }
            .filter-form label {
                font-weight: 600;
                color: #4b5675;
                margin-bottom: 5px;
            }
            .filter-form select, .filter-form input[type="date"] {
                min-width: 160px;
                border-radius: 6px;
                border: 1px solid #ddd;
                padding: 7px 12px;
            }
            .filter-form .btn {
                border-radius: 6px;
            }
        </style>
    </head>
    <body>
        <div id="main-wrapper" data-theme="light" data-layout="vertical" data-navbarbg="skin6"
             data-sidebartype="full" data-sidebar-position="fixed" data-header-position="fixed" data-boxed-layout="full">

            <c:import url="/view/compomnt/header.jsp" />
            <c:import url="/view/compomnt/siderbar.jsp" />

            <div class="page-wrapper">
                <div class="container-fluid">
                    <c:import url="/view/compomnt/notification.jsp" />

                    <h3 class="mb-4 text-primary fw-bold fs-3">📊 Danh sách chấm công nhân viên</h3>

                    <!-- FILTER FORM -->
                    <form method="get" class="filter-form" action="${pageContext.request.contextPath}/manager/attendance-list">
                        <div>
                            <label>Nhân viên:</label>
                            <select name="userId" class="form-select">
                                <option value="">-- Tất cả --</option>
                                <c:forEach var="emp" items="${employeeList}">
                                    <option value="${emp.userId}" <c:if test="${selectedUserId eq emp.userId}">selected</c:if>>
                                        ${emp.fullName}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div>
                            <label>Trạng thái:</label>
                            <select name="status" class="form-select">
                                <option value="">-- Tất cả --</option>
                                <option value="present" ${selectedStatus eq 'present' ? 'selected' : ''}>Đủ công</option>
                                <option value="absent" ${selectedStatus eq 'absent' ? 'selected' : ''}>Vắng</option>
                                <option value="partial" ${selectedStatus eq 'partial' ? 'selected' : ''}>Thiếu</option>
                            </select>
                        </div>
                        <div>
                            <label>Ngày:</label>
                            <input type="date" name="date" value="${selectedDate}" class="form-control"/>
                        </div>
                        <div>
                            <button type="submit" class="btn btn-primary px-3">Lọc</button>
                        </div>
                    </form>

                    <!-- BẢNG -->
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

                    <!-- PAGINATION -->
                    <nav class="mt-3 mb-3">
                        <ul class="pagination justify-content-center">
                            <li class="page-item <c:if test='${currentPage == 1}'>disabled</c:if>">
                                <a class="page-link" href="?userId=${selectedUserId}&status=${selectedStatus}&date=${selectedDate}&page=${currentPage - 1}">&laquo;</a>
                            </li>
                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <li class="page-item <c:if test='${i == currentPage}'>active</c:if>">
                                    <a class="page-link" href="?userId=${selectedUserId}&status=${selectedStatus}&date=${selectedDate}&page=${i}">${i}</a>
                                </li>
                            </c:forEach>
                            <li class="page-item <c:if test='${currentPage == totalPages}'>disabled</c:if>">
                                <a class="page-link" href="?userId=${selectedUserId}&status=${selectedStatus}&date=${selectedDate}&page=${currentPage + 1}">&raquo;</a>
                            </li>
                        </ul>
                        <div class="text-end text-muted">
                            Tổng số bản ghi: <strong>${totalRecords}</strong>
                        </div>
                    </nav>
                </div>
            </div>
            <c:import url="/view/compomnt/footer.jsp" />
        </div>
    </body>
</html>
