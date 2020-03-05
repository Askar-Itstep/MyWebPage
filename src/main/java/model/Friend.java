package model;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class Friend extends Person {
    private Map<String, String> messages = new HashMap<>();

    public Friend(Map<String, String> messages) {
        this.messages = messages;
    }

    public Friend(String username, int age, String credo, String password, int avatarId, int roleId, String login, Date dateReg,
                  Date dateDel, Map<String, String> messages) {
        super(username, age, credo, password, avatarId, roleId, login, dateReg, dateDel);
        this.messages = messages;
    }

    public Friend(String username, String password, String login, Map<String, String> messages) {
        super(username, password, login);
        this.messages = messages;
    }

    public Friend(Person person){
        super( person.getUsername(), person.getAge(), person.getCredo(), person.getPassword(), person.getAvatarId(), person.getRoleId(),
                person.getLogin(), person.getDateReg(), person.getDateDel());
    }
    public Map<String, String> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, String> messages) {
        this.messages = messages;
    }

    public void putMessages(String message1, String message2){

        messages.put(message1, message2);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Friend)) return false;  //getClass() != obj.getClass()
        Friend that = (Friend) obj;
        return this.getId() == that.getId() &&
                that.getUsername().equals(that.getUsername());
    }

    @Override
    public int hashCode() {
        int code =17;
        int hashCode = (int) ((31 * 17 + getUsername().hashCode()) + (31*17 + getId()));
        return hashCode;
    }

//    @Override
//    public int compareTo(Friend o) {
//        System.out.println("!!!"); //////////////////////////////////////////////
//        return getId().compareTo(o.getId());
//    }
}
