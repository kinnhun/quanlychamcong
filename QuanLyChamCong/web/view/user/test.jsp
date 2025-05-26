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
        .modal {
            display: none;
            background: rgba(0,0,0,0.5);
            position: fixed;
            top: 0; left: 0; width: 100%; height: 100%;
            z-index: 1000;
            justify-content: center;
            align-items: center;
            padding: 1rem;
            box-sizing: border-box;
        }
        .modal-content {
            background: white;
            padding: 20px;
            border-radius: 10px;
            max-width: 90vw;
            width: 100%;
            max-height: 90vh;
            overflow-y: auto;
            box-sizing: border-box;
            text-align: center;
        }
        .modal-content img {
            width: 100%;
            height: auto;
            border: 1px solid #ccc;
            margin-bottom: 15px;
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
            <form method="post" action="${pageContext.request.contextPath}/attendance" onsubmit="return false;">
                <input type="hidden" name="action" id="actionType" />
                <input type="hidden" name="imageBase64" id="imageBase64" />

                <div class="camera-box mb-3">
                    <div>
                        <video id="video" autoplay></video>
                    </div>
                    <div>
                        <canvas id="canvas" style="display:none;"></canvas>
                        <img id="preview" src="#" alt="Preview" style="display:none;" />
                    </div>
                </div>

                <div class="mb-3">
                    <button type="button" class="btn btn-outline-secondary" onclick="capture()">📸 Chụp ảnh</button>
                </div>
            </form>
        </div>
    </div>

    <c:import url="/view/compomnt/footer.jsp" />
</div>

<!-- Modal xác nhận -->
<div class="modal" id="confirmModal">
    <div class="modal-content">
        <h5>Xác nhận chấm công</h5>
        <img id="modalPreview" src="#" alt="Preview"/>
        <div class="mb-2">
            <button class="btn btn-success" onclick="submitAction('checkin')"
                    <c:if test="${not empty attendance && attendance.checkinTime != null}">disabled</c:if>>
                ✅ Check-in
            </button>
            <button class="btn btn-primary" onclick="submitAction('checkout')"
                    <c:if test="${not empty attendance && attendance.checkoutTime != null}">disabled</c:if>>
                ✅ Check-out
            </button>
        </div>
        <button class="btn btn-link" onclick="closeModal()">Huỷ</button>
    </div>
</div>

<!-- JavaScript xử lý webcam -->
<script>
    const video = document.getElementById('video');
    const canvas = document.getElementById('canvas');
    const imageInput = document.getElementById('imageBase64');
    const preview = document.getElementById('preview');
    const modal = document.getElementById('confirmModal');
    const modalPreview = document.getElementById('modalPreview');

    navigator.mediaDevices.getUserMedia({ video: true })
        .then(stream => video.srcObject = stream)
        .catch(err => alert("Không thể truy cập webcam: " + err));

    function capture() {
        const context = canvas.getContext('2d');
        canvas.width = video.videoWidth;
        canvas.height = video.videoHeight;
        context.drawImage(video, 0, 0, canvas.width, canvas.height);
        const dataURL = canvas.toDataURL("image/png");

        imageInput.value = dataURL;
        preview.src = dataURL;
        preview.style.display = 'block';

        modalPreview.src = dataURL;
        modal.style.display = 'flex';
    }

    function closeModal() {
        modal.style.display = 'none';
    }

    function submitAction(actionType) {
        document.getElementById("actionType").value = actionType;
        document.forms[0].submit();
        closeModal();
    }
</script>
</body>
</html>
