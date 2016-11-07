package server.model.dao;

import server.model.data.Score;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * Created by eugene on 10/18/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public interface DAO<T> {
    Long insert(T in) throws DaoError;
    T getById(Long id) throws DaoError;
    List<T> getWhere(String ... conditions) throws DaoError;
    List<T> getAll() throws SQLException, DaoError;
}
