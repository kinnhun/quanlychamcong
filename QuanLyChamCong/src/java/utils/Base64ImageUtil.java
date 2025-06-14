package utils;

import jakarta.servlet.ServletContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;

public class Base64ImageUtil {

public static String saveImage(String base64Data, int userId, String action, ServletContext context) {
    if (base64Data == null || base64Data.trim().isEmpty()) {
        return null;
    }

    try {
        // Loại bỏ phần header nếu có
        String[] parts = base64Data.split(",");
        String imageData = parts.length > 1 ? parts[1] : parts[0];

        // Giải mã base64 thành byte[]
        byte[] imageBytes = Base64.getDecoder().decode(imageData);

        // Lấy đường dẫn tuyệt đối tới thư mục upload trong webapp
        String uploadPath = context.getRealPath("/upload");

        // Tạo thư mục nếu chưa tồn tại
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Đặt tên file theo format: userId_action_yyyyMMdd_HHmmss.png
        String timeStr = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
        String fileName = userId + "_" + action + "_" + timeStr + ".png";
        File file = new File(uploadDir, fileName);

        try (OutputStream os = new FileOutputStream(file)) {
            os.write(imageBytes);
        }

        // Trả về tên file thôi (chỉ tên, không có đường dẫn upload/)
        return fileName;

    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}


}
