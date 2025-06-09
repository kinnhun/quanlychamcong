<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Danh sách tài khoản</title>
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
                    <!-- Thông báo -->
                    <c:import url="/view/compomnt/notification.jsp" />







                    <form action="${pageContext.request.contextPath}/update-profile" method="post" class="mx-auto" style="max-width: 700px;">
                        <div class="mb-3">
                            <label class="form-label fw-bold">Họ và tên</label>
                            <input type="text" name="fullName" class="form-control form-control-lg" value="${sessionScope.user.fullName}" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Email</label>
                            <input type="email" name="email" class="form-control form-control-lg" value="${sessionScope.user.email}" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Tên đăng nhập</label>
                            <input type="text" class="form-control form-control-lg" value="${sessionScope.user.username}" readonly>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Số điện thoại</label>
                            <input type="text" name="phone" class="form-control form-control-lg" value="${sessionScope.user.phone}" required>
                        </div>

                        <div class="text-center mt-4">
                            <a href="${pageContext.request.contextPath}/profile" class="btn btn-secondary px-4 py-2 me-2">Hủy</a>
                            <button type="submit" class="btn btn-success px-4 py-2">Lưu thay đổi</button>
                        </div>
                    </form>








                </div>   

            </div>   
        </div>

        <!-- Import footer -->
        <c:import url="/view/compomnt/footer.jsp" />


    </div>
</body>
</html>
