package dao;

import exception.DaoException;
import model.Role;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDao extends AbstractDao<Role> {
    @Override
    public Role findById(Long id) throws DaoException {
        String sqlQuery = "SELECT name_role FROM roles WHERE id=?";
        Role role=new Role();
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            role.setId(resultSet.getLong(1));
            role.setName_role(resultSet.getString(2));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }

    @Override
    public List<Role> findAll() throws DaoException {
        List<Role> roles = new ArrayList<>();
        String sqlQuery = "SELECT* FROM roles";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Role role = new Role();
                role.setId(resultSet.getLong(1));
                role.setName_role(resultSet.getString(2));
                roles.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    @Override
    public void update(Role role) throws DaoException {
        String sqlQuery = "UPDATE roles SET name_role=? WHERE id=?";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sqlQuery);
            preparedStatement.setString(1, role.getName_role());
            preparedStatement.setLong(2, role.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Role role) throws DaoException {
        String sqlQuery = "INSERT INTO roles (name_role) value ?";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sqlQuery);
            preparedStatement.setString(1, role.getName_role());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean remove(Role role) throws DaoException {
        String sqlQuery = "DELETE FROM roles WHERE id=?";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sqlQuery);
            preparedStatement.setLong(1, role.getId());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeById(Long id) throws DaoException {
        String sqlQuery = "DELETE FROM roles WHERE id=?";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sqlQuery);
            preparedStatement.setLong(1, id);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
