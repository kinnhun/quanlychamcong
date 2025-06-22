<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="model.Attendance" %>
<%@ page import="model.Locations" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <title>Danh sách chấm công</title>
    <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
    <style>
        body {
            background: #e8eef5;
        }
        .attendance-list-card {
            max-width: 100%;
            margin: 42px auto 40px auto;
            background: linear-gradient(120deg, #f8fbff 70%, #e5eeff 100%);
            border-radius: 20px;
            box-shadow: 0 8px 36px 0 rgba(41,127,255,0.12), 0 1.5px 12px 0 rgba(40,70,130,0.08);
            padding: 40px 32px 28px 32px;
        }
        .attendance-list-card h2 {
            margin-bottom: 28px;
            text-align: center;
            color: #297fff;
            font-size: 1.7rem;
            letter-spacing: 1px;
        }
        .filter-form {
            display: flex;
            flex-wrap: wrap;
            align-items: center;
            gap: 18px;
            margin-bottom: 18px;
            justify-content: center;
        }
        .filter-form label {
            font-weight: 600;
            color: #204887;
            margin-right: 7px;
        }
        .filter-form select,
        .filter-form input[type="date"] {
            padding: 7px 12px;
            border-radius: 7px;
            border: 1px solid #b6cae0;
            background: #f9fbff;
            font-size: 1.02rem;
        }
        .filter-form button {
            background: linear-gradient(90deg, #297fff 80%, #203e78 100%);
            color: #fff;
            font-weight: 600;
            border: none;
            border-radius: 7px;
            padding: 8px 20px;
            cursor: pointer;
        }
        .filter-form button:hover {
            background: linear-gradient(90deg, #174eb5 70%, #142d51 100%);
        }
        .table-responsive {
            margin-bottom: 10px;
        }
        .table {
            background: #fff;
            border-radius: 10px;
            overflow: hidden;
            width: 100%;
        }
        .table th, .table td {
            vertical-align: middle !important;
            text-align: center;
        }
        .table th {
            background: #e6f0ff;
            color: #174eb5;
            font-weight: 600;
            font-size: 1.08rem;
        }
        .table-img {
            width: 54px;
            height: 54px;
            object-fit: cover;
            border-radius: 8px;
            border: 1px solid #dde9f7;
            box-shadow: 0 2px 8px rgba(41,127,255,0.07);
        }
        .status-locked {
            color: #d63447;
            font-weight: 700;
        }
        .status-unlocked {
            color: #29904d;
            font-weight: 700;
        }
        .empty-msg {
            color: #297fff;
            text-align: center;
            padding: 40px 0 20px 0;
            font-size: 1.1rem;
        }
        .pagination {
            display: flex;
            justify-content: center;
            margin-top: 18px;
        }
        .pagination a, .pagination span {
            display: block;
            padding: 7px 14px;
            margin: 0 2px;
            border-radius: 7px;
            text-decoration: none;
            color: #297fff;
            background: #f4f8ff;
            border: 1px solid #b6cae0;
            font-weight: 600;
            transition: all 0.13s;
        }
        .pagination .active {
            background: #297fff;
            color: #fff;
            border-color: #297fff;
            pointer-events: none;
        }
        .pagination .disabled {
            color: #aaa;
            border-color: #eee;
            pointer-events: none;
        }
        @media (max-width: 900px) {
            .attendance-list-card { padding: 14px 2px 10px 2px; }
            .filter-form { gap:8px; }
            .table th, .table td { font-size: 13px;}
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

            <div class="attendance-list-card">
                <h2>Danh sách chấm công</h2>

                <!-- Bộ lọc & tìm kiếm -->
                <form class="filter-form" method="get" action="${pageContext.request.contextPath}/employee/attendance">
                    <label for="status">Trạng thái</label>
                    <select name="status" id="status">
                        <option value="" ${empty param.status ? "selected" : ""}>Tất cả</option>
                        <option value="locked" ${param.status == "locked" ? "selected" : ""}>Đã khóa</option>
                        <option value="unlocked" ${param.status == "unlocked" ? "selected" : ""}>Chưa khóa</option>
                    </select>

                    <label for="date">Ngày</label>
                    <input type="date" id="date" name="date" value="${param.date}"/>

                    <label for="locationId">Địa điểm</label>
                    <select name="locationId" id="locationId">
                        <option value="" ${empty param.locationId ? "selected" : ""}>Tất cả</option>
                        <c:forEach var="loc" items="${locationList}">
                            <option value="${loc.id}" ${param.locationId == (loc.id).toString() ? "selected" : ""}>${loc.name}</option>
                        </c:forEach>
                    </select>

                    <button type="submit">Lọc</button>
                </form>

                <c:if test="${empty attendanceList}">
                    <div class="empty-msg">Không có dữ liệu chấm công.</div>
                </c:if>
                <c:if test="${not empty attendanceList}">
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover align-middle">
                            <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Ngày</th>
                                    <th>Giờ vào</th>
                                    <th>Giờ ra</th>
                                    <th>Địa điểm</th>
                                    <th>Ảnh checkin</th>
                                    <th>Ảnh checkout</th>
                                    <th>Trạng thái</th>
                                    <th>Ngày tạo</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="a" items="${attendanceList}" varStatus="status">
                                    <tr>
                                        <td>${status.index + 1 + (page-1)*pageSize}</td>
                                        <td>
                                            <fmt:formatDate value="${a.date}" pattern="dd/MM/yyyy"/>
                                        </td>
                                        <td>
                                            <c:if test="${not empty a.checkinTime}">
                                                <fmt:formatDate value="${a.checkinTime}" pattern="HH:mm:ss"/>
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:if test="${not empty a.checkoutTime}">
                                                <fmt:formatDate value="${a.checkoutTime}" pattern="HH:mm:ss"/>
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty a.location && not empty a.location.name}">
                                                    ${a.location.name}
                                                </c:when>
                                                <c:otherwise>--</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:if test="${not empty a.checkinImageUrl}">
                                                <img class="table-img" src="${a.checkinImageUrl}" alt="Checkin"/>
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:if test="${not empty a.checkoutImageUrl}">
                                                <img class="table-img" src="${a.checkoutImageUrl}" alt="Checkout"/>
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${a.isLocked}">
                                                    <span class="status-locked">Đã khóa</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="status-unlocked">Chưa khóa</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:if test="${not empty a.createdAt}">
                                                <fmt:formatDate value="${a.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <!-- PHÂN TRANG -->
                    <div class="pagination">
                        <c:choose>
                            <c:when test="${page > 1}">
                                <a href="?status=${param.status}&date=${param.date}&locationId=${param.locationId}&page=${page-1}">&laquo; Trước</a>
                            </c:when>
                            <c:otherwise>
                                <span class="disabled">&laquo; Trước</span>
                            </c:otherwise>
                        </c:choose>

                        <c:forEach var="i" begin="1" end="${totalPage}">
                            <c:choose>
                                <c:when test="${i == page}">
                                    <span class="active">${i}</span>
                                </c:when>
                                <c:otherwise>
                                    <a href="?status=${param.status}&date=${param.date}&locationId=${param.locationId}&page=${i}">${i}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>

                        <c:choose>
                            <c:when test="${page < totalPage}">
                                <a href="?status=${param.status}&date=${param.date}&locationId=${param.locationId}&page=${page+1}">Sau &raquo;</a>
                            </c:when>
                            <c:otherwise>
                                <span class="disabled">Sau &raquo;</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>

<!-- Import footer -->
<c:import url="/view/compomnt/footer.jsp" />

</body>
</html>
