<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html dir="ltr" lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/view/lib/assets/images/favicon.png">
        <title>Admin Panel</title>
        <link href="${pageContext.request.contextPath}/view/lib/assets/extra-libs/c3/c3.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/view/lib/assets/libs/chartist/dist/chartist.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
        <style>
            /* Existing styles */
            div.dataTables_filter {
                float: right !important;
                text-align: right;
            }
            div.dataTables_paginate {
                float: right !important;
                text-align: right;
            }
            div.dataTables_length {
                float: left !important;
            }
            .dataTables_wrapper .dataTables_filter input {
                margin-left: 0.5rem;
            }

            /* Notification dropdown đẹp hiện đại */
            .dropdown-menu.mailbox {
                width: 340px !important;
                max-height: 410px;
                padding: 0;
                border-radius: 16px !important;
                box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.13);
                border: none;
            }

            .message-center.notifications {
                max-height: 310px;
                overflow-y: auto;
                padding: 0.5rem 0.2rem;
                scrollbar-color: #bbb #f5f5f5;
                scrollbar-width: thin;
            }

            .message-center.notifications::-webkit-scrollbar {
                width: 8px;
                border-radius: 12px;
                background: #f5f5f5;
            }
            .message-center.notifications::-webkit-scrollbar-thumb {
                background: #ccc;
                border-radius: 12px;
            }
            .message-center.notifications::-webkit-scrollbar-thumb:hover {
                background: #999;
            }

            .message-center.notifications .message-item {
                border-radius: 10px;
                transition: background 0.2s;
                background: #fff;
                margin: 6px 0;
                box-shadow: 0 1px 2px rgba(0,0,0,0.05);
                padding: 12px 12px;
                display: flex;
                align-items: flex-start;
                min-height: 70px;
                cursor: pointer;
            }
            .message-center.notifications .message-item:hover {
                background: #f2f7fb;
                box-shadow: 0 2px 8px rgba(31, 38, 135, 0.07);
            }

            .message-center.notifications .btn-circle {
                min-width: 40px;
                height: 40px;
                display: flex !important;
                align-items: center;
                justify-content: center;
                font-size: 1.3rem;
                margin-right: 10px;
                box-shadow: 0 2px 8px rgba(31, 38, 135, 0.10);
            }

            .message-center.notifications .message-title {
                font-size: 1.07rem;
                font-weight: 600;
                margin-bottom: 3px;
                color: #213a5a;
            }

            .message-center.notifications .font-12 {
                font-size: 0.95rem !important;
                color: #465368 !important;
                opacity: 0.93;
            }

            .message-center.notifications .text-truncate {
                white-space: nowrap;
                max-width: 180px;
                overflow: hidden;
                text-overflow: ellipsis;
                display: block;
            }

            /* Đảm bảo dòng "Check all notifications" luôn dưới cùng, tách biệt danh sách */
            .dropdown-menu.mailbox .list-style-none {
                display: flex;
                flex-direction: column;
                height: 100%;
                min-height: 350px;
                justify-content: flex-start;
            }

            .dropdown-menu.mailbox .list-style-none > li:last-child {
                margin-top: auto;
                border-top: 1px solid #efefef;
                background: #f7fafd;
                border-radius: 0 0 16px 16px;
                text-align: center;
            }

            .dropdown-menu.mailbox .list-style-none > li:last-child .nav-link {
                padding: 15px 0 10px 0 !important;
                color: #1b3358;
                font-size: 1.03rem;
                font-weight: 600;
                background: none !important;
            }
            .dropdown-menu.mailbox .list-style-none > li:last-child .nav-link:hover {
                color: #297fff;
                text-decoration: underline;
                background: none !important;
            }
            /* Notification dropdown đẹp hiện đại */
            .dropdown-menu.mailbox {
                width: 340px !important;
                max-height: 410px;
                padding: 0;
                border-radius: 16px !important;
                box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.13);
                border: none;
            }

            .message-center.notifications {
                max-height: 310px;
                overflow-y: auto;
                padding: 0.5rem 0.2rem;
                scrollbar-color: #bbb #f5f5f5;
                scrollbar-width: thin;
            }

            .message-center.notifications::-webkit-scrollbar {
                width: 8px;
                border-radius: 12px;
                background: #f5f5f5;
            }
            .message-center.notifications::-webkit-scrollbar-thumb {
                background: #ccc;
                border-radius: 12px;
            }
            .message-center.notifications::-webkit-scrollbar-thumb:hover {
                background: #999;
            }

            .message-center.notifications .message-item {
                border-radius: 10px;
                transition: background 0.2s;
                background: #fff;
                margin: 6px 0;
                box-shadow: 0 1px 2px rgba(0,0,0,0.05);
                padding: 12px 12px;
                display: flex;
                align-items: flex-start;
                min-height: 70px;
                cursor: pointer;
            }
            .message-center.notifications .message-item:hover {
                background: #f2f7fb;
                box-shadow: 0 2px 8px rgba(31, 38, 135, 0.07);
            }

            .message-center.notifications .btn-circle {
                min-width: 40px;
                height: 40px;
                display: flex !important;
                align-items: center;
                justify-content: center;
                font-size: 1.3rem;
                margin-right: 10px;
                box-shadow: 0 2px 8px rgba(31, 38, 135, 0.10);
            }

            .message-center.notifications .message-title {
                font-size: 1.07rem;
                font-weight: 600;
                margin-bottom: 3px;
                color: #213a5a;
            }

            .message-center.notifications .font-12 {
                font-size: 0.95rem !important;
                color: #465368 !important;
                opacity: 0.93;
            }

            .message-center.notifications .text-truncate {
                white-space: nowrap;
                max-width: 180px;
                overflow: hidden;
                text-overflow: ellipsis;
                display: block;
            }

            /* Đảm bảo dòng "Check all notifications" luôn dưới cùng, tách biệt danh sách */
            .dropdown-menu.mailbox .list-style-none {
                display: flex;
                flex-direction: column;
                height: 100%;
                min-height: 350px;
                justify-content: flex-start;
            }

            .dropdown-menu.mailbox .list-style-none > li:last-child {
                margin-top: auto;
                border-top: 1px solid #efefef;
                background: #f7fafd;
                border-radius: 0 0 16px 16px;
                text-align: center;
            }

            .dropdown-menu.mailbox .list-style-none > li:last-child .nav-link {
                padding: 15px 0 10px 0 !important;
                color: #1b3358;
                font-size: 1.03rem;
                font-weight: 600;
                background: none !important;
            }
            .dropdown-menu.mailbox .list-style-none > li:last-child .nav-link:hover {
                color: #297fff;
                text-decoration: underline;
                background: none !important;
            }

        </style>
    </head>
    <body>
        <header class="topbar" data-navbarbg="skin6">
            <nav class="navbar top-navbar navbar-expand-md">
                <div class="navbar-header" data-logobg="skin6">
                    <a class="nav-toggler waves-effect waves-light d-block d-md-none" href="javascript:void(0)"><i
                            class="ti-menu ti-close"></i></a>
                    <div class="navbar-brand">
                        <a href="index.html">
                            <p>Admin</p>             
                        </a>
                    </div>
                    <a class="topbartoggler d-block d-md-none waves-effect waves-light" href="javascript:void(0)"
                       data-toggle="collapse" data-target="#navbarSupportedContent"
                       aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><i
                            class="ti-more"></i></a>
                </div>
                <div class="navbar-collapse collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav float-left mr-auto ml-3 pl-1">
                        <!-- Notification -->
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle pl-md-3 position-relative" href="javascript:void(0)"
                               id="bell" role="button" data-toggle="dropdown" aria-haspopup="true"
                               aria-expanded="false">
                                <span><i data-feather="bell" class="svg-icon"></i></span>
                                <span class="badge badge-primary notify-no rounded-circle" id="notification-count">0</span>
                            </a>
                            <div class="dropdown-menu dropdown-menu-left mailbox animated bounceInDown">
                                <ul class="list-style-none">
                                    <li>
                                        <div class="message-center notifications position-relative" id="notification-list">
                                            <!-- Notifications will be populated here via AJAX -->
                                        </div>
                                    </li>
                                    <li>
                                        <a class="nav-link pt-3 text-center text-dark" href="javascript:void(0);">
                                            <strong>Check all notifications</strong>
                                            <i class="fa fa-angle-right"></i>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </li>
                        <!-- End Notification -->
                    </ul>
                    <ul class="navbar-nav float-right">
                        <li class="nav-item d-none d-md-block">
                            <a class="nav-link" href="javascript:void(0)">
                                <form>
                                    <div class="customize-input">
                                        <input class="form-control custom-shadow custom-radius border-0 bg-white"
                                               type="search" placeholder="Search" aria-label="Search">
                                        <i class="form-control-icon" data-feather="search"></i>
                                    </div>
                                </form>
                            </a>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="javascript:void(0)" data-toggle="dropdown"
                               aria-haspopup="true" aria-expanded="false">
                                <img src="./avata.png" alt="user" class="rounded-circle"
                                     width="40">
                                <span class="ml-2 d-none d-lg-inline-block"><span>Hello,</span> <span
                                        class="text-dark">${sessionScope.user.fullName}</span> <i data-feather="chevron-down"
                                        class="svg-icon"></i></span>
                            </a>
                            <div class="dropdown-menu dropdown-menu-right user-dd animated flipInY">
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/profile"><i data-feather="user"
                                                                                                              class="svg-icon mr-2 ml-1"></i>
                                    My Profile</a>
                                <a class="dropdown-item" href="javascript:void(0)"><i data-feather="credit-card"
                                                                                      class="svg-icon mr-2 ml-1"></i>
                                    My Balance</a>
                                <a class="dropdown-item" href="javascript:void(0)"><i data-feather="mail"
                                                                                      class="svg-icon mr-2 ml-1"></i>
                                    Inbox</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="javascript:void(0)"><i data-feather="settings"
                                                                                      class="svg-icon mr-2 ml-1"></i>
                                    Account Setting</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/logout"><i data-feather="power"
                                                                                                             class="svg-icon mr-2 ml-1"></i>
                                    Logout</a>
                                <div class="dropdown-divider"></div>
                                <div class="pl-4 p-3"><a href="javascript:void(0)" class="btn btn-sm btn-info">View
                                        Profile</a></div>
                            </div>
                        </li>
                    </ul>
                </div>
            </nav>
        </header>
        <!-- End Topbar -->

        <!-- JS Scripts -->
        <script src="${pageContext.request.contextPath}/view/lib/assets/libs/jquery/dist/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/view/lib/assets/libs/popper.js/dist/umd/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/view/lib/assets/libs/bootstrap/dist/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/view/lib/dist/js/feather.min.js"></script>
        <script src="${pageContext.request.contextPath}/view/lib/assets/libs/perfect-scrollbar/dist/perfect-scrollbar.jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/view/lib/dist/js/sidebarmenu.js"></script>
        <script src="${pageContext.request.contextPath}/view/lib/dist/js/custom.min.js"></script>
        <script src="${pageContext.request.contextPath}/view/lib/assets/extra-libs/c3/d3.min.js"></script>
        <script src="${pageContext.request.contextPath}/view/lib/assets/extra-libs/c3/c3.min.js"></script>
        <script src="${pageContext.request.contextPath}/view/lib/assets/libs/chartist/dist/chartist.min.js"></script>
        <script src="${pageContext.request.contextPath}/view/lib/assets/libs/chartist-plugin-tooltips/dist/chartist-plugin-tooltip.min.js"></script>
        <script src="${pageContext.request.contextPath}/view/lib/dist/js/pages/dashboards/dashboard1.min.js"></script>
        <script>
            $(document).ready(function () {
                // Function to fetch notifications via AJAX
                function loadNotifications() {
                    $.ajax({
                        url: '${pageContext.request.contextPath}/NotificationUserApiServlet',
                        type: 'GET',
                        dataType: 'json',
                        success: function (data) {
                            // Update notification count
                            $('#notification-count').text(data.length);

                            // Clear existing notifications
                            $('#notification-list').empty();

                            // Populate notifications
                            if (data.length === 0) {
                                $('#notification-list').append(
                                        '<div class="message-item d-flex align-items-center border-bottom px-3 py-2">' +
                                        '<div class="w-100 text-center text-muted">No notifications</div>' +
                                        '</div>'
                                        );
                            } else {
                                $.each(data, function (index, notification) {
                                    // Determine icon and color based on receiverStatus
                                    var iconClass = notification.receiverStatus === 'unread' ? 'btn-danger' : 'btn-success';
                                    var iconType = notification.receiverStatus === 'unread' ? 'bell' : 'check';

                                    // Append notification item
                                    $('#notification-list').append(
                                            '<a href="javascript:void(0)" class="message-item d-flex align-items-center border-bottom px-3 py-2">' +
                                            '<div class="btn ' + iconClass + ' rounded-circle btn-circle"><i data-feather="' + iconType + '" class="text-white"></i></div>' +
                                            '<div class="w-75 d-inline-block v-middle pl-2">' +
                                            '<h6 class="message-title mb-0 mt-1">' + notification.title + '</h6>' +
                                            '<span class="font-12 text-nowrap d-block text-muted text-truncate">' + notification.content + '</span>' +
                                            '<span class="font-12 text-nowrap d-block text-muted">' + notification.createdAt + '</span>' +
                                            '</div>' +
                                            '</a>'
                                            );
                                });
                            }

                            // Re-initialize feather icons after DOM update
                            feather.replace();
                        },
                        error: function (xhr, status, error) {
                            console.error('Error fetching notifications:', error);
                            $('#notification-list').empty().append(
                                    '<div class="message-item d-flex align-items-center border-bottom px-3 py-2">' +
                                    '<div class="w-100 text-center text-muted">Error loading notifications</div>' +
                                    '</div>'
                                    );
                        }
                    });
                }

                // Load notifications on page load
                loadNotifications();

                // Optional: Refresh notifications periodically (e.g., every 30 seconds)
                setInterval(loadNotifications, 30000);
            });
        </script>
    </body>
</html>