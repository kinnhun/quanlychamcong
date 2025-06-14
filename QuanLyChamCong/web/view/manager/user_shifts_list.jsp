<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Phân ca nhân viên</title>
    <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
    <style>
        .table-responsive {
            background: #fff;
            padding: 2rem;
            border-radius: 12px;
        }
        .badge {
            border-radius: 16px;
            padding: 6px 10px;
            font-size: 13px;
        }
        .bg-ca {
            background: #f0f9ff;
            color: #005d8c;
        }
        .bg-dept {
            background: #ffe6fa;
            color: #92007d;
        }
        .bg-loc {
            background: #f8fbe6;
            color: #607900;
        }
        /* ----------- FILTER FORM STYLE REWORK ----------- */
        .filter-form {
            background: #f6f8fa;
            border-radius: 12px;
            padding: 18px 16px 8px 16px;
            margin-bottom: 28px;
            box-shadow: 0 2px 8px 0 rgba(35, 70, 180, 0.07);
        }
        .filter-form .form-label {
            font-weight: 500;
            color: #2065d1;
            font-size: 15px;
            margin-bottom: 5px;
        }
        .filter-form .form-select, .filter-form .form-control {
            min-width: 140px;
            border-radius: 8px;
            font-size: 15px;
        }
        .filter-form .form-control[type="date"],
        .filter-form .form-control[type="week"],
        .filter-form .form-control[type="month"] {
            min-width: 120px;
        }
        .filter-form .btn {
            min-width: 90px;
            border-radius: 8px;
            font-weight: 600;
            font-size: 15px;
        }
        @media (max-width: 992px) {
            .filter-form .col-auto {
                flex: 1 1 100%;
                min-width: 180px;
                margin-bottom: 10px;
            }
            .filter-form {
                padding: 10px 8px 5px 8px;
            }
        }
    </style>
</head>
<body>
<div id="main-wrapper" data-theme="light" data-layout="vertical" data-navbarbg="skin6" data-sidebartype="full"
     data-sidebar-position="fixed" data-header-position="fixed" data-boxed-layout="full">

    <c:import url="/view/compomnt/header.jsp"/>
    <c:import url="/view/compomnt/siderbar.jsp"/>

    <div class="page-wrapper">
        <div class="container-fluid">
            <h3 class="mb-4 text-primary fw-bold fs-3">🗓️ Danh sách phân ca nhân viên</h3>
            <div class="mb-3 d-flex justify-content-end">
                <a href="${pageContext.request.contextPath}/manager/user-shifts-add" class="btn btn-success fw-bold">
                    ➕ Thêm phân ca
                </a>
            </div>

            <!-- FORM LỌC -->
            <form method="get" class="row g-2 align-items-end filter-form">
                <div class="col-auto">
                    <label class="form-label">Nhân viên</label>
                    <select name="employeeId" class="form-select">
                        <option value="">Tất cả</option>
                        <c:forEach var="e" items="${employeeList}">
                            <option value="${e.userId}" <c:if test="${filter.employeeId == e.userId}">selected</c:if>>
                                ${e.fullName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-auto">
                    <label class="form-label">Ca làm</label>
                    <select name="shiftId" class="form-select">
                        <option value="">Tất cả</option>
                        <c:forEach var="s" items="${shiftList}">
                            <option value="${s.shiftId}" <c:if test="${filter.shiftId == s.shiftId}">selected</c:if>>
                                ${s.shiftName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-auto">
                    <label class="form-label">Phòng ban</label>
                    <select name="departmentId" class="form-select">
                        <option value="">Tất cả</option>
                        <c:forEach var="d" items="${departmentList}">
                            <option value="${d.departmentId}" <c:if test="${filter.departmentId == d.departmentId}">selected</c:if>>
                                ${d.departmentName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-auto">
                    <label class="form-label">Ngày</label>
                    <input type="date" name="date" class="form-control" value="${filter.date != null ? filter.date : ''}" />
                </div>
                <div class="col-auto">
                    <label class="form-label">Tuần</label>
                    <input type="week" name="week" class="form-control" value="${filter.week != null ? filter.week : ''}" />
                </div>
                <div class="col-auto">
                    <label class="form-label">Tháng</label>
                    <input type="month" name="month" class="form-control" value="${filter.month != null ? filter.month : ''}" />
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-primary fw-bold">Lọc</button>
                </div>
            </form>

            <div class="table-responsive shadow-sm">
                <table class="table table-hover table-bordered text-center align-middle rounded">
                    <thead class="table-light">
                    <tr class="fw-bold text-secondary">
                        <th>#</th>
                        <th>Nhân viên</th>
                        <th>Ngày</th>
                        <th>Ca làm</th>
                        <th>Phòng ban</th>
                        <th>Ghi chú</th>
                        <th>Người phân</th>
                        <th>Lúc</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="us" items="${userShiftList}" varStatus="stt">
                        <tr>
                            <td>${(page-1)*pageSize + stt.index + 1}</td>
                            <td>
                                <span class="fw-semibold">${us.user.fullName}</span>
                                <br><span class="text-muted" style="font-size:12px;">${us.user.username}</span>
                            </td>
                            <td>
                                <fmt:formatDate value="${us.date}" pattern="dd/MM/yyyy"/>
                            </td>
                            <td>
                                <span class="badge bg-ca">${us.shift.shiftName}</span>
                                <br>
                                <span class="text-muted" style="font-size:12px;">
                                    <fmt:formatDate value="${us.shift.startTime}" pattern="HH:mm"/>
                                    -
                                    <fmt:formatDate value="${us.shift.endTime}" pattern="HH:mm"/>
                                </span>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty us.department}">
                                        <span class="badge bg-dept">${us.department.departmentName}</span>
                                    </c:when>
                                    <c:otherwise><i>--</i></c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty us.note}">
                                        ${us.note}
                                    </c:when>
                                    <c:otherwise><i>--</i></c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty us.assignedBy}">
                                        <span class="text-success">${us.assignedBy.fullName}</span>
                                    </c:when>
                                    <c:otherwise><i>--</i></c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <fmt:formatDate value="${us.assignedAt}" pattern="dd/MM/yyyy HH:mm"/>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty userShiftList}">
                        <tr>
                            <td colspan="8" class="text-center text-muted">Không có dữ liệu phân ca</td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </div>

            <!-- PHÂN TRANG -->
            <c:if test="${totalPages > 1}">
                <nav aria-label="Page navigation" class="mt-3">
                    <ul class="pagination justify-content-center">
                        <li class="page-item <c:if test='${page == 1}'>disabled</c:if>'">
                            <a class="page-link"
                               href="?page=${page-1}&pageSize=${pageSize}
                               <c:forEach var='entry' items='${filter}'>
                                   &${entry.key}=${entry.value}
                               </c:forEach>">«</a>
                        </li>
                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <li class="page-item <c:if test='${i == page}'>active</c:if>'">
                                <a class="page-link"
                                   href="?page=${i}&pageSize=${pageSize}
                                   <c:forEach var='entry' items='${filter}'>
                                       &${entry.key}=${entry.value}
                                   </c:forEach>">${i}</a>
                            </li>
                        </c:forEach>
                        <li class="page-item <c:if test='${page == totalPages}'>disabled</c:if>'">
                            <a class="page-link"
                               href="?page=${page+1}&pageSize=${pageSize}
                               <c:forEach var='entry' items='${filter}'>
                                   &${entry.key}=${entry.value}
                               </c:forEach>">»</a>
                        </li>
                    </ul>
                </nav>
            </c:if>
        </div>
    </div>
    <c:import url="/view/compomnt/footer.jsp"/>
</div>
</body>
</html>
