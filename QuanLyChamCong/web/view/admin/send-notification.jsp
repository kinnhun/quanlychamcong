<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Gửi Thông Báo</title>
        <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
        <style>
            .table-scroll {
                max-height: 400px;
                overflow-y: auto;
            }
            .form-label {
                font-weight: bold;
            }
            .filter-row .form-select, .filter-row .form-control {
                min-width: 150px;
                margin-right: 12px;
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

                    <div class="card mt-4">
                       <div class="card-header d-flex justify-content-between align-items-center">
    <h4>Gửi Thông Báo Đến Người Dùng</h4>
    <a href="${pageContext.request.contextPath}/admin/notification-list" class="btn btn-outline-success">
        <i class="fa fa-list"></i> Danh sách đã gửi
    </a>
</div>

                        <div class="card-body">
                            <!-- Filter form -->
                            <form method="get" action="${pageContext.request.contextPath}/admin/send-notification" class="mb-3">
                                <div class="row g-2 filter-row align-items-center">
                                    <div class="col-auto">
                                        <select class="form-select" name="locationId">
                                            <option value="">-- Địa điểm --</option>
                                            <c:forEach var="loc" items="${locationList}">
                                                <option value="${loc.id}" <c:if test="${param.locationId == loc.id}">selected</c:if>>
                                                    ${loc.name}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-auto">
                                        <select class="form-select" name="departmentId">
                                            <option value="">-- Phòng ban --</option>
                                            <c:forEach var="dep" items="${departmentList}">
                                                <option value="${dep.departmentId}" <c:if test="${param.departmentId == dep.departmentId}">selected</c:if>>
                                                    ${dep.departmentName}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-auto">
                                        <input type="text" class="form-control" name="keyword" placeholder="Tên hoặc email" value="${param.keyword}">
                                    </div>
                                    <div class="col-auto">
                                        <button type="submit" class="btn btn-outline-primary">Lọc</button>
                                        <a href="${pageContext.request.contextPath}/admin/send-notification" class="btn btn-outline-secondary">Xóa lọc</a>
                                    </div>
                                </div>
                            </form>
                            <!-- End filter form -->

                            <!-- Form gửi thông báo với upload file -->
                            <form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/admin/send-notification">
                                <div class="mb-3">
                                    <label class="form-label">Chọn người nhận</label>
                                    <div class="table-responsive table-scroll">
                                        <table class="table table-bordered table-hover align-middle">
                                            <thead>
                                                <tr>
                                                    <th style="width:40px;"><input type="checkbox" id="selectAll" onclick="toggleAll(this)"/></th>
                                                    <th>Tên</th>
                                                    <th>Email</th>
                                                    <th>Địa điểm</th>
                                                    <th>Phòng ban</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="ul" items="${userList}">
                                                    <tr>
                                                        <td>
                                                            <input type="checkbox" name="userIds" value="${ul.userId.userId}" />
                                                        </td>
                                                        <td>${ul.userId.fullName}</td>
                                                        <td>${ul.userId.email}</td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${ul.locationId != null}">
                                                                    ${ul.locationId.name}
                                                                </c:when>
                                                                <c:otherwise>
                                                                    (Chưa có)
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${ul.departmentId != null}">
                                                                    ${ul.departmentId.departmentName}
                                                                </c:when>
                                                                <c:otherwise>
                                                                    (Chưa có)
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="form-text">Tích chọn để gửi đến nhiều người (hoặc tất cả).</div>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Tiêu đề thông báo</label>
                                    <input type="text" class="form-control" name="title" required maxlength="255">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Nội dung</label>
                                    <textarea class="form-control" name="content" rows="5" required></textarea>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Ảnh (chọn file hoặc nhập URL)</label>
                                    <input type="file" class="form-control" name="imageFile">
                                    <input type="text" class="form-control mt-1" name="imageUrl" placeholder="Hoặc nhập URL nếu đã có">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">File đính kèm (chọn file hoặc nhập URL)</label>
                                    <input type="file" class="form-control" name="attachFile">
                                    <input type="text" class="form-control mt-1" name="fileUrl" placeholder="Hoặc nhập URL nếu đã có">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Thời gian gửi (nếu muốn hẹn giờ)</label>
                                    <input type="datetime-local" class="form-control" name="scheduledTime">
                                </div>
                                <button type="submit" class="btn btn-primary">
                                    <i class="fa fa-paper-plane"></i> Gửi thông báo
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <c:import url="/view/compomnt/footer.jsp" />
        <script>
            function toggleAll(master) {
                const checks = document.querySelectorAll('input[name="userIds"]');
                for (const cb of checks)
                    cb.checked = master.checked;
            }
        </script>
    </body>
</html>
