package dao;

import exception.DaoException;
import jdbc.JdbcDaoFactory;
import model.BaseDBEntity;

import java.sql.Connection;

public abstract class AbstractDao<T extends BaseDBEntity> implements GenericDao<T> {
    private JdbcDaoFactory factory;
    private Connection connection;

    public void setFactory(JdbcDaoFactory factory){
        this.factory = factory;
    }

    public JdbcDaoFactory getFactory() {
        return factory;
    }

    public Connection getConnection() throws DaoException {
        return factory.getConnection();
    }
}
