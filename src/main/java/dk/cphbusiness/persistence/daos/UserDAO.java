package dk.cphbusiness.persistence.daos;

import dk.cphbusiness.exceptions.EntityNotFoundException;
import dk.cphbusiness.persistence.HibernateConfig;
import dk.cphbusiness.persistence.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.hibernate.boot.model.source.internal.hbm.HibernateTypeSourceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Purpose:
 *
 * @author: Thomas Hartmann
 */
public class UserDAO implements IDAO<User>{
    EntityManagerFactory emf;
    public UserDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    // Example showing the use of Lazy Loading:
    public User findByIdWithRoleSet(Object id) throws EntityNotFoundException {
        try(EntityManager em = emf.createEntityManager()){
            User found = em.find(User.class, id);
            int size = found.getRoleSet().size();
            if(found==null)
                throw new EntityNotFoundException("No user with that id");
            return found;
        }
    }
    @Override
    public User findById(Object id) throws EntityNotFoundException {
        try(EntityManager em = emf.createEntityManager()){
            User found = em.find(User.class, id);
            if(found==null)
                throw new EntityNotFoundException("No user with that id");
            return found;
        }
    }

    @Override
    public Set<User> getAll() {
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
            return query
                    .getResultList()
                    .stream()
                    .collect(Collectors
                            .toSet());
        }
    }

    public List<User> getUserListOrderedByName(){

        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u ORDER BY u.userName", User.class);
            return query.getResultList();
        }
    }

    public User getUserByUsername(String username){
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.userName = :username", User.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        }
    }

    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO(HibernateConfig.getEntityManagerFactory());
//        userDAO.create(new User("Gert", "pass123", "50505050", LocalDate.of(2020,3,3)));
//        userDAO.create(new User("Charlie", "pass123", "12345678", LocalDate.of(2020,3,3)));
//        userDAO.create(new User("Helle", "pass123", "32342342", LocalDate.of(2020,3,3)));
//        userDAO.getUserListOrderedByName().forEach(System.out::println);
        User gert = userDAO.getUserByUsername("Gert");
        System.out.println(gert);
    }


    @Override
    public User create(User user) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        }
        return user;
    }


    @Override
    public User update(User user) {
        //return null;

        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void delete(User user) {
        //

        throw new UnsupportedOperationException("Not implemented yet");
    }
}
