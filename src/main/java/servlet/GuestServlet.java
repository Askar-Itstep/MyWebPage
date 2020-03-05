package servlet;

import dao.*;
import exception.DaoException;
import jdbc.JdbcDaoFactory;
import model.Image;
import model.MyStory;
import model.Person;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GuestServlet", urlPatterns = "/guest")
public class GuestServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(GuestServlet.class);

    private PersonDao personDao = (PersonDao) JdbcDaoFactory.getInstance().getDao(PersonDao.class);
    private ImageDao imageDao = (ImageDao) JdbcDaoFactory.getInstance().getDao(ImageDao.class);
    private MyStoryDao myStoryDao = (MyStoryDao) JdbcDaoFactory.getInstance().getDao(MyStoryDao.class);

//входит гость после areaGuest.jsp -> GuestServlet --> mainForUser
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        boolean flagGuest = Boolean.parseBoolean(request.getParameter("flagGuest"));
        if(  flagGuest == true)
            LOGGER.info("GuestServlet: flag = true");
        else LOGGER.info("GuestServlet: flag = false");

        response.setContentType("text/html");
        try {
            int pers_id = Integer.parseInt(request.getParameter("pers_id"));
            Person person = personDao.findById((long) pers_id);
            session.setAttribute("person", person);

//            String flagGuest = "trueGuest";
            session.setAttribute("flagGuest", flagGuest);

            List<Image> imagesAll = imageDao.findAll();
            session.setAttribute("images", imagesAll);

            List<MyStory>  myStories= myStoryDao.findAllByPersonId(Math.toIntExact(person.getId()));
            session.setAttribute("mystories", myStories);

            response.sendRedirect("mainForUser.jsp");

        } catch (DaoException e) {
            LOGGER.error(e);
            response.sendRedirect("errorPage.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { }
}
