package dk.cphbusiness.persistence.jdbc.Entities;

public class User {
    private final int userID;
    private final String email;
    private final String userName;
    private final String phone;
    private String password = null;
    private final String role;

    public User(int userId, String email, String userName, String password, String phone, String role) {
        this.userID = userId;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.role = role;
    }

    public User(int userId, String email, String userName, String phone, String role) {
        this.userID = userId;
        this.email = email;
        this.userName = userName;
        this.phone = phone;
        this.role = role;
    }
    public int getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }
    public String getUserName() {
        return userName;
    }
    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" +
            "userId=" + userID +
            ", userName='" + email + '\'' +
            ", password='" + password + '\'' +
            ", role='" + role + '\'' +
            '}';
    }
}
