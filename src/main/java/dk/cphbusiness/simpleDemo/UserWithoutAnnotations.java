package dk.cphbusiness.simpleDemo;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Purpose of this class is to show a simple example of a JPA entity
 * Author: Thomas Hartmann
 */
@Getter
@Setter
@NoArgsConstructor
public class UserWithoutAnnotations {

    private Integer id;
    private String userName;
    private String password;
    private String phone;
    private LocalDate birthDate;
    private LocalDate creationDate;

    public UserWithoutAnnotations(String userName, String password, String phone, LocalDate birthDate) {
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserWithoutAnnotations user = (UserWithoutAnnotations) o;
        return id.equals(user.id) && userName.equals(user.userName) && phone.equals(user.phone) && creationDate.equals(user.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, phone, creationDate);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}