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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "DeletePersonServlet", urlPatterns = "/deletePerson")
public class DeletePersonServlet extends HttpServlet {

    private PersonDao personDao = (PersonDao) JdbcDaoFactory.getInstance().getDao(PersonDao.class);
    private final Logger LOGGER =Logger.getLogger(DeletePersonServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            List<Person> persons = new ArrayList<>(personDao.findAll());
            for (Person person: persons
                 ) {
                if(person.getDateDel() == null)
                    personDao.removeById(id);
                else {
                    LOGGER.error("Попытка удаления удаленного субъекта!");
                }
            }

            persons = new ArrayList<>(personDao.findAll());
            request.setAttribute("persons", persons);
            getServletContext().getRequestDispatcher("/admin.jsp").forward(request, response);

        } catch (DaoException e) {
            LOGGER.error(e);
            response.sendRedirect("/errorPage.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/admin.jsp");
    }
}
