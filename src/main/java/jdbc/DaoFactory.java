package jdbc;

import dao.GenericDao;
import exception.DaoException;

import java.sql.Connection;

public interface DaoFactory<T extends GenericDao> {
    Connection getConnection() throws DaoException;

    void freeConnection(Connection connection);

    T getDao(Class clazz);
}
