package dk.cphbusiness.persistence.daos;


import dk.cphbusiness.persistence.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import org.hibernate.exception.ConstraintViolationException;

import javax.lang.model.UnknownEntityException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Purpose: This class is a specific DAO (Data Access Object) that can be used to perform CRUD operations on the Harbour entity plus some extra queries.
 * Author: Thomas Hartmann
 */
public class HarbourDAO implements IDAO<Harbour> {

    EntityManagerFactory emf;
    public HarbourDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Harbour findById(Object id) {
        try (EntityManager em = emf.createEntityManager()) {
            Harbour found = em.find(Harbour.class, id);
            if(found == null) {
                throw new EntityNotFoundException();
            }
            found.getBoats(); // Lazy loading
            return found;

        } catch (UnknownEntityException e) {
            System.out.println("Unknown entity: " );
            return null;
        }
    }

    public Set<Harbour> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Harbour> query = em.createQuery("SELECT p FROM Harbour p", Harbour.class);
            Set<Harbour> result = query.getResultList().stream().collect(Collectors.toSet());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Harbour create(Harbour t) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
            return t;
        } catch (ConstraintViolationException e) {
            System.out.println("Constraint violation: " + e.getMessage());
            return null;
        }
    }

    public Harbour update(Harbour t) {
        try (EntityManager em = emf.createEntityManager()) {
            Harbour found = em.find(Harbour.class, t.getId()); //
            if(found == null) {
                throw new EntityNotFoundException();
            }
            em.getTransaction().begin();
            Harbour merged = em.merge(t);
            em.getTransaction().commit();
            return merged;
        } catch (ConstraintViolationException e) {
            System.out.println("Constraint violation: " + e.getMessage());
            return null;
        }
    }

    public void delete(Harbour person) {
        try (EntityManager em = emf.createEntityManager()) {
            Harbour found = em.find(Harbour.class, person.getId()); //
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