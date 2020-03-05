package servlet;

import dao.MyStoryDao;
import exception.DaoException;
import jdbc.JdbcDaoFactory;
import model.MyStory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "EditMyStoryServlet", urlPatterns = "/editText")
public class EditMyStoryServlet extends HttpServlet {

    private static Logger LOGGER = Logger.getLogger(EditMyStoryServlet.class);
    private final MyStoryDao myStoryDao= (MyStoryDao) JdbcDaoFactory.getInstance().getDao(MyStoryDao.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            long storyId = Long.parseLong(request.getParameter("id"));
            String newText = request.getParameter("text");
            int persId = Integer.parseInt(request.getParameter("pers_id"));

            MyStory myStory = new MyStory(newText , persId);
            myStory.setId(storyId);
            myStoryDao.update(myStory); //Добавить в общий котел мою историю

            List<MyStory> myStories = myStoryDao.findAllByPersonId(persId); //вытащить только мои истории
//            request.setAttribute("mystories", myStories);
            request.getSession().setAttribute("mystories", myStories);
            getServletContext().getRequestDispatcher("/mainForUser.jsp").forward(request, response);
        }
        catch(Exception ex) {

            getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int story_id= Integer.parseInt(request.getParameter("id"));
        try {
            MyStory myStory = myStoryDao.findById((long) story_id);
            if(myStory != null){
                request.setAttribute("mystory", myStory);
                getServletContext().getRequestDispatcher("/editStory.jsp").forward(request, response);
            }
            else
                getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);

        } catch (DaoException e) {
//            e.printStackTrace();
            LOGGER.error(e);
            getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
        }
    }
}
