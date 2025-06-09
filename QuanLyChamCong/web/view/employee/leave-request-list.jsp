<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Danh sách đơn xin nghỉ phép của bạn</title>
        <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
        <style>
            /* Filter container */
            .filter-container {
                display: flex;
                flex-wrap: wrap;
                gap: 20px;
                align-items: center;
                margin-bottom: 20px;
                padding: 15px 20px;
                background-color: #f9fafb;
                border: 1px solid #e3e6ea;
                border-radius: 10px;
                box-shadow: 0 1px 4px rgba(0,0,0,0.04);
            }

            .filter-container label {
                font-weight: 600;
                color: #495057;
                margin-bottom: 6px;
                display: block;
            }

            .filter-container select {
                min-width: 200px;
                padding: 8px 12px;
                border-radius: 8px;
                border: 1px solid #ced4da;
                background-color: #ffffff;
                font-size: 14px;
                color: #495057;
                box-shadow: inset 0 1px 2px rgba(0,0,0,0.03);
                transition: border-color 0.2s, box-shadow 0.2s;
            }

            .filter-container select:focus {
                border-color: #6c63ff;
                box-shadow: 0 0 0 0.15rem rgba(108, 99, 255, 0.2);
                outline: none;
            }

            /* Table */
            table#multi_col_order {
                border-radius: 10px;
                overflow: hidden;
                box-shadow: 0 2px 8px rgba(0,0,0,0.03);
            }

            table#multi_col_order thead {
                background-color: #f1f3f5;
                color: #333;
            }

            table#multi_col_order thead th {
                font-size: 15px;
                padding: 14px 12px;
                font-weight: 700;
            }

            table#multi_col_order tbody td {
                font-size: 14px;
                padding: 12px 10px;
                color: #333;
                vertical-align: middle;
            }

            table#multi_col_order tbody tr {
                transition: background-color 0.25s ease;
                cursor: pointer;
            }

            table#multi_col_order tbody tr:hover {
                background-color: #f9fcff;
            }

            /* Badge tinh tế */
            .badge {
                border-radius: 30px !important;
                font-size: 13px;
                padding: 6px 12px;
                box-shadow: 0 1px 3px rgba(0,0,0,0.06);
            }

            .bg-success {
                background-color: #d1e7dd !important;
                color: #0f5132 !important;
            }

            .bg-warning {
                background-color: #fff3cd !important;
                color: #664d03 !important;
            }

            .bg-danger {
                background-color: #f8d7da !important;
                color: #842029 !important;
            }

            .bg-info {
                background-color: #cff4fc !important;
                color: #055160 !important;
            }

            .bg-secondary {
                background-color: #e2e3e5 !important;
                color: #41464b !important;
            }

            .text-small {
                font-size: 0.9rem;
                color: #555;
            }
        </style>
    </head>
    <body>

        <div id="main-wrapper" data-theme="light" data-layout="vertical" data-navbarbg="skin6" data-sidebartype="full"
             data-sidebar-position="fixed" data-header-position="fixed" data-boxed-layout="full">

            <!-- Import header -->
            <c:import url="/view/compomnt/header.jsp"/>

            <!-- Import sidebar -->
            <c:import url="/view/compomnt/siderbar.jsp"/>

            <div class="page-wrapper">
                <div class="container-fluid" style="background-color: #ffffff">

                    <!-- Thông báo -->
                    <c:import url="/view/compomnt/notification.jsp"/>

                    <h3 class="mb-4 text-primary fw-bold fs-3">📋 Danh sách đơn xin nghỉ phép của bạn</h3>

                    <!-- Filter -->
                    <div class="filter-container mb-3">
                        <div>
                            <label for="filterStatus" class="form-label fw-semibold text-secondary">📌 Trạng thái:</label>
                            <select id="filterStatus" class="form-select">
                                <option value="">-- Tất cả --</option>
                                <option value="Đã duyệt">Đã duyệt</option>
                                <option value="Đang chờ">Đang chờ</option>
                                <option value="Từ chối">Từ chối</option>
                                <option value="Đã hủy">Đã hủy</option>
                            </select>
                        </div>
                    </div>

                    <!-- Table -->
                    <table id="multi_col_order" class="table table-hover table-bordered text-center align-middle shadow rounded">
                        <thead class="table-light">
                            <tr class="fw-bold text-secondary">
                                <th>#</th>
                                <th>Loại nghỉ</th>
                                <th>Thời gian</th>
                                <th>Số ngày</th>
                                <th>Trạng thái</th>
                                <th class="text-start">Lý do</th>
                                <th>Ngày tạo</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requests}" var="r" varStatus="loop">
                                <tr onclick="window.location = '${pageContext.request.contextPath}/employee/leave-request-detail?id=${r.requestId}'">
                                    <td>${loop.index + 1}</td>
                                    <td>
                                        <span class="badge bg-info bg-gradient text-dark">
                                            <c:out value="${r.leaveTypeId != null ? r.leaveTypeId.leaveTypeName : '---'}" />
                                        </span>
                                    </td>
                                    <td class="text-small">
                                        <fmt:formatDate value="${r.startDate}" pattern="dd/MM/yyyy" />
                                        -
                                        <fmt:formatDate value="${r.endDate}" pattern="dd/MM/yyyy" />
                                    </td>
                                    <td><strong>${r.daysCount}</strong></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${r.status == 'approved'}">
                                                <span class="badge bg-success">Đã duyệt</span>
                                            </c:when>
                                            <c:when test="${r.status == 'rejected'}">
                                                <span class="badge bg-danger">Từ chối</span>
                                            </c:when>
                                            <c:when test="${r.status == 'canceled'}">
                                                <span class="badge bg-secondary">Đã hủy</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-warning text-dark">Đang chờ</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-start text-small">${r.reason}</td>
                                    <td class="text-small">
                                        <fmt:formatDate value="${r.createdAt}" pattern="dd/MM/yyyy HH:mm" />
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- Import footer -->
        <c:import url="/view/compomnt/footer.jsp"/>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const filterStatus = document.getElementById("filterStatus");

                function normalize(str) {
                    return str.trim().toLowerCase();
                }

                function filterTable() {
                    const selectedStatus = normalize(filterStatus.value);

                    document.querySelectorAll("#multi_col_order tbody tr").forEach(row => {
                        const status = normalize(row.cells[4].textContent);

                        const matchStatus = !selectedStatus || status.includes(selectedStatus);

                        row.style.display = matchStatus ? "" : "none";
                    });
                }

                filterStatus.addEventListener("change", filterTable);
            });
        </script>

    </body>
</html>
