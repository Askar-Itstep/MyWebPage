package servlet;

import dao.PersonDao;
import exception.DaoException;
import jdbc.JdbcDaoFactory;
import model.Person;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "EditPersonServlet", urlPatterns = "/editPerson")
public class EditPersonServlet extends HttpServlet {

    private static Logger LOGGER = Logger.getLogger(EditMyStoryServlet.class);
    private final PersonDao personDao= (PersonDao) JdbcDaoFactory.getInstance().getDao(PersonDao.class);
    private long pers_id=0;
    private Person person;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newCredo = "";
        String username = "";
        if(request.getParameter("person_credo") != null && !request.getParameter("person_credo").equals("")){
            newCredo = request.getParameter("person_credo");
            System.out.println("newCredo="+newCredo);               //!!
            person = (Person) request.getSession().getAttribute("person");
//            System.out.println("pers_id="+person.getId());

            person.setCredo(newCredo);
            try {
                personDao.update(person);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
        if(request.getParameter("new_name") != null && !request.getParameter("new_name").equals("")){
            username = request.getParameter("new_name");
            person = (Person) request.getSession().getAttribute("person");

            System.out.println("pers_id="+person.getId());  //!!
            System.out.println("newName="+username);        //!!

            person.setUsername(username);
            try {
                personDao.update(person);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
//        request.setAttribute("person", person);
        getServletContext().getRequestDispatcher("/mainForUser.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/editPerson.jsp").forward(request, response);
//        pers_id = Long.parseLong((request.getParameter("pers_id")));    //если в AuthServ. request.setAttribut("person..) То pers_id = ""; error!
//
//        try {
//            person = personDao.findById(pers_id);
//            if(person != null){
//                request.setAttribute("person", person);
//                getServletContext().getRequestDispatcher("/editPerson.jsp").forward(request, response);
//            }
//            else
//                getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
//
//        } catch (DaoException e) {
//            LOGGER.error(e);
//            getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
//        }
    }
}
