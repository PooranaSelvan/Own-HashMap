import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/MyHashMap";
    private static final String USER = "root";
    private static final String PASSWORD = "poorana@zs27";
    private static Connection connection;

    public static Connection makeConnection() throws SQLException {

        Logger logger = LogManager.getLogger("DBConnection");

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        logger.info("Connected to Database!");
        return connection;

    }
}
