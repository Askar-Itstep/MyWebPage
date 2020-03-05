package dao;

import exception.DaoException;
import model.BaseDBEntity;

import java.util.List;

public interface GenericDao<T extends BaseDBEntity> {

    T findById(Long id) throws DaoException;

    List<T> findAll() throws DaoException;

    void update(T t) throws DaoException;

    void save(T t) throws DaoException;

    boolean remove(T t) throws DaoException;

    boolean removeById(Long id) throws DaoException;
}
