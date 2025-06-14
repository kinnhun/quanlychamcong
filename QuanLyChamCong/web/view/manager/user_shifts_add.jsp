<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Phân ca cho nhân viên</title>
    <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
    <style>
        .form-section { max-width: 650px; margin: 36px auto; background: #fff; border-radius: 12px; box-shadow: 0 0 16px #eee; padding: 32px;}
        label { font-weight: 600; margin-bottom: 5px; }
        select, input[type="date"], textarea { width: 100%; padding: 8px 10px; border-radius: 8px; border: 1px solid #ddd; margin-bottom: 18px;}
        .btn-primary { padding: 10px 24px; font-weight: bold; }
    </style>
</head>
<body>
<div id="main-wrapper" data-theme="light" data-layout="vertical" data-navbarbg="skin6" data-sidebartype="full"
     data-sidebar-position="fixed" data-header-position="fixed" data-boxed-layout="full">

    <c:import url="/view/compomnt/header.jsp"/>
    <c:import url="/view/compomnt/siderbar.jsp"/>

    <div class="page-wrapper">
        <div class="container-fluid">
            <div class="form-section">
                <h3 class="mb-4 text-primary fw-bold">➕ Phân ca cho nhân viên</h3>
                <form method="post" action="${pageContext.request.contextPath}/manager/user-shifts-add">
                    <div class="mb-3">
                        <label>Nhân viên <span class="text-danger">*</span></label>
                        <select name="userId" required>
                            <option value="">-- Chọn nhân viên --</option>
                            <c:forEach var="u" items="${employeeList}">
                                <option value="${u.userId}">${u.fullName} (${u.username})</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label>Ca làm <span class="text-danger">*</span></label>
                        <select name="shiftId" required>
                            <option value="">-- Chọn ca làm --</option>
                            <c:forEach var="s" items="${shiftList}">
                                <option value="${s.shiftId}">${s.shiftName} (${s.startTime} - ${s.endTime})</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label>Ngày <span class="text-danger">*</span></label>
                        <input type="date" name="date" required min="<fmt:formatDate value='${now}' pattern='yyyy-MM-dd'/>"/>
                    </div>
                    <div class="mb-3">
                        <label>Chi nhánh</label>
                        <select name="locationId">
                            <option value="">-- Không chọn --</option>
                            <c:forEach var="l" items="${locationList}">
                                <option value="${l.id}">${l.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label>Phòng ban</label>
                        <select name="departmentId">
                            <option value="">-- Không chọn --</option>
                            <c:forEach var="d" items="${departmentList}">
                                <option value="${d.departmentId}">${d.departmentName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label>Ghi chú</label>
                        <textarea name="note" rows="2" placeholder="Ghi chú nếu cần..."></textarea>
                    </div>
                    <div class="mt-4">
                        <button type="submit" class="btn btn-primary">💾 Lưu phân ca</button>
                        <a href="${pageContext.request.contextPath}/manager/user-shifts" class="btn btn-secondary ms-2">Quay lại</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <c:import url="/view/compomnt/footer.jsp"/>
</div>
</body>
</html>
