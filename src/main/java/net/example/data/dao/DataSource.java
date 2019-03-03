package net.example.data.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DataSource {

    private final String userName;
    private final String password;
    private final String url;

    public DataSource() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String initBdScript = new Scanner(getClass().getClassLoader().getResourceAsStream("sql/init_database.sql")).useDelimiter("\\A").next();
        userName = "postgres";
        password = "root";
        url = "jdbc:postgresql://localhost/postgres";

        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(initBdScript);
        ) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, userName, password);
    }
}
