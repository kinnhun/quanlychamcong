<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Thêm địa điểm mới</title>
        <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">

        <!-- Leaflet + Geocoder -->
        <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" />
        <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>

        <link rel="stylesheet" href="https://unpkg.com/leaflet-control-geocoder/dist/Control.Geocoder.css" />
        <script src="https://unpkg.com/leaflet-control-geocoder/dist/Control.Geocoder.js"></script>

        <style>
            #map {
                width: 100%;
                height: 450px;
                border-radius: 10px;
                margin-bottom: 20px;
            }
        </style>
    </head>
    <body>

        <div id="main-wrapper" data-theme="light" data-layout="vertical" data-navbarbg="skin6"
             data-sidebartype="full" data-sidebar-position="fixed" data-header-position="fixed" data-boxed-layout="full">

            <c:import url="/view/compomnt/header.jsp"/>
            <c:import url="/view/compomnt/siderbar.jsp"/>

            <div class="page-wrapper">
                <div class="container-fluid">
                    <c:import url="/view/compomnt/notification.jsp"/>

                    <div class="card">
                        <div class="card-body">
                            <h4 class="card-title mb-4">Thêm địa điểm mới</h4>

                            <form action="${pageContext.request.contextPath}/admin/location-add" method="post">
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Tên địa điểm</label>
                                            <input type="text" name="name" class="form-control" required>
                                        </div>
                                        <div class="form-group">
                                            <label>Địa chỉ</label>
                                            <textarea name="address" class="form-control" rows="3" required></textarea>
                                        </div>
                                        <div class="form-group">
                                            <label>Tọa độ (Lat,Lng)</label>
                                            <input type="text" name="ipMap" id="ipMap" class="form-control" required readonly>
                                        </div>
                                    </div>

                                    <div class="col-md-6">
                                        <label>Bản đồ</label>
                                        <div id="map"></div>

                                        <div class="form-group">
                                            <label>Trạng thái</label>
                                            <select name="isActive" class="form-control">
                                                <option value="1">Kích hoạt</option>
                                                <option value="0">Không hoạt động</option>
                                            </select>
                                        </div>
                                        <div class="form-group text-end mt-4">
                                            <button type="submit" class="btn btn-primary">Thêm địa điểm</button>
                                        </div>
                                    </div>
                                </div>
                            </form>

                        </div>
                    </div>
                </div>
            </div>

            <c:import url="/view/compomnt/footer.jsp"/>
        </div>

        <script>
            let map = L.map('map').setView([21.0285, 105.8542], 13);
            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                attribution: '&copy; OpenStreetMap contributors'
            }).addTo(map);

            let marker;

            // Thêm chức năng tìm kiếm
            const geocoder = L.Control.geocoder({
                defaultMarkGeocode: false,
                placeholder: "Tìm địa điểm..."
            })
                    .on('markgeocode', function (e) {
                        const center = e.geocode.center;
                        const name = e.geocode.name;
                        const lat = center.lat.toFixed(6);
                        const lng = center.lng.toFixed(6);
                        const latlng = lat + ',' + lng;
                        const safeName = name.replace(/'/g, "\\'");

                        if (marker) {
                            marker.setLatLng(center);
                        } else {
                            marker = L.marker(center).addTo(map);
                        }

                        const popupHtml =
                                '<b>' + name + '</b><br/>' +
                                'Tọa độ: ' + latlng + '<br/>' +
                                '<button class="btn btn-sm btn-primary mt-2" onclick="selectLocation(\'' + safeName + '\', \'' + latlng + '\')">' +
                                'Chọn địa điểm này' +
                                '</button>';
                        marker.bindPopup(popupHtml).openPopup();

                        map.setView(center, 17);
                    })
                    .addTo(map);

            // Xử lý click bản đồ
            map.on('click', function (e) {
                const lat = e.latlng.lat.toFixed(6);
                const lng = e.latlng.lng.toFixed(6);
                const latlng = lat + ',' + lng;

                if (marker) {
                    marker.setLatLng(e.latlng);
                } else {
                    marker = L.marker(e.latlng).addTo(map);
                }

                fetch(`https://nominatim.openstreetmap.org/reverse?lat=${lat}&lon=${lng}&format=json&accept-language=vi`)
                        .then(res => res.json())
                        .then(data => {
                            const displayName = data.display_name || `Địa điểm tại ${latlng}`;
                            const safeDisplayName = displayName.replace(/'/g, "\\'");
                            const popupHtml =
                                    '<b>' + displayName + '</b><br/>' +
                                    'Tọa độ: ' + latlng + '<br/>' +
                                    '<button class="btn btn-sm btn-primary mt-2" onclick="selectLocation(\'' + safeDisplayName + '\', \'' + latlng + '\')">' +
                                    'Chọn địa điểm này' +
                                    '</button>';
                            marker.bindPopup(popupHtml).openPopup();
                        })
                        .catch(err => {
                            const popupHtml =
                                    '<b>Địa điểm tại ' + latlng + '</b><br/>' +
                                    '<button class="btn btn-sm btn-primary mt-2" onclick="selectLocation(\'Địa điểm tại ' + latlng + '\', \'' + latlng + '\')">' +
                                    'Chọn địa điểm này' +
                                    '</button>';
                            marker.bindPopup(popupHtml).openPopup();
                        });
            });

            function selectLocation(name, latlng) {
                document.querySelector('input[name="name"]').value = name;
                document.querySelector('textarea[name="address"]').value = name;
                document.getElementById('ipMap').value = latlng;
                if (marker)
                    marker.closePopup();
            }
        </script>

    </body>
</html>
