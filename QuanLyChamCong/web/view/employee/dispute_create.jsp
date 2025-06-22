<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gửi Khiếu Nại Chấm Công</title>
    <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
    <style>
        body {
            background: #e8eef5;
        }
        .dispute-form-card {
            max-width: 630px;
            min-height: 520px;
            margin: 42px auto 40px auto;
            background: linear-gradient(120deg, #f8fbff 70%, #e5eeff 100%);
            border-radius: 20px;
            box-shadow: 0 8px 36px 0 rgba(41,127,255,0.12), 0 1.5px 12px 0 rgba(40,70,130,0.08);
            padding: 46px 38px 34px 38px;
            display: flex;
            flex-direction: column;
            justify-content: center;
        }
        .dispute-form-card h2 {
            margin-bottom: 32px;
            text-align: center;
            color: #297fff;
            letter-spacing: 1px;
            font-size: 2rem;
        }
        .dispute-form-card label {
            font-weight: 600;
            margin-bottom: 7px;
            display: block;
            color: #204887;
        }
        .dispute-form-card input,
        .dispute-form-card select,
        .dispute-form-card textarea {
            width: 100%;
            margin-bottom: 24px;
            border: 1px solid #b6cae0;
            border-radius: 10px;
            padding: 11px 14px;
            font-size: 1.07rem;
            background: #f9fbff;
            transition: border 0.18s;
            box-sizing: border-box;
        }
        .dispute-form-card input:focus,
        .dispute-form-card select:focus,
        .dispute-form-card textarea:focus {
            border: 1.5px solid #297fff;
            background: #f4f9ff;
            outline: none;
        }
        .dispute-form-card textarea {
            min-height: 90px;
            max-height: 220px;
            resize: vertical;
        }
        .dispute-form-card button {
            width: 100%;
            padding: 16px 0;
            border: none;
            border-radius: 10px;
            background: linear-gradient(90deg, #297fff 80%, #203e78 100%);
            color: #fff;
            font-weight: 700;
            font-size: 1.16rem;
            letter-spacing: 1.1px;
            box-shadow: 0 2px 16px 0 rgba(41,127,255,0.10);
            cursor: pointer;
            transition: background 0.22s;
            margin-top: 10px;
        }
        .dispute-form-card button:hover {
            background: linear-gradient(90deg, #174eb5 70%, #142d51 100%);
        }
        .msg-success { color: #29904d; text-align: center; margin-bottom: 20px;}
        .msg-error { color: #d63447; text-align: center; margin-bottom: 20px;}
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

                <!-- Form khiếu nại chấm công -->
                <div class="dispute-form-card">
                    <h2>Gửi Khiếu Nại Chấm Công</h2>

                    <c:if test="${not empty message}">
                        <div class="msg-success">${message}</div>
                    </c:if>
                    <c:if test="${not empty error}">
                        <div class="msg-error">${error}</div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/employee/disputes" method="post" enctype="multipart/form-data">
                        <label for="attendance_id">Ngày chấm công</label>
                        <input type="date" name="attendance_id" id="attendance_id" required />

                        <label for="issue_type">Loại vấn đề</label>
                        <select name="issue_type" id="issue_type" required>
                            <option value="" hidden>Chọn loại vấn đề</option>
                            <option value="Quên chấm công">Quên chấm công</option>
                            <option value="Đi muộn">Đi muộn</option>
                            <option value="Về sớm">Về sớm</option>
                            <option value="Thiết bị lỗi">Thiết bị lỗi</option>
                            <option value="Khác">Khác</option>
                        </select>

                        <label for="reason">Lý do khiếu nại</label>
                        <textarea name="reason" id="reason" placeholder="Nhập lý do cụ thể..." required></textarea>

                        <label for="attachment">Đính kèm file (ảnh, PDF...)</label>
                        <input type="file" name="attachment" id="attachment" accept=".jpg,.jpeg,.png,.pdf" />

                        <button type="submit">Gửi khiếu nại</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Import footer -->
    <c:import url="/view/compomnt/footer.jsp" />
</body>
</html>
