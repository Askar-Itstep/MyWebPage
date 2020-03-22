package dao;

import exception.DaoException;
import model.Person;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonDao extends AbstractDao<Person> {
    private static final Logger LOGGER = Logger.getLogger(PersonDao.class);

    @Override
    public Person findById(Long id) throws DaoException {
        Person person = new Person();
        String sqlQuery = "SELECT * FROM persons WHERE id = ?;";
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                person.setId(resultSet.getLong("id"));
                person.setUsername(resultSet.getString("username"));
                person.setAge(resultSet.getInt("age"));
                person.setCredo(resultSet.getString("credo"));
                person.setPassword(resultSet.getString("password"));
                person.setAvatarId(resultSet.getInt("avatar_id"));
                person.setRoleId(resultSet.getInt("role_id"));
                person.setLogin(resultSet.getString("login"));
                person.setDateReg(resultSet.getDate("create_at"));
                person.setDateDel(resultSet.getDate("remove_at"));
                person.setEmail(resultSet.getString("email"));
                person.setPhone(resultSet.getString("phone"));
                person.setAddress(resultSet.getString("address"));
            }
        } catch (SQLException e){
            LOGGER.error("Select user by id ends with error", e.fillInStackTrace());
            throw new DaoException(e);
        } finally {
            getFactory().freeConnection(connection);
        }
        return person;
    }

    public Person findByLogin(String login) throws DaoException {
        Person person = null;
        String sqlQuery = "SELECT * FROM persons WHERE login = ?;";
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                person = new Person();
                person.setId(resultSet.getLong("id"));
                person.setUsername(resultSet.getString("username"));
                person.setAge(resultSet.getInt("age"));
                person.setCredo(resultSet.getString("credo"));
                person.setPassword(resultSet.getString("password"));
                person.setAvatarId(resultSet.getInt("avatar_id"));
                person.setRoleId(resultSet.getInt("role_id"));
                person.setLogin(resultSet.getString("login"));
                person.setDateReg(resultSet.getDate("create_at"));
                person.setDateDel(resultSet.getDate("remove_at"));
                person.setEmail(resultSet.getString("email"));
                person.setPhone(resultSet.getString("phone"));
                person.setAddress(resultSet.getString("address"));
            }
        } catch (SQLException e){
            LOGGER.error("Select user by login ends with error", e.fillInStackTrace());
            throw new DaoException(e);
        } finally {
            getFactory().freeConnection(connection);
        }
        return person;
    }

    @Override
    public List<Person> findAll()  throws DaoException {
        List<Person> persons = null;
        String sqlQuery = "SELECT * FROM persons;";
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            persons = new ArrayList<>();
            while (resultSet.next()){
                Person person = new Person();
                person.setId(resultSet.getLong("id"));
                person.setUsername(resultSet.getString("username"));
                person.setAge(resultSet.getInt("age"));
                person.setCredo(resultSet.getString("credo"));
                person.setPassword(resultSet.getString("password"));
                person.setAvatarId(resultSet.getInt("avatar_id"));
                person.setRoleId(resultSet.getInt("role_id"));
                person.setLogin(resultSet.getString("login"));
                person.setDateReg(resultSet.getDate("create_at"));

                if(resultSet.getDate("remove_at") == null)
                    person.setDateDel(Date.valueOf("2100-01-01"));
                else
                    person.setDateDel(resultSet.getDate("remove_at"));
//                if(person.getDateDel().compareTo(Date.valueOf(LocalDate.now())) > 0) {
//                    System.out.println("!!!");
//                }
                person.setEmail(resultSet.getString("email"));
                person.setPhone(resultSet.getString("phone"));
                person.setAddress(resultSet.getString("address"));
                persons.add(person);
            }
        } catch (SQLException e){
            LOGGER.error("Select person by login ends with error", e.fillInStackTrace());
            throw new DaoException(e);
        } finally {
            getFactory().freeConnection(connection);
        }
        return persons;
    }

    @Override
    public void update(Person person) throws DaoException {
        String sqlQuery = "UPDATE persons SET username=?, age=?, credo=?, password=?, avatar_id=?, role_id=?, login=?," +
                "remove_at=?, email=?, phone=?, address=? WHERE id =?";
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, person.getUsername());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setString(3, person.getCredo());
            preparedStatement.setString(4, person.getPassword());
            if(person.getAvatarId()==0){
                int defaultImgId = 1;       //иначе вылет nullpointer! - т.к. set avaId=0 where.. - ERROR!
                person.setAvatarId(defaultImgId);
            }
            preparedStatement.setInt(5, person.getAvatarId());
            preparedStatement.setInt(6, person.getRoleId());
            preparedStatement.setString(7, person.getLogin());

            if(person.getDateDel()==null){
                person.setDateDel(new Date(200,1,1));
            }
            preparedStatement.setDate(8, person.getDateDel());
            preparedStatement.setString(9, person.getEmail());
            preparedStatement.setString(10, person.getPhone());
            preparedStatement.setString(11, person.getAddress());
            preparedStatement.setLong(12,  person.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            LOGGER.error("Update user ends with error", e.fillInStackTrace());
            throw new DaoException(e);
        } finally {
            getFactory().freeConnection(connection);
        }
    }
//администратор имеет прямой доступ к БД-значит для всеx по умолч. role_id>=2
    @Override
    public void save(Person person) throws DaoException {
        String sqlQuery = "INSERT INTO persons (username, password,  role_id, login, create_at) VALUES(?, ?, ?, ?, ? );";
        Connection connection = getConnection();
        LocalDate dateReg = LocalDate.now();
        Date date = Date.valueOf(dateReg.toString());
        person.setDateReg(date);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, person.getUsername());
            preparedStatement.setString(2, person.getPassword());
            if(person.getRoleId()==0)
                person.setRoleId(2);
            preparedStatement.setInt(3, person.getRoleId());
            preparedStatement.setString(4, person.getLogin());
            preparedStatement.setDate(5,  person.getDateReg()); //date

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            LOGGER.error("Save user ends with error", e.fillInStackTrace());
            throw new DaoException(e);
        } finally {
            getFactory().freeConnection(connection);
        }
    }

    @Override
    public boolean remove(Person person)  throws DaoException {
        if(this.findById(person.getId()) == null)
            return false;
//        String sqlquery = "DELETE FROM persons WHERE id=?";
        LocalDate dateDel = LocalDate.now();
        Date date = Date.valueOf(dateDel.toString());
        String sqlquery = "UPDATE persons SET remove_at=? WHERE id=?";
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlquery);
            preparedStatement.setDate(1, date);
            preparedStatement.setLong(2, person.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean removeById(Long id) throws DaoException {
        if(this.findById(id) == null)
            return false;
//        String sqlquery = "DELETE FROM persons WHERE id = ?";

        String sqlquery = "UPDATE persons SET remove_at=? WHERE id=?";
        LocalDate dateDel = LocalDate.now();
        Date date = Date.valueOf(dateDel.toString());
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlquery);
            preparedStatement.setDate(1, date);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


}
