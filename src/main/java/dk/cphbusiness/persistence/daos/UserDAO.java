package dk.cphbusiness.persistence.daos;

import dk.cphbusiness.exceptions.EntityNotFoundException;
import dk.cphbusiness.persistence.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.Set;

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
        //return null;

        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public User create(User user) {
        //return null;

        throw new UnsupportedOperationException("Not implemented yet");
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
