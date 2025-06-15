<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Quản lý ngày nghỉ lễ</title>
        <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
        <style>
            .table-responsive {
                background: #fff;
                padding: 2rem;
                border-radius: 12px;
            }

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
            .filter-form .form-control, .filter-form .btn {
                min-width: 130px;
                border-radius: 8px;
                font-size: 15px;
            }
            .filter-form .btn {
                font-weight: 600;
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
                    <c:import url="/view/compomnt/notification.jsp" />

                    <h3 class="mb-4 text-primary fw-bold fs-3 d-flex align-items-center justify-content-between">
                        🎉 Danh sách ngày nghỉ lễ
                        <button class="btn btn-success fw-bold ms-3" data-bs-toggle="modal" data-bs-target="#addHolidayModal">
                            ➕ Thêm ngày nghỉ lễ
                        </button>
                    </h3>

                    <!-- FORM BỘ LỌC -->
                    <form method="get" class="row g-2 align-items-end filter-form mb-4">
                        <div class="col-auto">
                            <label class="form-label">Năm</label>
                            <input type="number" name="year" min="2000" max="2100"
                                   class="form-control"
                                   value="${param.year != null ? param.year : ''}" placeholder="Tất cả"/>
                        </div>
                        <div class="col-auto">
                            <label class="form-label">Tháng</label>
                            <select name="month" class="form-control">
                                <option value="">Tất cả</option>
                                <c:forEach var="m" begin="1" end="12">
                                    <option value="${m}" <c:if test="${param.month == m}">selected</c:if>>Tháng ${m}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-auto">
                            <label class="form-label">Tên ngày nghỉ lễ</label>
                            <input type="text" name="keyword" class="form-control" value="${param.keyword != null ? param.keyword : ''}" placeholder="Nhập tên ngày lễ"/>
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="btn btn-primary fw-bold">Lọc</button>
                            <a href="${pageContext.request.contextPath}/admin/holiday-dates" class="btn btn-outline-secondary fw-bold">Xóa bộ lọc</a>

                        </div>
                    </form>

                    <!-- DANH SÁCH NGÀY NGHỈ LỄ -->
                    <div class="table-responsive shadow-sm">
                        <table class="table table-hover table-bordered text-center align-middle rounded">
                            <thead class="table-light">
                                <tr class="fw-bold text-secondary">
                                    <th>#</th>
                                    <th>Tên ngày nghỉ lễ</th>
                                    <th>Ngày nghỉ lễ</th>
                                    <th>Năm</th>
                                    <th>Ngày tạo</th>
                                    <th style="width:90px;">Thao tác</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="h" items="${holidays}" varStatus="stt">
                                    <tr>
                                        <td>${(page-1)*pageSize + stt.index + 1}</td>
                                        <td>
                                            <span class="badge-holiday">${h.holidayName}</span>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${h.holidayDate}" pattern="dd/MM/yyyy"/>
                                        </td>
                                        <td>${h.year}</td>
                                        <td>
                                            <fmt:formatDate value="${h.createdAt}" pattern="dd/MM/yyyy"/>
                                        </td>
                                        <td>
                                            <form method="post" action="" onsubmit="return confirm('Bạn chắc chắn muốn xóa ngày nghỉ lễ này?');" style="display:inline;">
                                                <input type="hidden" name="action" value="delete"/>
                                                <input type="hidden" name="id" value="${h.holidayId}"/>
                                                <button type="submit" class="btn btn-danger btn-sm">Xóa</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty holidays}">
                                    <tr>
                                        <td colspan="6" class="text-center text-muted">Chưa có ngày nghỉ lễ nào!</td>
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
                                        <c:if test='${param.year != null && param.year ne ""}'> &year=${param.year}</c:if>
                                        <c:if test='${param.month != null && param.month ne ""}'> &month=${param.month}</c:if>
                                        <c:if test='${param.keyword != null && param.keyword ne ""}'> &keyword=${param.keyword}</c:if>">«</a>
                                    </li>
                                <c:forEach var="i" begin="1" end="${totalPages}">
                                    <li class="page-item <c:if test='${i == page}'>active</c:if>'">
                                            <a class="page-link"
                                               href="?page=${i}&pageSize=${pageSize}
                                            <c:if test='${param.year != null && param.year ne ""}'> &year=${param.year}</c:if>
                                            <c:if test='${param.month != null && param.month ne ""}'> &month=${param.month}</c:if>
                                            <c:if test='${param.keyword != null && param.keyword ne ""}'> &keyword=${param.keyword}</c:if>">${i}</a>
                                        </li>
                                </c:forEach>
                                <li class="page-item <c:if test='${page == totalPages}'>disabled</c:if>'">
                                        <a class="page-link"
                                           href="?page=${page+1}&pageSize=${pageSize}
                                        <c:if test='${param.year != null && param.year ne ""}'> &year=${param.year}</c:if>
                                        <c:if test='${param.month != null && param.month ne ""}'> &month=${param.month}</c:if>
                                        <c:if test='${param.keyword != null && param.keyword ne ""}'> &keyword=${param.keyword}</c:if>">»</a>
                                    </li>
                                </ul>
                            </nav>
                    </c:if>
                </div>
            </div>

            <!-- MODAL THÊM NGÀY NGHỈ LỄ -->
            <div class="modal fade" id="addHolidayModal" tabindex="-1" aria-labelledby="addHolidayModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <form method="post" class="modal-content">
                        <input type="hidden" name="action" value="add"/>
                        <div class="modal-header">
                            <h5 class="modal-title" id="addHolidayModalLabel">Thêm ngày nghỉ lễ mới</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
                        </div>
                        <div class="modal-body">
                            <div class="mb-3">
                                <label class="form-label">Tên ngày nghỉ lễ</label>
                                <input type="text" name="holidayName" class="form-control" placeholder="Ví dụ: Tết Dương lịch" required/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Ngày nghỉ lễ</label>
                                <input type="date" name="holidayDate" class="form-control" required/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Năm</label>
                                <input type="number" name="year" min="2000" max="2100"
                                       class="form-control"
                                       value="<%= java.time.Year.now().getValue() %>" required/>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                            <button type="submit" class="btn btn-primary">Lưu ngày nghỉ lễ</button>
                        </div>
                    </form>
                </div>
            </div>

            <c:import url="/view/compomnt/footer.jsp"/>
        </div>

        <!-- Bootstrap Modal JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
