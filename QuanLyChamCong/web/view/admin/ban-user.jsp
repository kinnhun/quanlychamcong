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

                
                
                
                
                
                
                
                <div class="container mt-5">
    <h3>Ban tài khoản</h3>
    <form action="${pageContext.request.contextPath}/admin/user-ban" method="post">
        <input type="hidden" name="userId" value="${param.userId}" />
        <div class="form-group">
            <label>Lý do ban</label>
            <textarea name="banReason" class="form-control" rows="4" required></textarea>
        </div>
        <button type="submit" class="btn btn-danger">Xác nhận Ban</button>
        <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-secondary">Hủy</a>
    </form>
</div>
                
                
                
                
                
                
                
                       </div>   

            </div>   
        </div>

        <!-- Import footer -->
        <c:import url="/view/compomnt/footer.jsp" />


    </div>
</body>
</html>
