package dk.cphbusiness.persistence.daos;


import dk.cphbusiness.persistence.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import org.hibernate.exception.ConstraintViolationException;

import javax.lang.model.UnknownEntityException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Purpose: This class is a specific DAO (Data Access Object) that can be used to perform CRUD operations on the Boat entity plus some extra queries.
 * @param <T> The entity class that the DAO should be used for.
 * Author: Thomas Hartmann
 */
public class BoatDAO implements IDAO<Boat> {

    EntityManagerFactory emf;
    public BoatDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Boat findById(Object id) {
        Boat found;
        try (EntityManager em = emf.createEntityManager()) {
             found = em.find(Boat.class, id);
            if(found == null) {
                throw new EntityNotFoundException();
            }
            found.getSeats(); // Lazy loading
            found.getOwners();
        } catch (UnknownEntityException e) {
            System.out.println("Unknown entity: " );
            return null;
        }
        return found;
    }

    public Set<Boat> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Boat> query = em.createQuery("SELECT p FROM Boat p", Boat.class);
            Set<Boat> result = query.getResultList().stream().collect(Collectors.toSet());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boat create(Boat t) {
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

    public Boat update(Boat boat) {
        try (EntityManager em = emf.createEntityManager()) {
            Boat found = em.find(Boat.class, boat.getId()); //
            if(found == null) {
                throw new EntityNotFoundException();
            }
            em.getTransaction().begin();
            if(boat.getName()!=null) {
                found.setName(boat.getName());
            }
//            Boat merged = em.merge(boat);
            em.getTransaction().commit();
            return found;
        } catch (ConstraintViolationException e) {
            System.out.println("Constraint violation: " + e.getMessage());
            return null;
        }
    }

    public void delete(Boat boat) {
        try (EntityManager em = emf.createEntityManager()) {
            Boat found = em.find(Boat.class, boat.getId()); //
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



    public Set<Seat> getPhoneNumbers(int id) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            entityManager.getTransaction().begin();
            Boat boat = entityManager.find(Boat.class, id);
            entityManager.getTransaction().commit();
            return boat.getSeats();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public List<Boat> getAllByZip(Integer zip) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            entityManager.getTransaction().begin();
            List<Boat> boats = entityManager.createQuery("SELECT p FROM Boat p LEFT JOIN p.address address WHERE address IS NOT NULL OR address.zip.zip = :zip", Boat.class)
                    .setParameter("zip", zip)
                    .getResultList();
            entityManager.getTransaction().commit();
            return boats;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public Boat getByPhone(String number) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            entityManager.getTransaction().begin();
            Boat boat = entityManager.createQuery("SELECT p FROM Boat p JOIN p.phones ph WHERE ph.number = :number", Boat.class)
                    .setParameter("number", number)
                    .getSingleResult();
            entityManager.getTransaction().commit();
            return boat;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public List<Boat> getAllByHobby(Owner owner) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            entityManager.getTransaction().begin();
            List<Boat> boats = entityManager.createQuery("SELECT DISTINCT p FROM Boat p JOIN p.hobbies ph WHERE ph.hobby = :hobby", Boat.class)
                    .setParameter("hobby", owner)
                    .getResultList();
            entityManager.getTransaction().commit();
            return boats;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public Boat getPersonByEmail(String mail) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            entityManager.getTransaction().begin();
            Boat boat = entityManager.createQuery("SELECT p FROM Boat p WHERE p.email = :mail", Boat.class)
                    .setParameter("mail", mail)
                    .getSingleResult();
            entityManager.getTransaction().commit();
            return boat;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
    public Harbour getAddressById(int id) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            Harbour harbour = entityManager.find(Harbour.class, id);
            return harbour;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
    public Seat getPhoneById(String id) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            Seat seat = entityManager.find(Seat.class, id);
            return seat;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
    public Owner getHobbyById(String id) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            Owner owner = entityManager.find(Owner.class, id);
            return owner;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}