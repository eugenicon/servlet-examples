package net.example.data.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

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

    public <T> List<T> selectQuery(String sql, SqlFunction<T> function) {
        return selectQuery(sql, function, ps -> {});
    }

    public <T> List<T> selectQuery(String sql, SqlFunction<T> function, SqlParamSetter paramSetter) {
        List<T> list = new ArrayList<>();
        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);

        ) {
            paramSetter.accept(ps);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    list.add(function.apply(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public <T> Optional<T> selectFirst(String sql, SqlFunction<T> function, SqlParamSetter paramSetter) {
        return selectQuery(sql, function, paramSetter).stream().findFirst();
    }

    interface SqlFunction<T> extends Function<ResultSet, T> {
        @Override
        default T apply(ResultSet resultSet) {
            try {
                return safeApply(resultSet);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        T safeApply(ResultSet resultSet) throws SQLException;
    }

    interface SqlParamSetter extends Consumer<PreparedStatement> {
        @Override
        default void accept(PreparedStatement preparedStatement) {
            try {
                safeApply(preparedStatement);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        void safeApply(PreparedStatement preparedStatement) throws SQLException;
    }
}
