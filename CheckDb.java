import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CheckDB {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/secondhand_book?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC";
            String username = "root";
            String password = "123456";
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();
            
            System.out.println("Databases:");
            ResultSet rs = stmt.executeQuery("SHOW DATABASES");
            while (rs.next()) {
                System.out.println("- " + rs.getString(1));
            }
            rs.close();
            
            System.out.println("\nTables in secondhand_book database:");
            rs = stmt.executeQuery("SHOW TABLES");
            while (rs.next()) {
                System.out.println("- " + rs.getString(1));
            }
            rs.close();
            
            System.out.println("\nBooks table structure:");
            rs = stmt.executeQuery("DESCRIBE books");
            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6));
            }
            rs.close();
            
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
