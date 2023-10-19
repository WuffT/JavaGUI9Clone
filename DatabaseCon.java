import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseCon {
    private static final String DB_URL = "jdbc:sqlserver://WuffTPC\\SQLEXPRESS;DatabaseName=Northwind;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "2002JT";

    
    //THIS METHOD HANDLES THE CONNECTION
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
    
    //THIS METHOD HANDLES THE USER LOGIN
    public static User getUser(String username, String password) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE username = ? AND password = ?")) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getString("role")
                    );
                }
            }
        }
        return null;
    }
    
    
}
