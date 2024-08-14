package dk.cphbusiness.persistence.jdbc.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dk.cphbusiness.utils.Utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/***
 * Purpose of this class is to handle a Hikari ConnectionPool
 * Author: Thomas Hartmann
 */
public class ConnectionPool {
    public static ConnectionPool instance;
    public static HikariDataSource ds;

    private ConnectionPool() {
    }

    public static ConnectionPool getConnectionPool(String user, String password, String url, String db) {
        if (instance != null) {
            return instance;
        }
        if (System.getenv("DEPLOYED") == null) {
            ds = createHikariConnectionPool(user, password, url, db);
        } else {
            ds = createHikariConnectionPool(
                    System.getenv("JDBC_USER"),
                    System.getenv("JDBC_PASSWORD"),
                    System.getenv("JDBC_CONNECTION_STRING"),
                    Utils.getPropertyValue("db.name", "properties-from-pom.properties"));
        }
        instance = new ConnectionPool();
        return instance;
    }

    /***
     * Getting a live connection from a Hikari Connection Pool
     * @return a database connection to be used in sql requests
     * @throws SQLException
     */
    public synchronized Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public synchronized void close() {
        Logger.getLogger("db").log(Level.INFO, "Shutting down connection pool");
        ds.close();
    }

    /***
     * @param user for Postgresql database user
     * @param password for Postgresql database user
     * @param url connection string for postgresql database. Remember to add currentSchema to string
     * @param db database name for connection
     * @return a Hikari DataSource
     */
    private static HikariDataSource createHikariConnectionPool(String user, String password, String url, String db) {
        Logger.getLogger("db").log(Level.INFO, String.format("Connection Pool created for: (%s, %s, %s, %s)", user, password, url, db));
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl(String.format(url, db));
        System.out.println("URL::" + String.format(url, db));
        config.setUsername(user);
        config.setPassword(password);
        config.setMaximumPoolSize(3);
        config.setPoolName("Postgresql Pool");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return new HikariDataSource(config);
    }
}