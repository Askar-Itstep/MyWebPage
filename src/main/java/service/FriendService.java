package service;

import dao.FriendsRelationsDao;
import dao.PersonDao;
import exception.DaoException;
import jdbc.JdbcDaoFactory;
import model.Friend;
import model.FriendRelation;
import model.Person;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FriendService {

    private static FriendsRelationsDao friendsRelationsDao = (FriendsRelationsDao) JdbcDaoFactory.getInstance().getDao(FriendsRelationsDao.class);
    private static PersonDao personDao = (PersonDao) JdbcDaoFactory.getInstance().getDao(PersonDao.class);
    private final Logger LOGGER = Logger.getLogger(FriendService.class);
    private static Set<Friend> friends = new HashSet<>();

//persId - парам. в AuthServ.
    public static Set<Friend> findMyFriends(int persId){    //найти друзей актора+прописать все сообщения

//        List<Person> persons = null;
        List<FriendRelation> myOutRelations = null; //исходящие транзакц. актора
        List<FriendRelation> myIncomeRelations=null;    //входящие
        try {
            myOutRelations = friendsRelationsDao.findByIdPers(persId);    //исходящие транзакц. актора-4
            myIncomeRelations = friendsRelationsDao.findByIdFriend(persId); //1

//a) - работа с исходящ. сообщ.
// данн. метод пока использ. выборку по отношениям,                     а в перпект. сначала выбрать всех друзей по флагу (которого еще нет)задаваемого кнопкой
            for (FriendRelation relation: myOutRelations
            ) {
                //  System.out.println("FServ: friend_id="+relation.getFriendId()); //2, 4, 2, 1-id pers для участн. сообщ.
                Person person = personDao.findAll().get(relation.getFriendId()-1);  //вытащить персонажей с такими id

                //созд. списка друзей актора с id == persId    по OUT-отношениям
                //если список друзей еще не содержит кандидата и это не актор -  созд. друга
                if( relation.getFriendId() != persId) {
                    Friend friend = new Friend(person);
                    friend.setId((long) relation.getFriendId());
                    friends.add(friend);
                }


//взять OUT-сообщение из текущего отношения и добав. в мои сообщ. для определенного друга
                friends.stream().filter(fr -> (fr.getId()==relation.getFriendId())).forEach(fr->fr.putMessages(relation.getMessage(), ""));
            }

 //b) - работа со входящ. сообщ.
            for (FriendRelation relation: myIncomeRelations
                 ) {
                friends.stream().filter(fr -> (fr.getId()==relation.getPersonId())).forEach(fr->fr.putMessages("", relation.getMessage()));
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return friends;
    }

    //найти кандидатов в друзья - т.е. все остальные
    public static List<Person> findCandidats(int persId){

        List<Person> candidats = null;
        List<Person> people = null;
        try {
            people = personDao.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }

//кандидат в друзья - это не сам персонаж, не друг и не админ
        candidats = people.stream().filter(p->(p.getId() != persId && !friends.contains(p)&& p.getRoleId() != 1)).collect(Collectors.toList());
        return candidats;
    }
}
