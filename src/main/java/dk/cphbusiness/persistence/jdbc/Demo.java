package dk.cphbusiness.persistence.jdbc;

import dk.cphbusiness.persistence.jdbc.Entities.User;
import dk.cphbusiness.persistence.jdbc.db.ConnectionPool;
import dk.cphbusiness.persistence.jdbc.db.DatabaseException;
import dk.cphbusiness.persistence.jdbc.mappers.UserMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Purpose:
 *
 * @author: Thomas Hartmann
 */
public class Demo {
    public static void main(String[] args) {
        // Read config.properties
        Properties properties = new Properties();
        try {
            properties.load(Demo.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        properties.propertyNames().asIterator().forEachRemaining(key -> {
            System.out.println(key + ": " + properties.getProperty(key.toString()));
        });
        // Create a connection pool
        ConnectionPool connectionPool = ConnectionPool.getConnectionPool(
                properties.getProperty("jdbc.user"),
                properties.getProperty("jdbc.password"),
                properties.getProperty("jdbc.url"),
                properties.getProperty("jdbc.db")
        );
        // Create a user
        try {

            UserMapper um = new UserMapper();
            deleteAllUsers(um, connectionPool);
            um.createUser("Kurt", "Kristensen", "kk@cphbusiness.dk", "40324020", "admin", connectionPool);
            um.createUser("Hassan", "Hasima", "hh@cphbusiness.dk", "40304020", "user", connectionPool);
            um.getAllUsers(connectionPool).forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteAllUsers(UserMapper um, ConnectionPool connectionPool) throws DatabaseException {
        um.getAllUsers(connectionPool).forEach((User user) -> {
            try {
                um.deleteUser(user.getUserID(), connectionPool);
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
        });
    }
}
