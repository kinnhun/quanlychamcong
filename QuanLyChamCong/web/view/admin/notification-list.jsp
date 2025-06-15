<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Danh Sách Thông Báo Đã Gửi</title>
        <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
        <style>
            .table-scroll {
                max-height: 500px;
                overflow-y: auto;
            }
            .short-content {
                max-width: 350px;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
            }
            .filter-row .form-select, .filter-row .form-control {
                min-width: 130px;
                margin-right: 8px;
            }
            .pagination li {
                margin: 0 2px;
            }
            .action-btn {
                min-width: 110px;
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
                            <h4 class="mb-0">Danh Sách Thông Báo Đã Gửi</h4>
                            <a href="${pageContext.request.contextPath}/admin/send-notification" class="btn btn-sm btn-success">
                                <i class="fa fa-plus"></i> Gửi thông báo mới
                            </a>
                        </div>
                        <div class="card-body">

                            <!-- Filter form -->
                            <form method="get" action="${pageContext.request.contextPath}/admin/notification-list" class="mb-3">
                                <div class="row g-2 filter-row align-items-center">
                                    <div class="col-auto">
                                        <select class="form-select" name="status">
                                            <option value="">-- Trạng thái --</option>
                                            <option value="active" <c:if test="${param.status == 'active'}">selected</c:if>>Đang hiệu lực</option>
                                            <option value="inactive" <c:if test="${param.status == 'inactive'}">selected</c:if>>Không hiệu lực</option>
                                            </select>
                                        </div>
                                        <div class="col-auto">
                                            <select class="form-select" name="senderId">
                                                <option value="">-- Người gửi --</option>
                                            <c:forEach var="sender" items="${senderList}">
                                                <option value="${sender.userId}" <c:if test="${param.senderId == sender.userId}">selected</c:if>>
                                                    ${sender.fullName}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-auto">
                                        <input type="text" class="form-control" name="searchTitle" placeholder="Tìm tiêu đề" value="${param.searchTitle}">
                                    </div>
                                    <div class="col-auto">
                                        <input type="text" class="form-control" name="searchContent" placeholder="Tìm nội dung" value="${param.searchContent}">
                                    </div>
                                    <div class="col-auto">
                                        <select class="form-select" name="sort">
                                            <option value="desc" <c:if test="${param.sort == 'desc' || empty param.sort}">selected</c:if>>Mới nhất</option>
                                            <option value="asc" <c:if test="${param.sort == 'asc'}">selected</c:if>>Cũ nhất</option>
                                            </select>
                                        </div>
                                        <div class="col-auto">
                                            <button type="submit" class="btn btn-outline-primary">Lọc</button>
                                            <a href="${pageContext.request.contextPath}/admin/notification-list" class="btn btn-outline-secondary">Xóa lọc</a>
                                    </div>
                                </div>
                            </form>
                            <!-- End filter form -->

                            <!-- Table -->
                            <div class="table-responsive table-scroll mb-3">
                                <table class="table table-bordered table-hover align-middle">
                                    <thead class="table-light">
                                        <tr>
                                            <th>#</th>
                                            <th>Tiêu đề</th>
                                            <th>Nội dung</th>
                                            <th>Người gửi</th>
                                            <th>Ngày gửi</th>
                                            <th>Thời gian hẹn giờ</th>
                                            <th>Trạng thái</th>
                                            <th>Ảnh</th>
                                            <th>File</th>
                                            <th class="action-btn">Hành động</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="noti" items="${notificationList}" varStatus="loop">
                                            <tr>
                                                <td>${(page - 1) * pageSize + loop.index + 1}</td>
                                                <td>${noti.title}</td>
                                                <td class="short-content" title="${noti.content}">${noti.content}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${not empty noti.createdBy.fullName}">
                                                            ${noti.createdBy.fullName}
                                                        </c:when>
                                                        <c:otherwise>
                                                            (ID: ${noti.createdBy.userId})
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>${noti.createdAt}</td>
                                                <td>
                                                    <c:if test="${noti.scheduledTime != null}">
                                                        ${noti.scheduledTime}
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${noti.status == 'active'}">
                                                            <span style="
                                                                  background: #198754;
                                                                  color: #fff;
                                                                  font-weight: 600;
                                                                  font-size: 1em;
                                                                  border-radius: 1.2em;
                                                                  padding: 0.45em 1.2em;
                                                                  box-shadow: 0 1px 6px #19875420;
                                                                  display: inline-flex;
                                                                  align-items: center;">
                                                                <i class="fa fa-check-circle" style="margin-right:7px;font-size:1.1em;"></i>
                                                                Đang hiệu lực
                                                            </span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span style="
                                                                  background: #dc3545;
                                                                  color: #fff;
                                                                  font-weight: 600;
                                                                  font-size: 1em;
                                                                  border-radius: 1.2em;
                                                                  padding: 0.45em 1.2em;
                                                                  box-shadow: 0 1px 6px #dc354520;
                                                                  display: inline-flex;
                                                                  align-items: center;">
                                                                <i class="fa fa-ban" style="margin-right:7px;font-size:1.1em;"></i>
                                                                Không hiệu lực
                                                            </span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>


                                                <td>
                                                    <c:if test="${not empty noti.imageUrl}">
                                                        <a href="${noti.imageUrl}" target="_blank">Xem/Tải</a>
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <c:if test="${not empty noti.fileUrl}">
                                                        <a href="${noti.fileUrl}" target="_blank">Tải về</a>
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <form method="post" action="${pageContext.request.contextPath}/admin/notification-list"
                                                          style="display:inline;">
                                                        <input type="hidden" name="notificationId" value="${noti.notificationId}" />
                                                        <input type="hidden" name="action" value="updateStatus"/>
                                                        <input type="hidden" name="newStatus" value="${noti.status == 'active' ? 'inactive' : 'active'}"/>
                                                        <button type="submit"
                                                                class="btn btn-sm ${noti.status == 'active' ? 'btn-warning' : 'btn-success'}"
                                                                onclick="return confirm('Bạn chắc chắn muốn chuyển trạng thái?')">
                                                            ${noti.status == 'active' ? 'Vô hiệu hóa' : 'Kích hoạt'}
                                                        </button>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        <c:if test="${empty notificationList}">
                                            <tr><td colspan="10" class="text-center">Không có thông báo nào!</td></tr>
                                        </c:if>
                                    </tbody>
                                </table>
                            </div>

                            <!-- Pagination -->
                            <c:set var="totalPages" value="${(total / pageSize) + (total % pageSize == 0 ? 0 : 1)}"/>
                            <nav>
                                <ul class="pagination justify-content-center">
                                    <li class="page-item <c:if test='${page == 1}'>disabled</c:if>'">
                                            <a class="page-link"
                                               href="?page=${page - 1}&status=${param.status}&senderId=${param.senderId}&searchTitle=${param.searchTitle}&searchContent=${param.searchContent}&sort=${param.sort}">&laquo;</a>
                                    </li>
                                    <c:forEach begin="1" end="${totalPages}" var="i">
                                        <li class="page-item <c:if test='${page == i}'>active</c:if>'">
                                                <a class="page-link"
                                                   href="?page=${i}&status=${param.status}&senderId=${param.senderId}&searchTitle=${param.searchTitle}&searchContent=${param.searchContent}&sort=${param.sort}">${i}</a>
                                        </li>
                                    </c:forEach>
                                    <li class="page-item <c:if test='${page == totalPages || totalPages == 0}'>disabled</c:if>'">
                                            <a class="page-link"
                                               href="?page=${page + 1}&status=${param.status}&senderId=${param.senderId}&searchTitle=${param.searchTitle}&searchContent=${param.searchContent}&sort=${param.sort}">&raquo;</a>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <c:import url="/view/compomnt/footer.jsp" />
    </body>
</html>
