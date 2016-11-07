package server.model.dao;

import org.hibernate.Session;
import org.jetbrains.annotations.TestOnly;

import java.util.Collection;
import java.util.List;

/**
 * Created by eugene on 10/18/16.
 */
public interface DAO<T> {
    Long insert(T in);
    void remove(T in) throws DaoError;
    void remove(Long id) throws DaoError;
    T getById(Long id);
    List<T> getWhere(String ... conditions);

    @TestOnly
    Session getSession();
}
