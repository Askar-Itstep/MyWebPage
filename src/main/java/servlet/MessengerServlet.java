package servlet;

import dao.FriendsRelationsDao;
import exception.DaoException;
import jdbc.JdbcDaoFactory;
import model.Friend;
import model.FriendRelation;
import org.apache.log4j.Logger;
import service.FriendService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

@WebServlet(name = "MessengerServlet", urlPatterns = "/message")
public class MessengerServlet extends HttpServlet {

    private final Logger LOGGER =   Logger.getLogger(MessengerServlet.class);
    private FriendsRelationsDao friendsRelationsDao = (FriendsRelationsDao) JdbcDaoFactory.getInstance().getDao(FriendsRelationsDao.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOGGER.error("MessengerServlet был запущен");
        HttpSession session = request.getSession();

        String message = request.getParameter("new_message") + "\n";

        int persId = Integer.parseInt(request.getParameter("pers_id"));
        int friendId = Integer.parseInt(request.getParameter("friend_id"));

        FriendRelation relation = new FriendRelation(persId, message, friendId);
        try {
            friendsRelationsDao.save(relation);
        } catch (DaoException e) {
            e.printStackTrace();
        }

        Set<Friend> myFriends = FriendService.findMyFriends(Math.toIntExact(persId));  //найти друзей по ID актора
        session.setAttribute("friends", myFriends);



        response.sendRedirect("forum.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
