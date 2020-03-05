package servlet;

import dao.MyStoryDao;
import jdbc.JdbcDaoFactory;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/createInfo")
public class CreateTextPageServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AuthServlet.class);
    private MyStoryDao myStoryDao= (MyStoryDao) JdbcDaoFactory.getInstance().getDao(MyStoryDao.class);
//    private ImageDao imageDao =(ImageDao) JdbcDaoFactory.getInstance().getDao(ImageDao.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/createTextPage.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOGGER.info("Received request for CreateTextPageServlet:");
        HttpSession session = request.getSession();
        Person person;
        try {
            MyStory myStory = new MyStory();
            String text = request.getParameter("text");
            int persId = Integer.parseInt(request.getParameter("pers_id"));
            myStory.setText(text);
            myStory.setPers_id(persId);

            myStoryDao.save(myStory);
            List<MyStory> myStories=new ArrayList<>(myStoryDao.findAllByPersonId(persId));

            request.setAttribute("mystories", myStories);
            getServletContext().getRequestDispatcher("/mainForUser.jsp").forward(request, response);
        }
        catch(Exception ex) {

            getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }
}