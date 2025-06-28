<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Lịch ngày nghỉ lễ</title>
        <link href='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.8/index.global.min.css' rel='stylesheet' />
        <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
        <style>
            body {
                background: #e8eef5;
            }
            .calendar-wrapper {
                max-width: 900px;
                margin: 38px auto;
                background: #fff;
                border-radius: 18px;
                box-shadow: 0 8px 36px 0 rgba(41,127,255,0.10), 0 1.5px 12px 0 rgba(40,70,130,0.08);
                padding: 36px 26px 18px 26px;
            }
            .fc-toolbar-title {
                color: #297fff !important;
                font-size: 1.6rem;
            }
            .fc-daygrid-event {
                background: #fde3e4;
                color: #d63447;
                border: none;
                font-weight: 600;
                border-radius: 7px;
            }
            .fc-daygrid-day.fc-day-today {
                background: #e5f0fe;
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
                    <div class="calendar-wrapper">
                        <h2 style="text-align:center; color:#297fff; margin-bottom:22px;">Lịch ngày nghỉ lễ</h2>
                        <div id='calendar'></div>
                    </div>
                </div>
            </div>
        </div>
        <c:import url="/view/compomnt/footer.jsp" />

        <script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.8/index.global.min.js'></script>
        <script>
            // Convert JSP holidayList to JS events
            var events = [
            <c:forEach var="h" items="${holidayList}" varStatus="status">
            {
            title: "${h.holidayName}",
                    start: "${h.holidayDate}",
                    allDay: true,
                    color: "#ffbaba"
    }<c:if test="${!status.last}">,</c:if>
            </c:forEach>
            ];
            document.addEventListener('DOMContentLoaded', function() {
            var calendarEl = document.getElementById('calendar');
            var calendar = new FullCalendar.Calendar(calendarEl, {
            initialView: 'dayGridMonth',
                    locale: 'vi',
                    height: 600,
                    headerToolbar: {
                    left: 'prev,next today',
                            center: 'title',
                            right: ''
                    },
                    events: events,
                    eventDidMount: function(info) {
                    info.el.title = info.event.title;
                    }
            });
            calendar.render();
            });</script>
    </body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Lịch ngày nghỉ lễ</title>
        <link href='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.8/index.global.min.css' rel='stylesheet' />
        <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
        <style>
            body {
                background: #e8eef5;
            }
            .calendar-wrapper {
                max-width: 900px;
                margin: 38px auto;
                background: #fff;
                border-radius: 18px;
                box-shadow: 0 8px 36px 0 rgba(41,127,255,0.10), 0 1.5px 12px 0 rgba(40,70,130,0.08);
                padding: 36px 26px 18px 26px;
            }
            .fc-toolbar-title {
                color: #297fff !important;
                font-size: 1.6rem;
            }
            .fc-daygrid-event {
                background: #fde3e4;
                color: #d63447;
                border: none;
                font-weight: 600;
                border-radius: 7px;
            }
            .fc-daygrid-day.fc-day-today {
                background: #e5f0fe;
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
                    <div class="calendar-wrapper">
                        <h2 style="text-align:center; color:#297fff; margin-bottom:22px;">Lịch ngày nghỉ lễ</h2>
                        <div id='calendar'></div>
                    </div>
                </div>
            </div>
        </div>
        <c:import url="/view/compomnt/footer.jsp" />

        <script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.8/index.global.min.js'></script>
        <script>
            // Convert JSP holidayList to JS events
            var events = [
            <c:forEach var="h" items="${holidayList}" varStatus="status">
            {
            title: "${h.holidayName}",
                    start: "${h.holidayDate}",
                    allDay: true,
                    color: "#ffbaba"
    }<c:if test="${!status.last}">,</c:if>
            </c:forEach>
            ];
            document.addEventListener('DOMContentLoaded', function() {
            var calendarEl = document.getElementById('calendar');
            var calendar = new FullCalendar.Calendar(calendarEl, {
            initialView: 'dayGridMonth',
                    locale: 'vi',
                    height: 600,
                    headerToolbar: {
                    left: 'prev,next today',
                            center: 'title',
                            right: ''
                    },
                    events: events,
                    eventDidMount: function(info) {
                    info.el.title = info.event.title;
                    }
            });
            calendar.render();
            });
        </script>
    </body>
</html>
