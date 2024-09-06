package dk.cphbusiness.daos;

import dk.cphbusiness.persistence.daos.HarbourDAO;
import dk.cphbusiness.persistence.daos.BoatDAO;
import dk.cphbusiness.persistence.model.Boat;
import dk.cphbusiness.persistence.model.Harbour;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import dk.cphbusiness.persistence.HibernateConfig;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DAOTest {
    private static EntityManagerFactory emf;
    private static BoatDAO boatDao;
    private static HarbourDAO harbourDao;

    Boat b1, b2, b3;
    Harbour h1, h2, h3;

    @BeforeAll
    static void setUpAll() {
        HibernateConfig.setTestMode(true);
        emf = HibernateConfig.getEntityManagerFactory();
        boatDao = new BoatDAO(emf);
        harbourDao = new HarbourDAO(emf);
    }

    @AfterAll
    static void tearDownAll() {
        HibernateConfig.setTestMode(false);
    }

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        b1 = new Boat("Utson", "DS740", "Wind of Change", LocalDate.now());
        b2 = new Boat("Zeelander", "8", "Why not", LocalDate.now());
        b3 = new Boat("Freya", "G45", "Holy smoke", LocalDate.now());
        h1 = new Harbour("Hansvej 111", "Rungsted Havn", 2800, "Lyngby");
        h2 = new Harbour("Fjellvej 232", "Kalkhavnen", 2800, "Lyngby");
        b1.setHarbour(h1);
        b2.setHarbour(h2);
        b3.setHarbour(h2);
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Seat").executeUpdate();
        em.createQuery("DELETE FROM Boat").executeUpdate();
        em.createQuery("DELETE FROM Harbour").executeUpdate();
        em.createQuery("DELETE FROM Owner").executeUpdate();
        em.persist(b1);
        em.persist(b2);
        em.persist(b3);
        em.persist(h1);
        em.persist(h2);
        em.getTransaction().commit();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Test that we can create a boat")
    void create() {
        Boat toBeCreated = new Boat("Epson", "DS440", "My Fair Lady", LocalDate.now());
        Boat boat = boatDao.create(toBeCreated);
        assert boat.getId() != null;
    }


    @Test
    @DisplayName("Test that we can get all boats")
    void getAll() {
        assertEquals(3, boatDao.getAll().size());
    }

    @Test
    @DisplayName("Test that we can get a boat by id")
    void getById() {
        Boat boat = boatDao.findById(b1.getId());
        assert boat.getId() != null && boat.getId().equals(b1.getId());
    }

    @Test
    @DisplayName("Test that we can update a boat")
    void update() {
        Boat boat = boatDao.findById(b1.getId());
        boat.setName("Magic Journey");
        boatDao.update(boat);
        Boat updated = boatDao.findById(b1.getId());
        assertEquals("Magic Journey", updated.getName());
    }

    @Test
    @DisplayName("Test that we can delete a boat")
    void delete() {
        Boat boat = boatDao.findById(b1.getId());
        boatDao.delete(boat);
        assertEquals(2, boatDao.getAll().size());
    }

    @Test
    @DisplayName("Test that we can add an harbour to a boat")
    void addHarbour() {
        Boat boat = boatDao.findById(b1.getId());
        Harbour a4 = new Harbour("Svanevej 123", "Fuglehavn", 3050, "Humlebæk");
        a4 = harbourDao.create(a4);
        Boat updated = boatDao.findById(b1.getId());
        updated.setHarbour(a4);
        assertEquals("Svanevej 123", updated.getHarbour().getStreet());
    }

    @Test
    @DisplayName("Test that we can remove an harbour from a boat")
    void removeAddress() {
        var boat = boatDao.findById(b1.getId());
        var harbour = harbourDao.findById(h1.getId());
        boat.removeHarbour();
        boatDao.update(boat);
        boat = boatDao.findById(b1.getId());
        assertNull(boat.getHarbour());
//        assert boat.getHarbour() == null;
    }

}