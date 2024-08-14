package dk.cphbusiness.persistence.jdbc.mappers;

import dk.cphbusiness.persistence.jdbc.db.ConnectionPool;
import dk.cphbusiness.persistence.jdbc.db.DatabaseException;
import dk.cphbusiness.persistence.jdbc.Entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public void createUser(String email, String userName, String password, String phone, String role, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "insert into users (email, user_name, password, phone, role) values (?,?,?,?,?)";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, email);
            ps.setString(2, userName);
            ps.setString(3, password);
            ps.setString(4, phone);
            ps.setString(5, role);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Error: No rows affected, when creating new User");
            }
        } catch (SQLException e) {
            String msg = "Error creating User";
            if (e.getMessage().startsWith("ERROR: duplicate key value ")) {
                msg = "Unique Key Violation: Email already exists";
            }
            throw new DatabaseException(msg, e.getMessage());
        }
    }

    public void deleteUser(int userID, ConnectionPool connectionPool) throws DatabaseException {
        String deleteUserSQL = "DELETE FROM users WHERE \"user_id\" = ?";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement deleteUserStatement = connection.prepareStatement(deleteUserSQL)
        ) {
            connection.setAutoCommit(false);
            deleteUserStatement.setInt(1, userID);
            int userRowsAffected = deleteUserStatement.executeUpdate();
            if (userRowsAffected != 1) {
                throw new DatabaseException("Error deleting user.");
            }

            connection.commit(); // Commit transaction
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error deleting User: ", e.getMessage());
        }
    }


    public List<User> getAllUsers(ConnectionPool connectionPool) throws DatabaseException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("user_id");
                String email = rs.getString("email");
                String userName = rs.getString("user_name");
                String phone = rs.getString("phone");
                String role = rs.getString("role");

                User user = new User(id, email, userName, phone, role);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting all users: ", e.getMessage());
        }
        return users;
    }

    public User login(String email, String password, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "select * from users where email=? and password=?";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("user_id");
                String userName = rs.getString("user_name");
                String role = rs.getString("role");
                String phone = rs.getString("phone");
                return new User(id, userName, email, phone, role);
            } else {
                throw new DatabaseException("Fejl i login. Prøv igen");
            }
        } catch (SQLException e) {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
    }

}