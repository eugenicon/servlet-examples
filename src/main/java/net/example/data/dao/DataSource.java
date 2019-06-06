package net.example.data.dao;

import net.example.resolver.Component;
import net.example.util.ResourceReader;
import net.example.util.SafeBiConsumer;
import net.example.util.SafeConsumer;
import net.example.util.SafeFunction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

@Component
public class DataSource {
    private static final Logger LOGGER = LogManager.getLogger(DataSource.class);
    private final String userName;
    private final String password;
    private final String url;

    public DataSource(Properties properties) throws ClassNotFoundException {
        Class.forName(properties.getProperty("jdbc.driver"));

        url = properties.getProperty("jdbc.url");
        userName = properties.getProperty("jdbc.user");
        password = properties.getProperty("jdbc.password");

        try {
            String initBdScript = ResourceReader.getResourceAsString(properties.getProperty("jdbc.init-script"));
            if (!initBdScript.isEmpty()) {
                query(initBdScript).execute();
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, userName, password);
    }

    private <T> List<T> executeQuery(Connection connection, QueryData queryData) throws SQLException {
        boolean useBatch = queryData.data.size() > 1;
        List<T> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(queryData.query, Statement.RETURN_GENERATED_KEYS)) {
            if (queryData.parameters != null) {
                for (Object dataObject : queryData.data) {
                    queryData.parameters.accept(ps, dataObject);
                    if (useBatch) {
                        ps.addBatch();
                    }
                }
            }
            LOGGER.debug(ps);
            if (queryData.query.toUpperCase().startsWith("SELECT")) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        list.add((T) queryData.converter.apply(rs));
                    }
                }
            } else {
                if (useBatch) {
                    ps.executeBatch();
                } else {
                    ps.executeUpdate();
                }
                if (queryData.resultProcessor != null) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        while (rs.next()) {
                            queryData.resultProcessor.accept(rs);
                        }
                    }
                }
            }
        }
        return list;
    }

    public QueryBuilder query(String sql) {
        return new QueryBuilder().and(sql);
    }

    private class QueryData {
        private List<?> data = Collections.singletonList(null);
        private SafeFunction<ResultSet, ?> converter;
        private String query;
        private SafeBiConsumer<PreparedStatement, Object> parameters;
        private SafeConsumer<ResultSet> resultProcessor;
    }

    public class QueryBuilder {
        private List<QueryData> queries = new ArrayList<>();

        private <T> List<T> executeAll() {
            List<T> list = new ArrayList<>();
            boolean useTransactions = queries.size() > 1;
            try (Connection connection = getConnection()) {
                if (useTransactions) {
                    LOGGER.debug("begin transaction");
                    connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                    connection.setAutoCommit(false);
                }
                for (QueryData queryData : queries) {
                    list.addAll(executeQuery(connection, queryData));
                }
                if (useTransactions) {
                    LOGGER.debug("commit transaction");
                    connection.commit();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return list;
        }

        public QueryBuilder and(String sql) {
            QueryData queryData = new QueryData();
            queryData.query = sql;
            queries.add(queryData);
            return this;
        }

        public <T> Optional<T> first(SafeFunction<ResultSet, T> converter) {
            return list(converter).stream().findFirst();
        }

        public <T> List<T> list(SafeFunction<ResultSet, T> converter) {
            getLastQuery().converter = converter;
            return executeAll();
        }

        public <T> QueryBuilder prepare(SafeConsumer<PreparedStatement> prepared) {
            return prepare(Collections.singletonList(null), (PreparedStatement ps, T data) -> prepared.accept(ps));
        }

        public <T> QueryBuilder prepare(List<T> data, SafeBiConsumer<PreparedStatement, T> prepared) {
            QueryData query = getLastQuery();
            query.data = data;
            query.parameters = (SafeBiConsumer<PreparedStatement, Object>) prepared;
            return this;
        }

        public void execute() {
            executeAll();
        }

        public void execute(SafeConsumer<ResultSet> processor) {
            getLastQuery().resultProcessor = processor;
            execute();
        }

        private QueryData getLastQuery() {
            return this.queries.get(this.queries.size() - 1);
        }
    }

}
