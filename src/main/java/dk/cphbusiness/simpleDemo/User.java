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
@Entity
@Getter
@Setter
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name="User.deleteAll", query="DELETE FROM User")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone", unique = true, nullable = false)
    private String phone;

    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Builder
    public User(String userName, String password, String phone, LocalDate birthDate) {
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.birthDate = birthDate;
    }

    @PrePersist
    private void prePersist() {
        this.creationDate = LocalDate.now();
    }

    @PreUpdate
    public void preUpdate() {
        if (!validatePhoneNumber(this.phone)) {
            throw new IllegalArgumentException("One or more phone numbers are invalid");
        }
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return true;
        }

        return phoneNumber.matches("^[0-9]{8,11}$");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
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
    public int getAge(){
        return Period.between(LocalDate.now(), creationDate).getYears();
    }
}