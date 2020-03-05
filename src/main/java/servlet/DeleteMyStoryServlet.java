package servlet;

import dao.MyStoryDao;
import exception.DaoException;
import jdbc.JdbcDaoFactory;
import model.MyStory;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "DeleteMyStoryServlet", urlPatterns = "/deleteText")
public class DeleteMyStoryServlet extends HttpServlet {

    private MyStoryDao myStoryDao = (MyStoryDao) JdbcDaoFactory.getInstance().getDao(MyStoryDao.class);
    private final Logger LOGGER=Logger.getLogger(DeletePersonServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long story_id = 0;
        try {
            if(request.getParameter("mystory_id") != null && request.getParameter("image_id") != "") {
                story_id = Long.parseLong((request.getParameter("mystory_id")));
                if(story_id == 0)
                    response.sendRedirect("/mainForUser.jsp");
                myStoryDao.removeById(story_id);

                int pers_id = Integer.parseInt(request.getParameter("pers_id"));
                List<MyStory> myStories=new ArrayList<>(myStoryDao.findAllByPersonId(pers_id));
                request.setAttribute("mystories", myStories);

                ServletContext servletContext = getServletContext();
                RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/mainForUser.jsp");
                requestDispatcher.forward(request, response);
            }

        } catch (DaoException e) {
            LOGGER.error(e);
            getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
