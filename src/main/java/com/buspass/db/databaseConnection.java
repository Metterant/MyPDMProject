//Module này để lấy được kết nối đến database.

package com.buspass.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.github.cdimascio.dotenv.Dotenv;

public class databaseConnection {
    private static final HikariDataSource dataSource;

    static {
        // Try loading from classpath .env (db_credentials.env) first, fall back to environment variables
        Dotenv dotenv = null;
        try {
            dotenv = Dotenv.configure()
                    .filename("db_credentials.env")
                    .ignoreIfMissing()
                    .load();
        } catch (Exception e) {
            // ignore, we'll try system env
        }

        String jdbcUrl = getVar(dotenv, "DB_URL", null);
        String user = getVar(dotenv, "DB_USER", null);
        String pass = getVar(dotenv, "DB_PASSWORD", null);
        String maxPool = getVar(dotenv, "DB_MAX_POOL_SIZE", "10");

        if (jdbcUrl == null || jdbcUrl.isBlank()) {
            System.err.println("Database URL is not set. Provide DB_URL in db_credentials.env or as an environment variable.");
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        if (user != null) config.setUsername(user);
        if (pass != null) config.setPassword(pass);

        try {
            config.setMaximumPoolSize(Integer.parseInt(maxPool));
        } catch (NumberFormatException e) {
            // ignore and use default
        }

        // Optional recommended settings
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(config);

        // Close pool on JVM shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (dataSource != null && !dataSource.isClosed()) {
                dataSource.close();
            }
        }));
    }

    private static String getVar(Dotenv dotenv, String key, String fallback) {
        if (dotenv != null) {
            try {
                String v = dotenv.get(key);
                if (v != null && !v.isBlank()) return v;
            } catch (Exception e) {
                // ignore
            }
        }
        String env = System.getenv(key);
        return (env != null && !env.isBlank()) ? env : fallback;
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            System.err.println("Failed to obtain connection from pool: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static void shutdownPool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}