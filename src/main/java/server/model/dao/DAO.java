package server.model.dao;

import org.jetbrains.annotations.TestOnly;
import java.util.List;

/**
 * Created by eugene on 10/18/16.
 */
public interface DAO<T> {
    Long insert(T in) throws DaoError;
    T getById(Long id) throws DaoError;
    List<T> getWhere(String ... conditions) throws DaoError;
    List<T> getAll() throws DaoError;

    void remove(T in) throws DaoError;
    void remove(Long id) throws DaoError;

}
