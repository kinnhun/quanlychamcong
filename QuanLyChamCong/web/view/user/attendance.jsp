<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Chấm công</title>
    <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
    <style>
        .camera-box {
            display: flex;
            gap: 20px;
            flex-wrap: wrap;
        }

        .camera-box video,
        .camera-box canvas,
        .camera-box img {
            border: 2px solid #ccc;
            border-radius: 6px;
            width: 320px;
            height: 240px;
            object-fit: cover;
        }

        .attendance-card {
            background-color: #f3f8ff;
            border: 1px solid #bee3f8;
            border-radius: 10px;
            padding: 15px;
        }

        .attendance-card h5 {
            color: #1a202c;
            margin-bottom: 10px;
        }

        .attendance-card p {
            margin: 5px 0;
        }

        .preview-label {
            font-weight: bold;
            margin-top: 10px;
            display: block;
        }
    </style>
</head>
<body>
<div id="main-wrapper" data-theme="light" data-layout="vertical" data-navbarbg="skin6"
     data-sidebartype="full" data-sidebar-position="fixed" data-header-position="fixed" data-boxed-layout="full">

    <c:import url="/view/compomnt/header.jsp" />
    <c:import url="/view/compomnt/siderbar.jsp" />

    <div class="page-wrapper">
        <div class="container-fluid">
            <c:import url="/view/compomnt/notification.jsp" />

            <h3 class="mb-4">Chấm công bằng webcam</h3>

            <!-- Hiển thị chấm công hôm nay -->
            <c:if test="${not empty attendance}">
                <div class="attendance-card mb-4">
                    <h5>⏱️ Thông tin chấm công hôm nay: <fmt:formatDate value="${attendance.date}" pattern="dd/MM/yyyy"/></h5>
                    <p><strong>Check-in:</strong>
                        <c:choose>
                            <c:when test="${not empty attendance.checkinTime}">
                                <fmt:formatDate value="${attendance.checkinTime}" pattern="HH:mm:ss" />
                            </c:when>
                            <c:otherwise><em>Chưa chấm</em></c:otherwise>
                        </c:choose>
                    </p>
                    <p><strong>Check-out:</strong>
                        <c:choose>
                            <c:when test="${not empty attendance.checkoutTime}">
                                <fmt:formatDate value="${attendance.checkoutTime}" pattern="HH:mm:ss" />
                            </c:when>
                            <c:otherwise><em>Chưa chấm</em></c:otherwise>
                        </c:choose>
                    </p>
                    <c:if test="${not empty attendance.checkinImageUrl}">
                        <p>Ảnh Check-in:</p>
                        <img src="${pageContext.request.contextPath}/uploads/${attendance.checkinImageUrl}" width="200" class="mb-3"/>
                    </c:if>
                    <c:if test="${not empty attendance.checkoutImageUrl}">
                        <p>Ảnh Check-out:</p>
                        <img src="${pageContext.request.contextPath}/uploads/${attendance.checkoutImageUrl}" width="200"/>
                    </c:if>
                </div>
            </c:if>

            <!-- Form chấm công -->
            <form method="post" action="${pageContext.request.contextPath}/attendance" onsubmit="return submitImage();">
                <input type="hidden" name="action" id="actionType" value="checkin" />
                <input type="hidden" name="imageBase64" id="imageBase64" />

                <div class="camera-box mb-3">
                    <!-- Webcam view -->
                    <div>
                        <label class="preview-label">📷 Webcam</label>
                        <video id="video" autoplay></video>
                    </div>

                    <!-- Ảnh chụp preview -->
                    <div>
                        <label class="preview-label">🖼️ Ảnh vừa chụp</label>
                        <canvas id="canvas" style="display:none;"></canvas>
                        <img id="preview" src="#" alt="Preview" style="display:none;" />
                    </div>
                </div>

                <!-- Buttons -->
                <div class="mb-3">
                    <button type="button" class="btn btn-outline-secondary" onclick="capture()">📸 Chụp ảnh</button>
                    <button type="submit" class="btn btn-success" onclick="setAction('checkin')">✅ Check-in</button>
                    <button type="submit" class="btn btn-primary" onclick="setAction('checkout')">✅ Check-out</button>
                </div>
            </form>
        </div>
    </div>

    <c:import url="/view/compomnt/footer.jsp" />
</div>

<!-- JavaScript xử lý webcam -->
<script>
    const video = document.getElementById('video');
    const canvas = document.getElementById('canvas');
    const imageInput = document.getElementById('imageBase64');
    const preview = document.getElementById('preview');

    navigator.mediaDevices.getUserMedia({ video: true })
        .then(stream => video.srcObject = stream)
        .catch(err => alert("Không thể truy cập webcam: " + err));

    function capture() {
        const context = canvas.getContext('2d');
        canvas.style.display = 'block';
        preview.style.display = 'block';

        context.drawImage(video, 0, 0, canvas.width, canvas.height);
        const dataURL = canvas.toDataURL("image/png");
        imageInput.value = dataURL;
        preview.src = dataURL;
        alert("✅ Ảnh đã được chụp!");
    }

    function setAction(action) {
        document.getElementById("actionType").value = action;
    }

    function submitImage() {
        if (!imageInput.value) {
            alert("⚠️ Bạn cần chụp ảnh trước khi chấm công.");
            return false;
        }
        return true;
    }
</script>
</body>
</html>
