package servlet;

import dao.*;
import exception.DaoException;
import jdbc.JdbcDaoFactory;
import model.*;
import org.apache.log4j.Logger;
import service.FriendService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet(name = "AuthServlet", urlPatterns = "/auth")
public class AuthServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(AuthServlet.class);

    private PersonDao personDao = (PersonDao) JdbcDaoFactory.getInstance().getDao(PersonDao.class);
    private ImageDao imageDao = (ImageDao) JdbcDaoFactory.getInstance().getDao(ImageDao.class);
    private MyStoryDao myStoryDao = (MyStoryDao) JdbcDaoFactory.getInstance().getDao(MyStoryDao.class);
    private StatisticDao statisticDao = (StatisticDao) JdbcDaoFactory.getInstance().getDao(StatisticDao.class);
    private FriendsRelationsDao friendsRelationsDao = (FriendsRelationsDao) JdbcDaoFactory.getInstance().getDao(FriendsRelationsDao.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Received request with parameters:");
        HttpSession session = request.getSession();
        try {
            Person person = personDao.findByLogin(request.getParameter("login"));
            List<MyStory> myStories;
            List<Person> persons = new ArrayList<>();
          //------------------если вошел гость  ------------------------------
            boolean flagGuest = Boolean.parseBoolean(request.getParameter("flagGuest"));
            if(flagGuest) {
                LOGGER.info("AuthServlet: Вошел гость!");
//                session.setAttribute("flagGuest", flagGuest);

                persons = personDao.findAll().stream().filter(p->p.getRoleId() != 1).collect(Collectors.toList());
                List<Image> imagesAll = imageDao.findAll();
                session.setAttribute("persons", persons);
                session.setAttribute("images", imagesAll);
                response.sendRedirect("areaGuest.jsp");
            }
        //------------------если в БД нет такого Login'a-----------------------------
            else if (person == null) {
                LOGGER.info("User not found");
                session.setAttribute("error", "Пользователь не найден!");
                response.sendRedirect("index.jsp");
        //--------а если есть такой Login..------------
            } else if (!flagGuest){
                if (!request.getParameter("password").equals(person.getPassword())){    //если пароль не найден
                    LOGGER.info("Incorrect login or password");
                    session.setAttribute("error", "Логин или пароль неверны!");
                    response.sendRedirect("index.jsp");

                } else {                                                    //найден и логи и пароль
                    LOGGER.info("Succeed login person: "+person.getId());

                    session.setAttribute("person", person);
                    persons = personDao.findAll();
                    session.setAttribute("persons", persons);

                    //--------------если вошел admin---------------------------
                    if(person.getRoleId()==1){
                        Statistic statistic = (Statistic) statisticDao.findAll().get(0);
//                        System.out.println("statistic.getQuantityRegYear()="+statistic.getQuantityRegYear());
//                        System.out.println("statistic.getTotalReg()" + statistic.getTotalReg());

//                        for (Map.Entry<String, Date> item: statistic.getDictDate().entrySet()
//                             ) {
//                            System.out.println("key: "+item.getKey()+" value: "+item.getValue());
//                        }
                        session.setAttribute("statistic", statistic);
                        String now = Date.valueOf(LocalDate.now()).toString();
                        session.setAttribute("now", now);
                        response.sendRedirect("admin.jsp");
                    }
                    //-----------вошел user-------------------------------
                    else {
                        LOGGER.info("User "+person.getId()+" connected from: "+request.getRemoteAddr());

                        myStories= myStoryDao.findAllByPersonId(Math.toIntExact(person.getId()));

//                        images=imageDao.findAllByPerson(Math.toIntExact(person.getId()));   //все img актора- больше не надо, в jsp своя выборка
                        session.setAttribute("mystories", myStories);

//                        session.setAttribute("images", images);
                        List<Image> imagesAll = imageDao.findAll();     //вообще все img, в jsp своя выборка
                        session.setAttribute("images", imagesAll);

                        Set<Friend> myFriends = FriendService.findMyFriends(Math.toIntExact(person.getId()));  //найти друзей по ID актора
                        session.setAttribute("friends", myFriends);
//                        System.out.println("size="+myFriends.size()); // Set -нет актора-только 2 чел.!
//                        for (Friend friend: myFriends
//                             ) {
//                            System.out.println("auth:"+friend.getUsername()); //!
////                            System.out.println("auth: friend.avatar_id = "+ friend.getAvatarId());     //6!!!
//                            //a - img
////                            Image avatar = imagesAll.stream().filter(img -> img.getId() == friend.getAvatarId()).findFirst().get();
////                            System.out.println("auth: avatarFriendName="+avatar.getImageName());
////                            //b
////                            System.out.println("img="+imagesAll.stream().filter(img->img.getId()==friend.getAvatarId()).findFirst().get().getImageName());//???
//
// //                            if(friend.getId() == person.getId())                 //для list<String> messages - delete
////                                System.out.println("msgIn="+friend.getMessages().stream().findFirst().get());
//
//                                                                    //для Map<String, String> messages;
////                            friend.getMessages().forEach((k, v)-> System.out.println(k+" = " + v));    //!?
////                            System.out.println("msgOut="+friend.getMessages().keySet().stream().collect(Collectors.joining()));//не работ. в EL!?
////                            System.out.println("msgOut="+ friend.getMessages().keySet().stream().reduce(", ", (n, p)->p.concat(n)));    //!!-но еще не то
//
////                            System.out.println("msgOut="+
//                            List<String> outMessages =  friend.getMessages().keySet().stream().collect(Collectors.toList());
//                            for (String s: outMessages
//                                 ) {
//                                System.out.println("msgOut="+s);
//                            }
//                            System.out.println("msgIn="+friend.getMessages().get(""));
//
//                        }


                        List<Person> candidats = FriendService.findCandidats(Math.toIntExact(person.getId()));
                        session.setAttribute("candidats", candidats);
//                      candidats.forEach(c-> System.out.println("auth:"+c.getUsername()));


                        response.sendRedirect("mainForUser.jsp");
                    }
                }
            }
        } catch (DaoException e) {
            LOGGER.error(e);
            response.sendRedirect("errorPage.jsp");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("doGet!!!!!!!:");
    }


}
