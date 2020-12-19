import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Author: YHQ
 * @Date: 2020/8/29 15:38
 */
public class DatabaseTest {
    public static void main(String args[]) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/yun",
                "root",
                "root");
        System.out.println(connection.createStatement());
    }
}
