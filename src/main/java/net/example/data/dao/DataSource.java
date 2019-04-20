package net.example.data.dao;

import net.example.util.ResourceReader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Function;

public class DataSource {

    private final String userName;
    private final String password;
    private final String url;

    public DataSource(Properties properties) {
       try {
            Class.forName(properties.getProperty("jdbc.driver"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        url = properties.getProperty("jdbc.url");
        userName = properties.getProperty("jdbc.user");
        password = properties.getProperty("jdbc.password");

        try {
            String initBdScript = ResourceReader.getResourceAsString(properties.getProperty("jdbc.init-script"));
            if (!initBdScript.isEmpty()) {
                executeUpdate(initBdScript, ps -> {}, rs -> {});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, userName, password);
    }

    public <T> List<T> selectQuery(String sql, SqlFunction<T> converter) {
        return selectQuery(sql, converter, ps -> {});
    }

    public <T> List<T> selectQuery(String sql, SqlFunction<T> converter, SqlConsumer<PreparedStatement> paramSetter) {
        List<T> list = new ArrayList<>();
        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            paramSetter.accept(ps);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    list.add(converter.apply(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public void executeUpdate(String sql, SqlConsumer<PreparedStatement> paramSetter, SqlConsumer<ResultSet> resultProcessor) {
        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            paramSetter.accept(ps);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys();) {
                while (rs.next()) {
                    resultProcessor.accept(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> Optional<T> selectFirst(String sql, SqlFunction<T> converter, SqlConsumer<PreparedStatement> paramSetter) {
        return selectQuery(sql, converter, paramSetter).stream().findFirst();
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

    interface SqlConsumer<T> extends Consumer<T> {
        @Override
        default void accept(T preparedStatement) {
            try {
                safeApply(preparedStatement);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        void safeApply(T preparedStatement) throws SQLException;
    }
}
