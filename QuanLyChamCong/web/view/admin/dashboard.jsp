<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Quản lý - Thống kê</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome cho biểu tượng -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
        <!-- CSS tùy chỉnh -->
        <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
        <style>
            .card-header {
                background-color: #f8f9fa;
            }
            .bg-primary-custom {
                background-color: #007bff;
                color: white;
            } /* Màu xanh dương */
            .bg-success-custom {
                background-color: #28a745;
                color: white;
            } /* Màu xanh lá */
            .calendar-day {
                width: 40px;
                height: 40px;
                text-align: center;
                border: 1px solid #ddd;
            }
            .calendar-day.active {
                background-color: #28a745;
                color: white;
            } /* Màu xanh lá cho ngày active */
            .notification-badge {
                background-color: #dc3545;
                color: white;
                border-radius: 50%;
                padding: 2px 6px;
                font-size: 12px;
            } /* Màu đỏ thông báo */
            .nav-btn-primary {
                background-color: #007bff;
                color: white;
                border-color: #007bff;
            } /* Màu nút Xn nhân sự */
            .nav-btn-danger {
                background-color: #dc3545;
                color: white;
                border-color: #dc3545;
            } /* Màu nút Thêm ca */
            .nav-btn-success {
                background-color: #28a745;
                color: white;
                border-color: #28a745;
            } /* Màu nút Quản chấm công */
            .nav-btn-warning {
                background-color: #ffc107;
                color: white;
                border-color: #ffc107;
            } /* Màu nút Đánh giá */
            .nav-btn-info {
                background-color: #17a2b8;
                color: white;
                border-color: #17a2b8;
            } /* Màu nút Báo cáo */
            .nav-btn-secondary {
                background-color: #6c757d;
                color: white;
                border-color: #6c757d;
            } /* Màu nút Thông báo */
            .nav-btn-dark {
                background-color: #343a40;
                color: white;
                border-color: #343a40;
            } /* Màu nút Đăng xuất */
            .chart-bar {
                background-color: #007bff;
            } /* Màu cột biểu đồ */
            .chart-line {
                border-color: #28a745;
            } /* Màu đường biểu đồ */
            .chart-pie-lam {
                background-color: #28a745;
            } /* Màu Làm việc */
            .chart-pie-nghi {
                background-color: #ffc107;
            } /* Màu Nghỉ */
            .chart-pie-tang {
                background-color: #17a2b8;
            } /* Màu Tăng ca */
        </style>

    </head>
    <body>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <div id="main-wrapper" data-theme="light" data-layout="vertical" data-navbarbg="skin6" data-sidebartype="full"
             data-sidebar-position="fixed" data-header-position="fixed" data-boxed-layout="full">

            <!-- Import header -->
            <c:import url="/view/compomnt/header.jsp" />

            <!-- Import sidebar -->
            <c:import url="/view/compomnt/siderbar.jsp" />

            <div class="page-wrapper">
                <div class="container-fluid py-4">
                    <!-- Thông báo -->
                    <c:import url="/view/compomnt/notification.jsp" />


                    <!-- Dòng thứ hai: Nội dung chính -->
                    <div class="row">
                        <!-- Cột trái: Lịch chấm công và thông tin -->
                        <div class="col-md-4">
                            <!-- Lịch chấm công -->
                            <div class="card mb-4">
                                <div class="card-header bg-primary-custom  text-dark">Lịch ca làm chấm công</div>
                                <div class="card-body">
                                    <div class="d-flex justify-content-between mb-2">
                                        <span>Tháng 06</span>
                                        <span>2025</span>
                                    </div>
                                    <c:forEach items="${listShift}" var="listShift">
                                        <div class="alert alert-warning" role="alert">
                                            ${listShift.shiftName}<br>
                                            ${listShift.description}<br>
                                            ${listShift.startTime} - ${listShift.endTime}<br>

                                        </div>
                                    </c:forEach>

                                </div>
                            </div>
                            <!-- Lịch chấm công - Kỳ Công -->
                            <div class="card">
                                <div class="card-header bg-success-custom text-dark">Duyệt lịch chấm công - Kỳ Công: 12</div>
                                <div class="card-body">
                                    <table class="table table-bordered">
                                        <tr>
                                            <th>Th 2</th>
                                            <th>Th 3</th>
                                            <th>Th 4</th>
                                            <th>Th 5</th>
                                            <th>Th 6</th>
                                            <th>Th 7</th>
                                            <th>CN</th>
                                        </tr>
                                        <tr>
                                            <td class="calendar-day">4</td>
                                            <td class="calendar-day">5</td>
                                            <td class="calendar-day">6</td>
                                            <td class="calendar-day">7</td>
                                            <td class="calendar-day">8</td>
                                            <td class="calendar-day">9</td>
                                            <td class="calendar-day">10</td>
                                        </tr>
                                        <tr>
                                            <td class="calendar-day">11</td>
                                            <td class="calendar-day">12</td>
                                            <td class="calendar-day">13</td>
                                            <td class="calendar-day">14</td>
                                            <td class="calendar-day">15</td>
                                            <td class="calendar-day">16</td>
                                            <td class="calendar-day">17</td>
                                        </tr>
                                        <tr>
                                            <td class="calendar-day">18</td>
                                            <td class="calendar-day">19</td>
                                            <td class="calendar-day">20</td>
                                            <td class="calendar-day">21</td>
                                            <td class="calendar-day">22</td>
                                            <td class="calendar-day">23</td>
                                            <td class="calendar-day">24</td>
                                        </tr>
                                        <tr>
                                            <td class="calendar-day">25</td>
                                            <td class="calendar-day">26</td>
                                            <td class="calendar-day">27</td>
                                            <td class="calendar-day">28</td>
                                            <td class="calendar-day active">29</td>
                                            <td class="calendar-day">30</td>
                                            <td class="calendar-day">1</td>
                                        </tr>
                                        <tr>
                                            <td class="calendar-day">2</td>
                                            <td class="calendar-day">3</td>
                                            <td class="calendar-day">4</td>
                                            <td class="calendar-day"></td>
                                            <td class="calendar-day"></td>
                                            <td class="calendar-day"></td>
                                            <td class="calendar-day"></td>
                                        </tr>
                                    </table>
                                    <button class="btn btn-primary mt-2">Duyệt tất cả</button>
                                </div>
                            </div>
                            
                            
                            
                            
                            
                            
                        </div>

                        <!-- Cột giữa: Biểu đồ -->
                        <div class="col-md-4">
                            <!-- Biểu đồ cột -->



                            <div class="card mb-4">
                                <div class="card-header">Tình trạng nhân viên</div>
                                <div class="card-body text-center">
                                    <h3>${totalEmployees}</h3>
                                    <p>Tổng nhân viên</p>
                                    <canvas id="barChart" width="200" height="100"></canvas>
                                </div>
                            </div>
                            <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                            <script>
                                // Biểu đồ cột
                                const barCtx = document.getElementById('barChart').getContext('2d');
                                new Chart(barCtx, {
                                    type: 'bar',
                                    data: {
                                        labels: ['Th 1', 'Th 2', 'Th 3', 'Th 4', 'Th 5', 'Th 6', 'Th 7', 'Th 8', 'Th 9', 'Th 10', 'Th 11', 'Th 12'],
                                        datasets: [{
                                                label: 'Số nhân viên làm việc',
                                                data: [
                                ${shiftsPerMonth['2025-01']},
                                ${shiftsPerMonth['2025-02']},
                                ${shiftsPerMonth['2025-03']},
                                ${shiftsPerMonth['2025-04']},
                                ${shiftsPerMonth['2025-05']},
                                ${shiftsPerMonth['2025-06']},
                                ${shiftsPerMonth['2025-07']},
                                ${shiftsPerMonth['2025-08']},
                                ${shiftsPerMonth['2025-09']},
                                ${shiftsPerMonth['2025-10']},
                                ${shiftsPerMonth['2025-11']},
                                ${shiftsPerMonth['2025-12']}
                                                ],
                                                backgroundColor: '#007bff'
                                            }]
                                    },
                                    options: {
                                        responsive: true,
                                        scales: {
                                            y: {beginAtZero: true}
                                        }
                                    }
                                });
                            </script>


                            <!-- Biểu đồ đường -->
                            <div class="card">
                                <div class="card-header">Hôm nay</div>
                                <div class="card-body text-center">
                                    <p>Nhân viên làm việc hôm nay</p>

                                    <h3>${todayWorkingEmployees}</h3>
                                    <canvas id="lineChart" width="200" height="100"></canvas>
                                </div>
                            </div>
                            <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                            <script>
                                // Biểu đồ đường
                                const lineCtx = document.getElementById('lineChart').getContext('2d');
                                new Chart(lineCtx, {
                                    type: 'line',
                                    data: {
                                        labels: ['Th 2', 'Th 3', 'Th 4', 'Th 5', 'Th 6', 'Th 7'],
                                        datasets: [{
                                                label: 'Nhân viên làm việc',
                                                data: [
                                ${employeesPerDay[0]},
                                ${employeesPerDay[1]},
                                ${employeesPerDay[2]},
                                ${employeesPerDay[3]},
                                ${employeesPerDay[4]},
                                ${employeesPerDay[5]}
                                                ],
                                                borderColor: '#28a745',
                                                fill: false
                                            }]
                                    },
                                    options: {
                                        responsive: true,
                                        scales: {
                                            y: {beginAtZero: true}
                                        }
                                    }
                                });
                            </script>





                        </div>



                        <!-- Cột phải: Thống kê nhân sự và thông tin -->
                        <div class="col-md-4">
                            <!-- Thống kê nhân sự -->
                            <div class="card mb-4">
                                <div class="card-header">Thống kê nhân sự</div>
                                <div class="card-body text-center">
                                    <canvas id="pieChart" width="200" height="100"></canvas>
                                    <p>Làm việc: 210 <span class="text-success">A</span> (67.4%)<br>
                                        Nghỉ: 254 <span class="text-warning">B</span> (32.6%)<br>
                                        Tăng ca: 34 <span class="text-info">C</span> (0.4%)</p>
                                </div>
                            </div>
                            <!-- Thông tin nhân viên -->
                            <div class="card">
                                <div class="card-header bg-primary-custom">Thông tin admin</div>
                                <div class="card-body text-center">
                                    <img src="https://via.placeholder.com/100" class="rounded-circle mb-2" alt="Admin">
                                    <h5>Admin Hệ Thống</h5>
                                    <p>Quản lý toàn diện</p>
                                    <button class="btn btn-outline-primary btn-sm">Cập nhật thông tin</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Dòng thứ ba: Tin tức -->
                    <div class="row mt-4">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-header">Báo cáo & Tài liệu</div>
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-md-4 mb-3">
                                            <div class="card h-100">
                                                <img src="https://via.placeholder.com/150" class="card-img-top" alt="Báo cáo 1">
                                                <div class="card-body">
                                                    <h6 class="card-title">BÁO CÁO 1</h6>
                                                    <p class="card-text">Báo cáo nhân sự tháng 06...</p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-4 mb-3">
                                            <div class="card h-100">
                                                <img src="https://via.placeholder.com/150" class="card-img-top" alt="Báo cáo 2">
                                                <div class="card-body">
                                                    <h6 class="card-title">BÁO CÁO 2</h6>
                                                    <p class="card-text">Tài liệu quản lý ca làm...</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Import footer -->
        <c:import url="/view/compomnt/footer.jsp" />

        <!-- Bootstrap JS và Chart.js -->


        <script>



            // Biểu đồ tròn
            const pieCtx = document.getElementById('pieChart').getContext('2d');
            new Chart(pieCtx, {
                type: 'pie',
                data: {
                    labels: ['Làm việc', 'Nghỉ', 'Tăng ca'],
                    datasets: [{
                            data: [210, 254, 34],
                            backgroundColor: ['#28a745', '#ffc107', '#17a2b8']
                        }]
                },
                options: {responsive: true}
            });
        </script>
    </body>
</html>