<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sửa địa điểm</title>
    <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
</head>
<body>
<div id="main-wrapper" data-theme="light" data-layout="vertical" data-navbarbg="skin6"
     data-sidebartype="full" data-sidebar-position="fixed" data-header-position="fixed" data-boxed-layout="full">

    <!-- Header -->
    <c:import url="/view/compomnt/header.jsp"/>

    <!-- Sidebar -->
    <c:import url="/view/compomnt/siderbar.jsp"/>

    <div class="page-wrapper">
        <div class="container-fluid">
            <!-- Notification -->
            <c:import url="/view/compomnt/notification.jsp"/>

            <div class="card">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <h4 class="card-title mb-0">Sửa địa điểm</h4>
                        <a href="${pageContext.request.contextPath}/admin/locations" class="btn btn-secondary">⬅ Quay lại</a>
                    </div>

                    <form action="${pageContext.request.contextPath}/admin/location-edit" method="post">
                        <input type="hidden" name="id" value="${location.id}"/>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Tên địa điểm</label>
                                    <input type="text" name="name" class="form-control" value="${location.name}" required/>
                                </div>
                                <div class="form-group">
                                    <label>Địa chỉ</label>
                                    <input type="text" name="address" class="form-control" value="${location.address}" required/>
                                </div>
                            </div>

                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>IP Map</label>
                                    <input type="text" name="ipMap" class="form-control" value="${location.ipMap}" />
                                </div>
                                <div class="form-group">
                                    <label>Trạng thái</label>
                                    <select name="isActive" class="form-control">
                                        <option value="true" ${location.isActive ? "selected" : ""}>Hoạt động</option>
                                        <option value="false" ${!location.isActive ? "selected" : ""}>Không hoạt động</option>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <button type="submit" class="btn btn-primary">Cập nhật</button>
                    </form>
                </div>
            </div>

        </div>
    </div>

    <!-- Footer -->
    <c:import url="/view/compomnt/footer.jsp"/>
</div>
</body>
</html>
