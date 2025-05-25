package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBContext {

    private final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=chamcongnhanhsu";
    private final String DB_USER = "sa";
    private final String DB_PASSWORD = "123456";

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, "❌ Không tìm thấy driver SQL Server!", ex);
        }
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, "❌ Kết nối CSDL thất bại!", ex);
            return null;
        }
    }

    // Dùng để test kết nối
    public static void main(String[] args) {
        DBContext db = new DBContext();
        Connection conn = db.getConnection();
        if (conn != null) {
            System.out.println("✅ Kết nối thành công đến cơ sở dữ liệu!");
        } else {
            System.out.println("❌ Kết nối thất bại!");
        }
    }
}
