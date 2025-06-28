<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <title>Chọn nhân viên</title>
        <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
        <style>
            /* Container chính */
            .select-container {
                width: 100%;
                max-width: 600px;
                margin: 40px auto;
                padding: 25px;
                background: linear-gradient(135deg, #ffffff, #f8f9fa);
                border-radius: 15px;
                box-shadow: 0 6px 15px rgba(0, 0, 0, 0.1);
                text-align: center;
                position: relative;
            }

            /* Tiêu đề */
            .select-container h3 {
                color: #007bff;
                font-size: 1.5rem;
                font-weight: 700;
                margin-bottom: 25px;
                text-transform: uppercase;
            }

            /* Ô tìm kiếm */
            .select-container .search-input {
                width: 100%;
                padding: 12px 15px;
                border-radius: 8px;
                border: 2px solid #e9ecef;
                font-size: 16px;
                transition: border-color 0.3s ease, box-shadow 0.3s ease;
                outline: none;
            }
            .select-container .search-input:focus {
                border-color: #007bff;
                box-shadow: 0 0 8px rgba(0, 123, 255, 0.2);
            }

            /* Select ẩn */
            .select-container select {
                display: none;
            }

            /* Gợi ý */
            .select-container .suggestions {
                position: absolute;
                top: 100%;
                left: 0;
                right: 0;
                width: 100%;
                max-height: 250px;
                overflow-y: auto;
                background: #fff;
                border: 2px solid #e9ecef;
                border-radius: 8px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                z-index: 1000;
                display: none;
                margin-top: 5px;
            }
            .select-container .suggestion-item {
                padding: 10px 15px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }
            .select-container .suggestion-item:hover {
                background-color: #e9ecef;
            }

            /* Nút submit */
            .select-container button {
                margin-top: 25px;
                padding: 12px 30px;
                border-radius: 8px;
                background: linear-gradient(45deg, #007bff, #0056b3);
                color: #fff;
                font-weight: 600;
                border: none;
                cursor: pointer;
                transition: transform 0.2s ease, box-shadow 0.3s ease;
            }
            .select-container button:hover {
                transform: translateY(-2px);
                box-shadow: 0 4px 12px rgba(0, 123, 255, 0.3);
            }
            .select-container button:active {
                transform: translateY(0);
                box-shadow: none;
            }

            /* Danh sách nhân viên */
            .user-list {
                width: 100%;
                max-width: 600px;
                margin: 30px auto;
                padding: 20px;
                background: #fff;
                border-radius: 15px;
                box-shadow: 0 6px 15px rgba(0, 0, 0, 0.1);
            }
            .user-list h4 {
                color: #343a40;
                font-size: 1.25rem;
                font-weight: 600;
                margin-bottom: 15px;
                text-align: left;
            }
            .user-list ul {
                list-style: none;
                padding: 0;
                margin: 0;
            }
            .user-list li {
                padding: 12px 15px;
                border-bottom: 1px solid #eee;
                cursor: pointer;
                text-align: left;
                transition: background-color 0.3s ease;
            }
            .user-list li:hover {
                background-color: #f1f3f5;
            }
            .user-list li:last-child {
                border-bottom: none;
            }

            /* Responsive */
            @media (max-width: 768px) {
                .select-container, .user-list {
                    margin: 20px auto;
                    padding: 15px;
                    width: 90%;
                }
                .select-container h3 {
                    font-size: 1.3rem;
                }
                .select-container button {
                    width: 100%;
                    margin-top: 15px;
                }
            }
        </style>
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
                    <!-- Thông báo -->
                    <c:import url="/view/compomnt/notification.jsp" />

                    <!-- Form chọn nhân viên -->
                    <div class="select-container">
                        <h3>📋 Chọn nhân viên để xem chấm công</h3>
                        <form id="employeeForm" action="${pageContext.request.contextPath}/manager/attendance-each-employee" method="get">
                            <input type="text" class="search-input" id="searchInput" placeholder="Tìm nhân viên..." autocomplete="off">
                            <select name="userId" id="userSelect" required style="display: none;">
                                <option value="">-- Chọn nhân viên --</option>
                                <c:forEach var="emp" items="${employeeList}">
                                    <option value="${emp.userId}">${emp.fullName}</option>
                                </c:forEach>
                            </select>
                            <div id="suggestions" class="suggestions"></div>
                            <button type="submit">Xem chấm công</button>
                        </form>
                    </div>


                </div>   
            </div>   
        </div>

        <!-- Import footer -->
        <c:import url="/view/compomnt/footer.jsp" />

        <!-- JavaScript -->
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const searchInput = document.getElementById('searchInput');
                const suggestions = document.getElementById('suggestions');
                const userSelect = document.getElementById('userSelect');
                const form = document.getElementById('employeeForm');

                // Lấy tất cả các option từ select
                const options = Array.from(userSelect.options).slice(1); // Loại bỏ option mặc định

                searchInput.addEventListener('input', function () {
                    const query = this.value.toLowerCase();
                    suggestions.innerHTML = '';
                    suggestions.style.display = 'block';

                    if (query.length === 0) {
                        suggestions.style.display = 'none';
                        return;
                    }

                    const filteredOptions = options.filter(option =>
                        option.text.toLowerCase().includes(query)
                    );

                    filteredOptions.forEach(option => {
                        const div = document.createElement('div');
                        div.className = 'suggestion-item';
                        div.textContent = option.text;
                        div.dataset.value = option.value;
                        div.addEventListener('click', function () {
                            searchInput.value = this.textContent;
                            userSelect.value = this.dataset.value;
                            suggestions.style.display = 'none';
                        });
                        suggestions.appendChild(div);
                    });

                    document.addEventListener('click', function (event) {
                        if (!suggestions.contains(event.target) && event.target !== searchInput) {
                            suggestions.style.display = 'none';
                        }
                    });
                });

                // Submit form khi nhấn Enter
                searchInput.addEventListener('keypress', function (event) {
                    if (event.key === 'Enter' && userSelect.value) {
                        form.submit();
                    }
                });

                // Chọn nhân viên từ danh sách
                function selectUser(userId, fullName) {
                    searchInput.value = fullName;
                    userSelect.value = userId;
                    form.submit();
                }
            });
        </script>
    </body>
</html>