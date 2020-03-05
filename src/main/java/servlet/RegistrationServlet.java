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
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RegistrationServlet", urlPatterns = "/register")
public class RegistrationServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(RegistrationServlet.class);
    private PersonDao personDao = (PersonDao) JdbcDaoFactory.getInstance().getDao(PersonDao.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);

        Person person = new Person(request.getParameter("username"), request.getParameter("password"),request.getParameter("login"));
        try {
            personDao.save(person);
            session.setAttribute("person", person);
            response.sendRedirect("mainForUser.jsp");

        } catch (DaoException e) {
            LOGGER.error(e);
            session.setAttribute("error", "Ошибка при регистрации, обратитесь в службу поддержки по телефону: +7(777)777-77-77");
            response.sendRedirect("errorPage.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
