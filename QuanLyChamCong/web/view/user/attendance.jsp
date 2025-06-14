<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chấm công bằng webcam</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
    <style>
        body {
            background: #f0f2f5;
            font-family: 'Segoe UI', sans-serif;
            margin: 0;
            width: 100%
        }

        .page-wrapper {
            padding: 40px 20px;
            max-width: 1400px;
            margin: auto;
        }

        .title {
            text-align: center;
            font-size: 30px;
            font-weight: 700;
            color: #1e2a44;
            margin-bottom: 30px;
            text-transform: uppercase;
            position: relative;
        }

        .title::after {
            content: '';
            width: 80px;
            height: 4px;
            background: #007bff;
            position: absolute;
            bottom: -10px;
            left: 50%;
            transform: translateX(-50%);
        }

        .clock {
            font-size: 42px;
            font-weight: bold;
            color: #dc3545;
            text-align: center;
            margin-bottom: 40px;
        }

        .webcam-section {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 40px;
            align-items: start;
            justify-content: center;
        }

        .webcam-box,
        .preview-box,
        .attendance-info-box {
            background: #fff;
            padding: 20px;
            border-radius: 12px;
            box-shadow: 0 0 15px rgba(0,0,0,0.05);
            text-align: center;
        }

        video, canvas, img {
            width: 100%;
            height: auto;
            border-radius: 10px;
            border: 3px solid #dee2e6;
            background-color: #f8f9fa;
        }

        .preview-label {
            font-weight: 600;
            margin-bottom: 10px;
            display: block;
            color: #333;
            text-transform: uppercase;
        }

        .attendance-info-box h5 {
            font-size: 20px;
            margin-bottom: 15px;
            color: #007bff;
        }

        .attendance-info-box p {
            text-align: left;
            margin: 6px 0;
            font-size: 15px;
        }

        .attendance-info-box img {
            margin-top: 10px;
            width: 100%;
            border-radius: 8px;
        }

        .btn-section {
            text-align: center;
            margin-top: 30px;
        }

        .btn-section button {
            margin: 0 10px;
            padding: 12px 25px;
            font-size: 16px;
            border-radius: 8px;
            font-weight: bold;
        }

        @media (max-width: 992px) {
            .webcam-section {
                grid-template-columns: 1fr;
            }

            .attendance-info-box {
                text-align: center;
            }
        }
    </style>
</head>
<body>
<div id="main-wrapper" data-theme="light" data-layout="vertical" data-navbarbg="skin6"
     data-sidebartype="full" data-sidebar-position="fixed" data-header-position="fixed" data-boxed-layout="full">

    <c:import url="/view/compomnt/header.jsp"/>
    <c:import url="/view/compomnt/siderbar.jsp"/>

    <div class="page-wrapper" >
        <div class="container-fluid" >
            <c:import url="/view/compomnt/notification.jsp"/>

            <div class="title">📸 Chấm công bằng webcam</div>
            <div id="clock" class="clock">--:--:--</div>

            <!-- Khối webcam + preview + thông tin chấm công -->
            <div class="webcam-section">
                <!-- Webcam -->
                <div class="webcam-box">
                    <label class="preview-label">🎥 Camera</label>
                    <video id="video" autoplay muted playsinline></video>
                </div>

                <!-- Preview -->
                <div class="preview-box">
                    <label class="preview-label">🖼️ Ảnh vừa chụp</label>
                    <canvas id="canvas" width="320" height="240" style="display:none;"></canvas>
                    <img id="preview" src="#" alt="Preview" style="display:none;" />
                </div>

                <!-- Thông tin hôm nay -->
                <c:if test="${not empty attendance}">
                    <div class="attendance-info-box">
                        <h5>Thông tin chấm công hôm nay</h5>
                        <p><strong>Ngày:</strong> <fmt:formatDate value="${attendance.date}" pattern="dd/MM/yyyy"/></p>
                        <p><strong>Check-in:</strong>
                            <c:choose>
                                <c:when test="${not empty attendance.checkinTime}">
                                    <fmt:formatDate value="${attendance.checkinTime}" pattern="HH:mm:ss"/>
                                </c:when>
                                <c:otherwise><em>Chưa chấm</em></c:otherwise>
                            </c:choose>
                        </p>
                        <p><strong>Check-out:</strong>
                            <c:choose>
                                <c:when test="${not empty attendance.checkoutTime}">
                                    <fmt:formatDate value="${attendance.checkoutTime}" pattern="HH:mm:ss"/>
                                </c:when>
                                <c:otherwise><em>Chưa chấm</em></c:otherwise>
                            </c:choose>
                        </p>
                        <c:if test="${not empty attendance.checkinImageUrl}">
                            <p>Ảnh Check-in:</p>
                            <img src="${pageContext.request.contextPath}/upload/${attendance.checkinImageUrl}"/>
                        </c:if>
                        <c:if test="${not empty attendance.checkoutImageUrl}">
                            <p>Ảnh Check-out:</p>
                            <img src="${pageContext.request.contextPath}/upload/${attendance.checkoutImageUrl}"/>
                        </c:if>
                    </div>
                </c:if>
            </div>

            <!-- Form -->
            <form method="post" action="${pageContext.request.contextPath}/attendance" onsubmit="return submitImage();">
                <input type="hidden" name="action" id="actionType" value="checkin"/>
                <input type="hidden" name="imageBase64" id="imageBase64"/>

                <div class="btn-section">
                    <button type="button" class="btn btn-outline-secondary" onclick="capture()">📸 Chụp ảnh</button>

                    <c:if test="${empty attendance.checkinTime}">
                        <button type="submit" class="btn btn-warning text-white" onclick="setAction('checkin')">✅ Check-in</button>
                    </c:if>

                    <c:if test="${not empty attendance.checkinTime && empty attendance.checkoutTime}">
                        <button type="submit" class="btn btn-primary" onclick="setAction('checkout')">✅ Check-out</button>
                    </c:if>
                </div>
            </form>
        </div>
    </div>

    <c:import url="/view/compomnt/footer.jsp"/>
</div>

<script>
    const clock = document.getElementById("clock");
    setInterval(() => {
        const now = new Date();
        clock.innerText = now.toLocaleTimeString('vi-VN', { hour12: false });
    }, 1000);

    const canvas = document.getElementById('canvas');
    const imageInput = document.getElementById('imageBase64');
    const preview = document.getElementById('preview');
    const video = document.getElementById('video');

    navigator.mediaDevices.getUserMedia({ video: true })
        .then(stream => video.srcObject = stream)
        .catch(err => alert("Không thể truy cập webcam: " + err));

    function capture() {
        const context = canvas.getContext("2d");
        context.drawImage(video, 0, 0, canvas.width, canvas.height);
        const dataURL = canvas.toDataURL("image/png");
        imageInput.value = dataURL;
        preview.src = dataURL;
        preview.style.display = "block";
        alert("✅ Ảnh đã được chụp!");
    }

    function setAction(action) {
        document.getElementById("actionType").value = action;
    }

    function submitImage() {
        if (!imageInput.value || imageInput.value.trim() === '') {
            alert("⚠️ Bạn cần chụp ảnh trước khi chấm công.");
            return false;
        }
        return true;
    }
</script>
</body>
</html>
