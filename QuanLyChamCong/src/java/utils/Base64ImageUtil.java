package utils;

import jakarta.servlet.ServletContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;

public class Base64ImageUtil {

    public static String saveImage(String base64Data, String fileNameWithoutExt, ServletContext context) {
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

            // Đường dẫn file tuyệt đối: /upload/123_checkin.png
            String fileName = fileNameWithoutExt + ".png";
            File file = new File(uploadDir, fileName);

            try (OutputStream os = new FileOutputStream(file)) {
                os.write(imageBytes);
            }

            // Trả về tên file để dùng trong JSP: upload/123_checkin.png
            return fileName;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
