import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CheckDatabase {
    public static void main(String[] args) {
        try {
            // 加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 连接数据库
            String url = "jdbc:mysql://localhost:3306/secondhand_book?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC";
            String username = "root";
            String password = "123456";
            Connection conn = DriverManager.getConnection(url, username, password);
            
            // 创建语句
            Statement stmt = conn.createStatement();
            
            // 显示所有数据库
            System.out.println("Databases:");
            ResultSet rs = stmt.executeQuery("SHOW DATABASES");
            while (rs.next()) {
                System.out.println("- " + rs.getString(1));
            }
            rs.close();
            
            // 显示 secondhand_book 数据库中的所有表
            System.out.println("\nTables in secondhand_book database:");
            rs = stmt.executeQuery("SHOW TABLES");
            while (rs.next()) {
                System.out.println("- " + rs.getString(1));
            }
            rs.close();
            
            // 显示 books 表的结构
            System.out.println("\nBooks table structure:");
            rs = stmt.executeQuery("DESCRIBE books");
            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6));
            }
            rs.close();
            
            // 关闭连接
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
