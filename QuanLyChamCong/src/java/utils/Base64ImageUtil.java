package utils;

import jakarta.servlet.ServletContext;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;

public class Base64ImageUtil {

    public static String saveImage(String base64, String filenamePrefix, ServletContext context) {
        try {
            if (base64 == null || !base64.contains(",")) return null;

            String base64Image = base64.split(",")[1];
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);

            String fileName = filenamePrefix + "_" + System.currentTimeMillis() + ".png";

            // ✅ Đường dẫn tới thư mục uploads bên trong dự án web
            String uploadFolderPath = context.getRealPath("/uploads");
            java.io.File folder = new java.io.File(uploadFolderPath);
            if (!folder.exists()) {
                folder.mkdirs(); // tạo thư mục nếu chưa có
            }

            String savePath = uploadFolderPath + "/" + fileName;

            try (OutputStream os = new FileOutputStream(savePath)) {
                os.write(imageBytes);
            }

            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
