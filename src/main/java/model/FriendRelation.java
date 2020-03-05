package model;

import java.io.Serializable;

public class FriendRelation extends BaseDBEntity implements Serializable {

    private int personId;
    private String message;
    private int friendId;

    public FriendRelation() {   }

    public FriendRelation(int personId, String message, int friendId) {
        this.personId = personId;
        this.message = message;
        this.friendId = friendId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }


    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }
}
