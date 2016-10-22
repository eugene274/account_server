package server.model.dao;

/**
 * Created by eugene on 10/18/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public interface DAO<T> {
    Long insert(T in);
    T getById(Long id);
}
