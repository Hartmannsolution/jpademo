package dk.cphbusiness.persistence.daos;

import dk.cphbusiness.persistence.HibernateConfig;
import dk.cphbusiness.persistence.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    private static EntityManagerFactory emf;
    private static UserDAO userDAO;
    private User u1, u2;

    @BeforeAll
    static void setUpAll() {
        HibernateConfig.setTestMode(true);
        emf = HibernateConfig.getEntityManagerFactory();
        userDAO = new UserDAO(emf);
    }

    @AfterAll
    static void tearDownAll() {
        HibernateConfig.setTestMode(false);
    }

    @BeforeEach
    void setUp() {
        try(EntityManager em = emf.createEntityManager()){
            u1 = new User("user1", "password1", "30303030", LocalDate.of(2010, 9, 20));
            u2 = new User("user2", "password1", "30303030", LocalDate.of(2010, 9, 20));
            em.getTransaction().begin();
            em.remove(u1);
            em.remove(u2);
            em.persist(u1);
            em.persist(u2);
            em.getTransaction().commit();

        }

    }

    @Test
    void findByIdWithRoleSet() {
    }

    @Test
    void findById() {
    }

    @Test
    void getAll() {
    }

    @Test
    void create() {
    }
}