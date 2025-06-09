<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Quản lý loại nghỉ phép</title>
        <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
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

                    <!-- Notification -->
                    <c:import url="/view/compomnt/notification.jsp" />

                    <!-- Title + Add button -->
                    <div class="d-flex justify-content-between align-items-center mt-4 mb-4">
                        <h3 class="fw-bold text-primary mb-0">Quản lý loại nghỉ phép</h3>

                        <a href="${pageContext.request.contextPath}/manager/leave-type-add" class="btn btn-success">
                            + Thêm mới loại nghỉ phép
                        </a>
                    </div>

                    <!-- Bảng danh sách -->
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover bg-white">
                            <thead class="table-primary">
                                <tr>
                                    <th>#</th>
                                    <th>Tên loại nghỉ phép</th>
                                    <th>Trạng thái</th>
                                    <th>Thao tác</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="type" items="${leaveTypes}" varStatus="loop">
                                    <tr>
                                        <td>${loop.index + 1}</td>
                                        <td>${type.leaveTypeName}</td>
                                        <td>
                                            <span class="badge px-2 py-1 fw-semibold
                                                  <c:choose>
                                                      <c:when test="${type.status == 'active'}">bg-success text-white</c:when>
                                                      <c:otherwise>bg-secondary text-white</c:otherwise>
                                                  </c:choose>">
                                                <c:choose>
                                                    <c:when test="${type.status == 'active'}">Đang hoạt động</c:when>
                                                    <c:otherwise>Ngừng hoạt động</c:otherwise>
                                                </c:choose>
                                            </span>
                                        </td>

                                        <td>
                                            <!-- Nút cập nhật trạng thái -->
                                            <form id="toggleStatusForm-${type.leaveTypeId}" action="${pageContext.request.contextPath}/manager/leave-types" method="post" style="display:inline;">
                                                <input type="hidden" name="action" value="toggleStatus">
                                                <input type="hidden" name="id" value="${type.leaveTypeId}">
                                                <button type="button" class="btn btn-sm btn-warning"
                                                        onclick="confirmToggleStatus(${type.leaveTypeId}, '${type.leaveTypeName}', '${type.status}')">
                                                    Cập nhật trạng thái
                                                </button>
                                            </form>

                                            <!-- Nút xoá -->
                                            <form id="deleteForm-${type.leaveTypeId}" action="${pageContext.request.contextPath}/manager/leave-types" method="post"
                                                  style="display:inline; margin-left: 5px;">
                                                <input type="hidden" name="action" value="delete">
                                                <input type="hidden" name="id" value="${type.leaveTypeId}">
                                                <button type="button" class="btn btn-sm btn-danger"
                                                        onclick="confirmDelete(${type.leaveTypeId}, '${type.leaveTypeName}')">
                                                    Xoá
                                                </button>
                                            </form>
                                        </td>
                                        <!-- SweetAlert2 -->
                                <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

                                <script>
                                                    function confirmToggleStatus(id, name, currentStatus) {
                                                        Swal.fire({
                                                            title: 'Cập nhật trạng thái?',
                                                            html: `Bạn có chắc muốn <b>cập nhật trạng thái</b> cho loại nghỉ này <b></b><br>`,
                                                            icon: 'question',
                                                            showCancelButton: true,
                                                            confirmButtonText: 'Đồng ý',
                                                            cancelButtonText: 'Huỷ',
                                                            confirmButtonColor: '#198754',
                                                            cancelButtonColor: '#6c757d'
                                                        }).then((result) => {
                                                            if (result.isConfirmed) {
                                                                document.getElementById('toggleStatusForm-' + id).submit();
                                                            }
                                                        });
                                                    }

                                                    function confirmDelete(id, name) {
                                                        Swal.fire({
                                                            title: 'Xoá loại nghỉ phép?',
                                                            html: `Bạn có chắc muốn <b>xóa</b> loại nghỉ này?<br>Hành động này không thể hoàn tác.`,
                                                            icon: 'warning',
                                                            showCancelButton: true,
                                                            confirmButtonText: 'Xoá',
                                                            cancelButtonText: 'Huỷ',
                                                            confirmButtonColor: '#dc3545',
                                                            cancelButtonColor: '#6c757d'
                                                        }).then((result) => {
                                                            if (result.isConfirmed) {
                                                                document.getElementById('deleteForm-' + id).submit();
                                                            }
                                                        });
                                                    }
                                </script>

                                </tr>
                            </c:forEach>

                            <c:if test="${empty leaveTypes}">
                                <tr>
                                    <td colspan="4" class="text-center text-muted py-4">Chưa có loại nghỉ phép nào.</td>
                                </tr>
                            </c:if>
                            </tbody>
                        </table>
                    </div>

                </div> <!-- container-fluid -->
            </div> <!-- page-wrapper -->

        </div> <!-- main-wrapper -->

        <!-- Import footer -->
        <c:import url="/view/compomnt/footer.jsp" />

    </body>
</html>
