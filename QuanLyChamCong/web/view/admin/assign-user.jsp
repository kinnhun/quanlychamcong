<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Phân công nhân viên cho chi nhánh</title>
        <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
    </head>
    <body>
        <div id="main-wrapper" data-theme="light" data-layout="vertical" data-navbarbg="skin6"
             data-sidebartype="full" data-sidebar-position="fixed" data-header-position="fixed" data-boxed-layout="full">

            <c:import url="/view/compomnt/header.jsp"/>
            <c:import url="/view/compomnt/siderbar.jsp"/>

            <div class="page-wrapper">
                <div class="container-fluid">

                    <c:import url="/view/compomnt/notification.jsp"/>

                    <!-- 🔍 Bộ lọc tìm kiếm -->
                    <div class="card">
                        <div class="card-body">
                            <h4 class="card-title">Bộ lọc</h4>
                            <form class="row" method="get" action="">
                                <div class="form-group col-md-3">
                                    <label>Chi nhánh</label>
                                    <select name="locationId" class="form-control">
                                        <option value="">Tất cả</option>
                                        <c:forEach var="l" items="${locationList}">
                                            <option value="${l.id}" <c:if test="${selectedLocation == l.id}">selected</c:if>>${l.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group col-md-2">
                                    <label>Vai trò</label>
                                    <select name="role" class="form-control">
                                        <option value="">Tất cả</option>
                                        <option value="admin" <c:if test="${selectedRole == 'admin'}">selected</c:if>>Admin</option>
                                        <option value="manager" <c:if test="${selectedRole == 'manager'}">selected</c:if>>Manager</option>
                                        <option value="employee" <c:if test="${selectedRole == 'employee'}">selected</c:if>>Employee</option>
                                        </select>
                                    </div>
                                    <div class="form-group col-md-2">
                                        <label>Trạng thái</label>
                                        <select name="status" class="form-control">
                                            <option value="">Tất cả</option>
                                            <option value="active" <c:if test="${selectedStatus == 'active'}">selected</c:if>>Đang hoạt động</option>
                                        <option value="banned" <c:if test="${selectedStatus == 'banned'}">selected</c:if>>Bị khóa</option>
                                        </select>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label>Từ khóa</label>
                                        <input type="text" name="keyword" value="${keyword}" class="form-control" placeholder="Tên, email, SĐT...">
                                </div>
                                <div class="form-group col-md-2 align-self-end">
                                    <button class="btn btn-primary btn-block">🔍 Tìm</button>
                                </div>
                            </form>
                        </div>
                    </div>
                                
                                
               <!-- ➕ Form phân công mới -->
<div class="card">
    <div class="card-body">
        <h4 class="card-title">Thêm phân công mới</h4>
        <form method="post" action="${pageContext.request.contextPath}/admin/assign-user-location">
            <div class="row">
                <div class="form-group col-md-6">
                    <label>Nhân viên</label>
                    <select name="userId" class="form-control" required>
                        <c:forEach var="u" items="${userList}">
                            <option value="${u.userId}">${u.fullName} (${u.username})</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group col-md-6">
                    <label>Chi nhánh</label>
                    <select name="locationId" class="form-control" required>
                        <c:forEach var="l" items="${locationList}">
                            <option value="${l.id}">${l.name} - ${l.address}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <button type="submit" class="btn btn-primary">Phân công</button>
        </form>
    </div>
</div>


                    <!-- 📋 Danh sách phân công -->
                    <div class="card">
                        <div class="card-body">
                            <h4 class="card-title">Danh sách phân công</h4>
                            <div class="table-responsive">
                                <table  id="assignmentTable"  class="table table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th>Nhân viên</th>
                                            <th>Email</th>
                                            <th>Chi nhánh</th>
                                            <th>Địa chỉ</th>
                                            <th>Ngày phân công</th>
                                            <th>Hành động</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="row" items="${assignmentList}">
                                            <tr>
                                                <td>${row[0]}</td>
                                                <td>${row[1]}</td>
                                                <td>${row[2]}</td>
                                                <td>${row[3]}</td>
                                                <td><fmt:formatDate value="${row[4]}" pattern="dd/MM/yyyy HH:mm"/></td>
                                                <td>
                                                    <form action="${pageContext.request.contextPath}/admin/delete-assignment" method="post" style="display:inline;">
    <input type="hidden" name="userId" value="${row[5]}" />
    <input type="hidden" name="locationId" value="${row[6]}" />
    <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('Bạn có chắc muốn xóa phân công này?');">Xóa</button>
</form>

                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <!-- Phân trang -->
                                <nav>
                                    <ul id="pagination" class="pagination justify-content-center mt-3"></ul>
                                </nav>
                                <script>
                                    document.addEventListener("DOMContentLoaded", function () {
                                        const rowsPerPage = 5;
                                        const table = document.getElementById("assignmentTable");
                                        const tbody = table.querySelector("tbody");
                                        const rows = Array.from(tbody.querySelectorAll("tr"));
                                        const pagination = document.getElementById("pagination");

                                        let currentPage = 1;
                                        const totalPages = Math.ceil(rows.length / rowsPerPage);

                                        function renderTablePage(page) {
                                            tbody.innerHTML = "";
                                            const start = (page - 1) * rowsPerPage;
                                            const end = start + rowsPerPage;
                                            const pageRows = rows.slice(start, end);
                                            pageRows.forEach(row => tbody.appendChild(row));
                                        }

                                        function renderPagination() {
                                            pagination.innerHTML = "";
                                            for (let i = 1; i <= totalPages; i++) {
                                                const li = document.createElement("li");
                                                li.className = "page-item" + (i === currentPage ? " active" : "");
                                                const btn = document.createElement("button");
                                                btn.className = "page-link";
                                                btn.innerText = i;
                                                btn.addEventListener("click", () => {
                                                    currentPage = i;
                                                    renderTablePage(currentPage);
                                                    renderPagination();
                                                });
                                                li.appendChild(btn);
                                                pagination.appendChild(li);
                                            }
                                        }

                                        if (rows.length > 0) {
                                            renderTablePage(currentPage);
                                            renderPagination();
                                        }
                                    });
                                </script>


                            </div>
                        </div>
                    </div>

                  
                </div>
            </div>

            <c:import url="/view/compomnt/footer.jsp"/>
        </div>
    </body>
</html>
