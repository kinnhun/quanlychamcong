<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html dir="ltr">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Login page for employee attendance system">
    <meta name="author" content="">

    <title>Employee Attendance - Login</title>

    <!-- Favicon -->
    <link rel="icon" type="image/png" sizes="16x16"
          href="${pageContext.request.contextPath}/view/lib/assets/images/favicon.png">

    <!-- CSS -->
    <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
</head>

<body>
<div class="main-wrapper">
    <!-- Preloader -->
    <div class="preloader">
        <div class="lds-ripple">
            <div class="lds-pos"></div>
            <div class="lds-pos"></div>
        </div>
    </div>

    <!-- Login Box -->
    <div class="auth-wrapper d-flex no-block justify-content-center align-items-center position-relative"
         style="background:url(${pageContext.request.contextPath}/view/lib/assets/images/big/auth-bg.jpg) no-repeat center center;">
        <div class="auth-box row">
            <div class="col-lg-7 col-md-5 modal-bg-img"
                 style="background-image: url(${pageContext.request.contextPath}/view/lib/assets/images/big/3.jpg);">
            </div>
            <div class="col-lg-5 col-md-7 bg-white">
                <div class="p-3">
                    <div class="text-center">
                        <img src="${pageContext.request.contextPath}/view/lib/assets/images/big/icon.png" alt="logo">
                    </div>
                    <h2 class="mt-3 text-center">Đăng nhập</h2>
                    <p class="text-center">Nhập tài khoản và mật khẩu để tiếp tục</p>

                    <!-- Import thông báo nổi -->
                    <c:import url="/view/compomnt/notification.jsp"/>

                    <!-- Login Form -->
                    <form class="mt-4" action="${pageContext.request.contextPath}/login" method="post">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="form-group">
                                    <label class="text-dark" for="username">Tên đăng nhập</label>
                                    <input class="form-control" id="username" name="username" type="text"
                                           placeholder="Nhập tên đăng nhập" required>
                                </div>
                            </div>

                            <div class="col-lg-12">
                                <div class="form-group">
                                    <label class="text-dark" for="password">Mật khẩu</label>
                                    <input class="form-control" id="password" name="password" type="password"
                                           placeholder="Nhập mật khẩu" required>
                                </div>
                            </div>

                            <div class="col-lg-12 text-center">
                                <button type="submit" class="btn btn-block btn-dark">Đăng nhập</button>
                            </div>

                            <div class="col-lg-12 text-center mt-3">
                                Chưa có tài khoản?
                                <a href="${pageContext.request.contextPath}/view/user/Register.jsp" class="text-primary">Đăng ký</a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- JS Scripts -->
<script src="${pageContext.request.contextPath}/view/lib/assets/libs/jquery/dist/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/view/lib/assets/libs/popper.js/dist/umd/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/view/lib/assets/libs/bootstrap/dist/js/bootstrap.min.js"></script>
<script>
    $(".preloader").fadeOut();
</script>
</body>
</html>
