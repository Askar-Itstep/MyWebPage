package dao;

import exception.DaoException;
import model.MyStory;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyStoryDao extends AbstractDao<MyStory> {
    private static final Logger LOGGER = Logger.getLogger(MyStoryDao.class);
    @Override
    public MyStory findById(Long id) throws DaoException {
        MyStory myStory = new MyStory();
       String query = "SELECT * FROM my_story WHERE id =?";

       Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
             while(resultSet.next()){
                myStory.setId(resultSet.getLong(1));
                myStory.setText(resultSet.getString(2));
                myStory.setPers_id(resultSet.getInt(3));
            }
        } catch (SQLException e) {
            LOGGER.error("Select story by id ends with error", e.fillInStackTrace());
            e.printStackTrace();
        }finally {
            getFactory().freeConnection(connection);
        }
        return myStory;
    }
    public List<MyStory> findAllByPersonId(int pers_id) throws DaoException {
        List<MyStory> myStoryList = new ArrayList<>();
        String query = "SELECT * FROM my_story WHERE pers_id =?";
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, pers_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                MyStory myStory = new MyStory();
                myStory.setId(resultSet.getLong(1));
                myStory.setText(resultSet.getString(2));
                myStory.setPers_id(resultSet.getInt(3));
                myStoryList.add(myStory);
            }
        } catch (SQLException e) {
            LOGGER.error("Select story by person id ends with error", e.fillInStackTrace());
            e.printStackTrace();
        }finally {
            getFactory().freeConnection(connection);
        }
        return myStoryList;
    }
    @Override
    public List<MyStory> findAll() throws DaoException {
        List<MyStory> myStoryList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM my_story";
        try {
            ResultSet resultSet = getConnection().prepareStatement(sqlQuery).executeQuery();
            while (resultSet.next()){
                MyStory story = new MyStory();
                story.setId(resultSet.getLong(1));
                story.setText(resultSet.getString(2));
                story.setPers_id(resultSet.getInt(3));
                myStoryList.add(story);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myStoryList;
    }

    @Override
    public  void update(MyStory myStory) throws DaoException {
        String sqlQuery = "UPDATE my_story SET text=? WHERE id=?";
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, myStory.getText());
            preparedStatement.setLong(2, myStory.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void save(MyStory myStory) throws DaoException {
        String sqlQuery = "INSERT INTO my_story (text, pers_id) VALUES(?, ?)";
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, myStory.getText());
            preparedStatement.setInt(2, myStory.getPers_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean remove(MyStory myStory) throws DaoException {
        String sqlQuery = "DELETE FROM my_story WHERE id=?";
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setLong(1, myStory.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeById(Long id) throws DaoException {
        String sqlQuery = "DELETE FROM my_story WHERE id=?";
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
