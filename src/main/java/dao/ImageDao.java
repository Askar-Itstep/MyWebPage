package dao;

import exception.DaoException;
import model.Image;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ImageDao extends AbstractDao<Image> {
    private static final Logger LOGGER = Logger.getLogger(ImageDao.class);

    @Override
    public Image findById(Long id) throws DaoException {
        Image image = new Image();
        String sqlQuery = "SELECT * FROM images WHERE id=?";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sqlQuery);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                image.setId(resultSet.getLong("id"));
                image.setPersId(resultSet.getInt("pers_id"));
                image.setImageName(resultSet.getString("image_name"));
            }
        } catch (SQLException e) {
            LOGGER.error("Select image by id ends with error", e.fillInStackTrace());
            e.printStackTrace();
        }finally {
            getFactory().freeConnection(getConnection());
        }
        return image;
    }

    @Override
    public List<Image> findAll() throws DaoException {
        List<Image> images = new ArrayList<>();
        String sqlQuary = "SELECT * FROM images";
        try {
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuary);
            while (resultSet.next()){
                Image image = new Image();
                image.setId(resultSet.getLong(1));
                image.setPersId(resultSet.getInt(2));
                image.setImageName(resultSet.getString(3));
                images.add(image);
            }
        } catch (SQLException e) {
            LOGGER.error("Select images ends with error", e.fillInStackTrace());
            e.printStackTrace();
        }finally {
            getFactory().freeConnection(getConnection());
        }
        return images;
    }

    public List<Image> findAllByPerson(int pers_id) throws DaoException{
        List<Image> images = new ArrayList<>();
        String sqlQuery = "SELECT * FROM images WHERE pers_id=?";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sqlQuery);
            preparedStatement.setInt(1, pers_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Image image = new Image();
                image.setId(resultSet.getLong(1));
                image.setPersId(resultSet.getInt(2));
                image.setImageName(resultSet.getString(3));
                images.add(image);
            }
        } catch (SQLException e) {
            LOGGER.error("Select images by peson id ends with error", e.fillInStackTrace());
            e.printStackTrace();
        }finally {
            getFactory().freeConnection(getConnection());
        }
        return images;
    }
    @Override
    public void update(Image image) throws DaoException {
        String sqlQuery = "UPDATE images SET pers_id=?, image_name=? WHERE id=?";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sqlQuery);
            preparedStatement.setInt(1, image.getPersId());
            preparedStatement.setString(2, image.getImageName());
            preparedStatement.setLong(3, image.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Image image) throws DaoException {
        String sqlQuery = "INSERT INTO images (pers_id, image_name) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement=getConnection().prepareStatement(sqlQuery);
            preparedStatement.setInt(1, image.getPersId());
            preparedStatement.setString(2, image.getImageName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean remove(Image image) throws DaoException {
        String sqlQuery = "DELETE FROM images WHERE id=?";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sqlQuery);
            preparedStatement.setLong(1, image.getId());
            preparedStatement.executeUpdate();
            return  true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeById(Long id) throws DaoException {
        String sqlQuery = "DELETE FROM images WHERE id=?";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sqlQuery);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            return  true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Image findByImgNameByPersId(String filename, int pers_id) {
        Image image = new Image();
        String sqlQuery = "SELECT * FROM images WHERE pers_id=? AND image_name=?";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sqlQuery);
            preparedStatement.setInt(1, pers_id);
            preparedStatement.setString(2, filename);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                image.setId(resultSet.getLong("id"));
                image.setPersId(resultSet.getInt("pers_id"));
                image.setImageName(resultSet.getString("image_name"));
            }
        } catch (SQLException e) {
            LOGGER.error("Select image by id ends with error", e.fillInStackTrace());
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        } finally {
            try {
                getFactory().freeConnection(getConnection());
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
        return image;
    }
}
