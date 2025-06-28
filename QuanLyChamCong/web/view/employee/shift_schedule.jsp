<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lịch ca làm</title>
    <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        #calendar {
            max-width: 1100px;
            margin: 0 auto;
            padding: 20px;
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            height: 600px;
            min-height: 400px; /* Đảm bảo chiều cao tối thiểu */
        }
        .no-data {
            text-align: center;
            padding: 20px;
            color: #6c757d;
            font-size: 16px;
        }
    </style>
</head>
<body>
<div id="main-wrapper" data-theme="light" data-layout="vertical" data-navbarbg="skin6" data-sidebartype="full"
     data-sidebar-position="fixed" data-header-position="fixed" data-boxed-layout="full">

    <c:import url="/view/compomnt/header.jsp" />
    <c:import url="/view/compomnt/siderbar.jsp" />

    <div class="page-wrapper">
        <div class="container-fluid">
            <c:import url="/view/compomnt/notification.jsp" />
            <h3 class="mb-4 text-primary fw-bold fs-3">🗓️ Lịch ca làm</h3>

            <!-- LỊCH -->
            <c:choose>
                <c:when test="${empty userShiftList}">
                    <div class="no-data">Không có ca làm nào.</div>
                </c:when>
                <c:otherwise>
                    <div id="calendar"></div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <c:import url="/view/compomnt/footer.jsp" />
</div>

<!-- Modal chi tiết ca làm -->
<div class="modal fade" id="shiftDetailModal" tabindex="-1" aria-labelledby="shiftDetailModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="shiftDetailModalLabel">Chi tiết ca làm</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p><strong>Ca làm:</strong> <span id="shiftTitle"></span></p>
                <p><strong>Ngày:</strong> <span id="shiftDate"></span></p>
                <p><strong>Thời gian:</strong> <span id="shiftTime"></span></p>
                <p><strong>Ghi chú:</strong> <span id="shiftNote"></span></p>
                <p><strong>Chi nhánh:</strong> <span id="shiftLocation"></span></p>
                <p><strong>Phòng ban:</strong> <span id="shiftDepartment"></span></p>
                <p><strong>Người phân ca:</strong> <span id="shiftAssignedBy"></span></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        console.log("FullCalendar initializing...");
        var calendarEl = document.getElementById('calendar');
        if (calendarEl) {
            var calendar = new FullCalendar.Calendar(calendarEl, {
                initialView: 'dayGridMonth',
                locale: 'vi',
                headerToolbar: {
                    left: 'prev,next today',
                    center: 'title',
                    right: 'dayGridMonth,timeGridWeek,timeGridDay'
                },
                events: [
                    <c:forEach var="us" items="${userShiftList}" varStatus="status">
                    {
                        title: '${us.shift.shiftName.replace("'", "\\'").replace("\"", "\\\"")}',
                        start: '<fmt:formatDate value="${us.date}" pattern="yyyy-MM-dd"/>T${us.shift.startTime}',
                        end: '<fmt:formatDate value="${us.date}" pattern="yyyy-MM-dd"/>T${us.shift.endTime}',
                        extendedProps: {
                            note: '${us.note != null ? us.note.replace("'", "\\'").replace("\"", "\\\"") : ""}',
                            location: '${us.location != null ? us.location.name.replace("'", "\\'").replace("\"", "\\\"") : ""}',
                            department: '${us.department != null ? us.department.departmentName.replace("'", "\\'").replace("\"", "\\\"") : ""}',
                            assignedBy: '${us.assignedBy != null ? us.assignedBy.fullName.replace("'", "\\'").replace("\"", "\\\"") : ""}'
                        }
                    }<c:if test="${!status.last}">,</c:if>
                    </c:forEach>
                ],
                eventClick: function(info) {
                    document.getElementById('shiftTitle').textContent = info.event.title;
                    document.getElementById('shiftDate').textContent = info.event.start.toLocaleDateString('vi-VN');
                    document.getElementById('shiftTime').textContent = info.event.start.toLocaleTimeString('vi-VN', {hour12: false}) + ' - ' +
                        info.event.end.toLocaleTimeString('vi-VN', {hour12: false});
                    document.getElementById('shiftNote').textContent = info.event.extendedProps.note || 'Không có';
                    document.getElementById('shiftLocation').textContent = info.event.extendedProps.location || 'Không có';
                    document.getElementById('shiftDepartment').textContent = info.event.extendedProps.department || 'Không có';
                    document.getElementById('shiftAssignedBy').textContent = info.event.extendedProps.assignedBy || 'Không có';

                    var modal = new bootstrap.Modal(document.getElementById('shiftDetailModal'));
                    modal.show();
                },
                eventTimeFormat: {
                    hour: '2-digit',
                    minute: '2-digit',
                    hour12: false
                },
                windowResize: function() {
                    calendar.updateSize(); // Cập nhật kích thước khi resize
                }
            });
            console.log("FullCalendar rendering...");
            calendar.render();
            setTimeout(function() {
                calendar.updateSize(); // Cập nhật lại kích thước sau khi load
            }, 100);
        } else {
            console.error("Calendar element not found");
        }
    });
</script>
</body>
</html>