package dao;

import exception.DaoException;
import model.Statistic;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatisticDao  extends AbstractDao<Statistic>  {
//вычисление количеств регистраций...
    public int findTotalReg() throws DaoException {
        int res = 0;
        try {
            CallableStatement callableStatement = getConnection().prepareCall("{call proc_total_reg_users(?)}");
            callableStatement.registerOutParameter(1, java.sql.Types.TINYINT);
            callableStatement.execute();
            res = callableStatement.getInt(1);
//            statistic.setTotalReg(res);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public int findRegBeginYear() throws DaoException {
        int res = 0;
        try {
            CallableStatement callableStatement = getConnection().prepareCall("{call proc_reg_user_begin_year(?)}");
            callableStatement.registerOutParameter(1, java.sql.Types.TINYINT);
            callableStatement.execute();
            res = callableStatement.getInt(1);
//            statistic.setTotalReg(res);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public int findDelBeginYear() throws DaoException {
        int res = 0;
        try {
            CallableStatement callableStatement = getConnection().prepareCall("{call proc_del_user_begin_year(?)}");
            callableStatement.registerOutParameter(1, java.sql.Types.TINYINT);
            callableStatement.execute();
            res = callableStatement.getInt(1);
//            statistic.setTotalReg(res);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }
    public int findTotalDel() throws DaoException {
        int res = 0;
        try {
            CallableStatement callableStatement = getConnection().prepareCall("{call proc_total_del_user(?)}");
            callableStatement.registerOutParameter(1, java.sql.Types.TINYINT);
            callableStatement.execute();
            res = callableStatement.getInt(1);
//            statistic.setTotalReg(res);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public Statistic findById(Long id) throws DaoException {
        return null;
    }

    @Override
    public List<Statistic> findAll() throws DaoException {
        List<Statistic> statisticList = new ArrayList<>();
        Statistic statistic = new Statistic(findTotalReg(), findRegBeginYear(), findDelBeginYear(), findTotalDel());
        statisticList.add(statistic);
        return statisticList ;
    }

    @Override
    public void update(Statistic statistic) throws DaoException {

    }

    @Override
    public void save(Statistic statistic) throws DaoException {

    }

    @Override
    public boolean remove(Statistic statistic) throws DaoException {
        return false;
    }

    @Override
    public boolean removeById(Long id) throws DaoException {
        return false;
    }
}
