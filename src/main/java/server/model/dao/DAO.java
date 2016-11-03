package server.model.dao;

import java.util.Collection;

/**
 * Created by eugene on 10/18/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public interface DAO<T> {
    Long insert(T in);
    T getById(Long id);
    Collection<T> getWhere(String ... conditions);
}
