package jdbc;

import com.jolbox.bonecp.BoneCP;
import dao.AbstractDao;
import exception.DaoException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcDaoFactory<T extends AbstractDao> implements DaoFactory<T> {
    private static volatile JdbcDaoFactory instance;
    private static final Logger LOGGER = Logger.getLogger(JdbcDaoFactory.class);
    private static BoneCP connectionPool;
    private Connection connection;

    private JdbcDaoFactory() {
        ConnectionManager.configureConnPool();
        connectionPool = ConnectionManager.getConnectionPool();
    }

    public static JdbcDaoFactory getInstance(){
        JdbcDaoFactory localInstance = instance;
        if (localInstance == null) {
            synchronized (JdbcDaoFactory.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = new JdbcDaoFactory();
                }
            }
        }
        return instance;
    }

    @Override
    public Connection getConnection() throws DaoException {
        try {
            connection = connectionPool.getConnection();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return connection;
    }

    @Override
    public void freeConnection(Connection connection) {
        if (connection != null) {
            ConnectionManager.closeConnection(connection);
        }
    }

    @Override
    public T getDao(Class clazz) {
        T t = null;
        try {
            t = (T) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.error("InstantiationException or IllegalAccessException was occurred: "+e);
        }
        if (t != null) {
            t.setFactory(this);
        }
        return t;
    }
}
