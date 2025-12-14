import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/MyHashMap";
    private static final String USER = "root";
    private static final String PASSWORD = "poorana@zs27";
    private static Connection connection;

    public static Connection makeConnection() throws SQLException {

        if(connection == null){
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);

            } catch (SQLException e){
                System.out.println("Connection Not Created!");
                System.out.println(e.getStackTrace());
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("Non SQL Error!");
                System.out.println(e.getStackTrace());
                e.printStackTrace();
            } finally {
                if (connection != null){
                    connection.close();
                }
            }
        }
        return  connection;
    }
}
