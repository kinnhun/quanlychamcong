<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>➕ Gán phòng ban</title>
    <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
    <style>
        .department-card {
            border: 2px solid #e9ecef;
            border-radius: 8px;
            padding: 15px;
            margin-bottom: 15px;
            transition: all 0.3s ease;
            cursor: pointer;
            background-color: #fff;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
        }

        .department-card:hover {
            border-color: #007bff;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }

        .department-card.selected {
            background-color: #e6f3e6;
            border-color: #28a745;
        }

        .department-card .form-check-input {
            display: none;
        }

        .department-card .form-check-label {
            width: 100%;
            margin: 0;
            cursor: pointer;
        }

        .department-name {
            font-size: 1.1rem;
            font-weight: 500;
            color: #333;
        }

        .department-code {
            font-size: 0.9rem;
            color: #6c757d;
        }

        .department-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 15px;
        }
    </style>
</head>
<body>
<div id="main-wrapper" data-theme="light" data-layout="vertical" data-navbarbg="skin6"
     data-sidebartype="full" data-sidebar-position="fixed" data-header-position="fixed"
     data-boxed-layout="full">

    <!-- Import header -->
    <c:import url="/view/compomnt/header.jsp" />

    <!-- Import sidebar -->
    <c:import url="/view/compomnt/siderbar.jsp" />

    <div class="page-wrapper">
        <div class="container-fluid">

            <!-- Notification -->
            <c:import url="/view/compomnt/notification.jsp" />

            <!-- Tiêu đề + Quay lại -->
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h3 class="text-primary fw-bold fs-3 mb-0">➕ Gán phòng ban cho địa điểm: ${location.name}</h3>
                <a href="${pageContext.request.contextPath}/admin/locations-department?locationId=${location.id}"
                   class="btn btn-outline-secondary">⬅️ Quay lại</a>
            </div>

            <!-- Form -->
            <form action="${pageContext.request.contextPath}/admin/assign-department" method="post"
                  class="bg-white border rounded p-4 shadow-sm" style="width: 100%;">
                <input type="hidden" name="locationId" value="${location.id}" />

                <div class="mb-4">
                    <label class="form-label fw-bold mb-3">Chọn phòng ban muốn gán:</label>
                    <div class="department-grid">
                        <c:forEach var="d" items="${departments}">
                            <div class="department-card <c:if test="${assignedIds.contains(d.departmentId)}">selected</c:if>">
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox"
                                           name="departmentIds"
                                           value="${d.departmentId}"
                                           id="dep-${d.departmentId}"
                                           <c:if test="${assignedIds.contains(d.departmentId)}">checked</c:if> />
                                    <label class="form-check-label" for="dep-${d.departmentId}">
                                        <div class="department-name">${d.departmentName}</div>
                                        <div class="department-code">(${d.departmentCode})</div>
                                    </label>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <button type="submit" class="btn btn-success">💾 Lưu gán phòng ban</button>
                <a href="${pageContext.request.contextPath}/admin/locations-department?locationId=${location.id}"
                   class="btn btn-secondary ms-2">Hủy</a>
            </form>

        </div>
    </div>

    <!-- Import footer -->
    <c:import url="/view/compomnt/footer.jsp" />
</div>

<script>
    document.querySelectorAll('.department-card').forEach(card => {
        card.addEventListener('click', () => {
            const checkbox = card.querySelector('.form-check-input');
            checkbox.checked = !checkbox.checked;
            card.classList.toggle('selected');
        });
    });
</script>
</body>
</html>