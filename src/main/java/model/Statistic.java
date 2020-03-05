package model;

import dao.PersonDao;
import exception.DaoException;
import jdbc.JdbcDaoFactory;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistic extends BaseDBEntity {

    private PersonDao personDao = (PersonDao) JdbcDaoFactory.getInstance().getDao(PersonDao.class);
    private final Logger LOGGER = Logger.getLogger(Statistic.class);
//    private String date;
    private int totalReg;
    private int quantityRegYear;
    private int totalDel;
    private int quantityDelYear;
//    Date dateFirstReg;
    private Map<String, Date> dictDate = new HashMap<>();
    private List<Person> personList;

    {
        try {
            personList = personDao.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public Statistic() {    }

    public Statistic(/*String date,*/ int totalReg, int quantityRegYear, int totalDel, int quantityDelYear) {
//        this.date = date;
        this.totalReg = totalReg;
        this.quantityRegYear = quantityRegYear;
        this.totalDel = totalDel;
        this.quantityDelYear = quantityDelYear;
    }

//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }

    public int getTotalReg() {
        return totalReg;
    }

    public void setTotalReg(int totalReg) {
        this.totalReg = totalReg;
    }

    public int getQuantityRegYear() {
        return quantityRegYear;
    }

    public void setQuantityRegYear(int quantityRegYear) {
        this.quantityRegYear = quantityRegYear;
    }

    public int getTotalDel() {
        return totalDel;
    }

    public void setTotalDel(int totalDel) {
        this.totalDel = totalDel;
    }

    public int getQuantityDelYear() {
        return quantityDelYear;
    }

    public void setQuantityDelYear(int quantityDelYear) {
        this.quantityDelYear = quantityDelYear;
    }

//    public List<Date> datesStatistic(){
//        List<Date> dates = new ArrayList<>();
//        Date dateFirstReg = new Date();
//
//        return dates;
//    }
   public Map<String, Date> getDictDate(){
        Date dateFirstReg = personList.stream().findFirst().get().getDateReg();
//       System.out.println("Stat.:dateFirstReg="+ dateFirstReg);

        Date dateLastReg = personList.get(personList.size()-1).getDateReg();
//       System.out.println("Stat.:dateLastReg="+ dateLastReg);

//дата 1-го удаления = 1-ая выборка из списка (?) дат удалений
        Date dateFirstDel =personList.stream().filter(p->(p.getDateDel() != null)).findFirst().get().getDateDel();  //????????????????
//       System.out.println("dateFirstDel="+ dateFirstDel);

        Date dateLastDel = personList.stream().filter(p->(p.getDateDel() != null)).max((p1, p2)->p1.getDateDel().compareTo(p2.getDateDel()))
                .get().getDateDel();        //???????????????????????
//       System.out.println("Stat.:dateLastDel="+ dateLastDel);

        dictDate.put("dateFirstReg", dateFirstReg);
        dictDate.put("dateLastReg", dateLastReg);
        dictDate.put("dateFirstDel", dateFirstDel);
        dictDate.put("dateLastDel", dateLastDel);

        return dictDate;
   }
}
