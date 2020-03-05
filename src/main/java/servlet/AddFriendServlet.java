package servlet;

import dao.FriendsRelationsDao;
import exception.DaoException;
import jdbc.JdbcDaoFactory;
import model.Friend;
import model.FriendRelation;
import model.Person;
import org.apache.log4j.Logger;
import service.FriendService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@WebServlet(name = "AddFriendServlet", urlPatterns = "/addFriend")
public class AddFriendServlet extends HttpServlet {

    private final Logger LOGGER =   Logger.getLogger(MessengerServlet.class);
    private FriendsRelationsDao friendsRelationsDao = (FriendsRelationsDao) JdbcDaoFactory.getInstance().getDao(FriendsRelationsDao.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.error("MessengerServlet был запущен");
        HttpSession session = request.getSession();

        int persId = Integer.parseInt(request.getParameter("pers_id"));
        int candidatId = Integer.parseInt(request.getParameter("candidat_id"));

        try {
            friendsRelationsDao.save(new FriendRelation(persId, "Hello!", candidatId));
        } catch (DaoException e) {
            e.printStackTrace();
        }

        Set<Friend> myFriends = FriendService.findMyFriends(Math.toIntExact(persId));  //найти друзей по ID актора
        session.setAttribute("friends", myFriends);

        List<Person> candidats = FriendService.findCandidats(Math.toIntExact(persId));
        session.setAttribute("candidats", candidats);
        response.sendRedirect("forum.jsp");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
