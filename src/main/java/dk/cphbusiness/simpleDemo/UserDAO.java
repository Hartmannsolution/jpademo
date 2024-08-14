package dk.cphbusiness.simpleDemo;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Purpose: This class is a specific DAO (Data Access Object) that can be used to perform CRUD operations on the Boat entity plus some extra queries.
 * @param <T> The entity class that the DAO should be used for.
 * Author: Thomas Hartmann
 */
public class UserDAO {

    EntityManagerFactory emf;
    public UserDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public User getById(Object id) {
        User found;
        try (EntityManager em = emf.createEntityManager()) {
             found = em.find(User.class, id);
            if(found == null) {
                throw new EntityNotFoundException();
            }
        }
        return found;
    }

    public Set<User> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
            Set<User> result = query.getResultList().stream().collect(Collectors.toSet());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User create(User u) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();
            return u;
        } catch (ConstraintViolationException e) {
            System.out.println("Constraint violation: " + e.getMessage());
            return null;
        }
    }

    public User update(User u) {
        try (EntityManager em = emf.createEntityManager()) {
            User found = em.find(User.class, u.getId()); //
            if(found == null) {
                throw new EntityNotFoundException();
            }
            em.getTransaction().begin();
            User merged = em.merge(u);
            em.getTransaction().commit();
            return merged;
        } catch (ConstraintViolationException e) {
            System.out.println("Constraint violation: " + e.getMessage());
            return null;
        }
    }

    public void delete(User user) {
        try (EntityManager em = emf.createEntityManager()) {
            User found = em.find(User.class, user.getId()); //
            if(found == null) {
                throw new EntityNotFoundException();
            }
            em.getTransaction().begin();
            em.remove(found);
            em.getTransaction().commit();
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
        }
    }
}