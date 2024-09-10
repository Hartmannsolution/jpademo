package dk.cphbusiness.day1.daos;

import java.util.Set;

public interface IDAO<T> {
    T findById(long id);
    void create(T t);
    void update(T t);
    void delete(T t);
    Set<T> findAll();
}
