import java.sql.Connection;
import java.sql.DriverManager;

public class DbFunctions {
    public Connection connect_to_db(String dbName, String user, String pass) {
        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbName,user,pass);

            if (connection != null) {
                System.out.println("Connection Established!");
            }
            else {
                System.out.println("Connection Failed!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return connection;
    }
}
