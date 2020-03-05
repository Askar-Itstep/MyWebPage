package dao;

import exception.DaoException;
import model.FriendRelation;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendsRelationsDao extends AbstractDao<FriendRelation> {
    private static final Logger LOGGER = Logger.getLogger(PersonDao.class);
    @Override
    public FriendRelation findById(Long id) throws DaoException {
        String sqlQuery = "SELECT *FROM friends_relations WHERE id=?";
        FriendRelation friendRelation = new FriendRelation();
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sqlQuery);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                friendRelation.setId(resultSet.getLong("id"));
                friendRelation.setPersonId(resultSet.getInt("person_id"));
                friendRelation.setMessage( resultSet.getString("message"));
                friendRelation.setFriendId(resultSet.getInt("friend_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendRelation;
    }

    public List<FriendRelation> findByIdPers(int persId) throws DaoException {
        List<FriendRelation> relations = new ArrayList<>();
        String sqlQuery = "SELECT *FROM friends_relations WHERE person_id=?";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sqlQuery);
            preparedStatement.setLong(1, persId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                FriendRelation relation = new FriendRelation();
                relation.setId(resultSet.getLong("id"));
                relation.setPersonId(resultSet.getInt("person_id"));
                relation.setMessage( resultSet.getString("message"));
                relation.setFriendId(resultSet.getInt("friend_id"));
                relations.add(relation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relations;
    }

    public List<FriendRelation>  findByIdFriend(int friendId) throws DaoException {
        List<FriendRelation> relations = new ArrayList<>();
        String sqlQuery = "SELECT *FROM friends_relations WHERE friend_id=?";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sqlQuery);
            preparedStatement.setLong(1, friendId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                FriendRelation relation = new FriendRelation();
                relation.setId(resultSet.getLong("id"));
                relation.setPersonId(resultSet.getInt("person_id"));
                relation.setMessage( resultSet.getString("message"));   //???????????
                relation.setFriendId(resultSet.getInt("friend_id"));
                relations.add(relation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relations;
    }

    public List<FriendRelation>  findAllByMyId(int myId) throws DaoException {
        List<FriendRelation> relations = new ArrayList<>();
        String sqlQuery = "SELECT *FROM friends_relations WHERE person_id=? UNION SELECT *FROM friends_relations WHERE friend_id=? ";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sqlQuery);
            preparedStatement.setInt(1, myId);
            preparedStatement.setInt(2, myId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                FriendRelation relation = new FriendRelation();
                relation.setId(resultSet.getLong("id"));
                relation.setPersonId(resultSet.getInt("person_id"));
                relation.setMessage(resultSet.getString("message"));   //???????????
                relation.setFriendId(resultSet.getInt("friend_id"));
//                System.out.println("FRelat.Dao: friend_id ="+relation.getFriendId());   //!
                relations.add(relation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relations;
    }

    @Override
    public List<FriendRelation> findAll() throws DaoException {
        String sqlQuery = "SELECT *FROM friends_relations";
        List<FriendRelation> friendRelations = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                FriendRelation friendRelation = new FriendRelation();
                friendRelation.setId(resultSet.getLong("id"));
                friendRelation.setPersonId(resultSet.getInt("person_id"));
                friendRelation.setMessage( resultSet.getString("message"));
                friendRelation.setFriendId(resultSet.getInt("friend_id"));
                friendRelations.add(friendRelation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendRelations;
    }

    @Override
    public void update(FriendRelation friendRelation) throws DaoException {
        String sqlQuery = "UPDATE friends_relations SET person_id=?, message=?, friend_id=? WHERE id =?";
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, friendRelation.getPersonId());
            preparedStatement.setString(2,  friendRelation.getMessage());
            preparedStatement.setInt(3, friendRelation.getFriendId());
            preparedStatement.setLong(4, friendRelation.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            LOGGER.error("Update friends ends with error", e.fillInStackTrace());
            throw new DaoException(e);
        } finally {
            getFactory().freeConnection(connection);
        }
    }

    @Override
    public void save(FriendRelation friendRelation) throws DaoException {
        String sqlQuery = "INSERT INTO friends_relations (person_id, message, friend_id) VALUES(?, ?, ?);";
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, friendRelation.getPersonId());
            preparedStatement.setString(2,  friendRelation.getMessage());
            preparedStatement.setInt(3,  friendRelation.getFriendId()); //date
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            LOGGER.error("Save user ends with error", e.fillInStackTrace());
            throw new DaoException(e);
        } finally {
            getFactory().freeConnection(connection);
        }
    }

    @Override
    public boolean remove(FriendRelation friendRelation) throws DaoException {
        if(this.findById(friendRelation.getId()) == null)
            return false;
       String sqlQuery = "DELETE FROM friends_relations WHERE id=?";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sqlQuery);
            preparedStatement.setLong(1, friendRelation.getId());
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
        String sqlQuery = "DELETE FROM friends_relations WHERE id=?";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sqlQuery);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
