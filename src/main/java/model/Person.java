package model;

import java.io.Serializable;
import java.sql.Date;

//import java.util.Date;

public class Person extends BaseDBEntity implements Serializable {

    private String username;
    private int age;
    private String credo;
    private String password;
    private int avatarId;
    private int roleId;
    private String login;
    private Date dateReg;
    private Date dateDel;
    private String email;
    private String phone;
    private String address;

    public Person(){}

    public Person(String username, int age, String credo, String password, int avatarId, int roleId, String login,
                  Date dateReg, Date dateDel){
        this.username = username;
        this.age=age;
        this.credo=credo;
        this.password=password;
        this.avatarId=avatarId;
        this.roleId=roleId;
        this.login = login;
        this.dateReg = dateReg;
        this.dateDel = dateDel;
    }

    public Person(String username, int age, String credo, String password, int avatarId, int roleId, String login,
                  Date dateReg, Date dateDel, String email, String phone, String address) {
        this.username = username;
        this.age = age;
        this.credo = credo;
        this.password = password;
        this.avatarId = avatarId;
        this.roleId = roleId;
        this.login = login;
        this.dateReg = dateReg;
        this.dateDel = dateDel;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public Person(String username, String password, String login) {
        this.username = username;
        this.password = password;
        this.login = login;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCredo() {
        return credo;
    }

    public void setCredo(String credo) {
        this.credo = credo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Date getDateReg() {
        return dateReg;
    }

    public void setDateReg(Date dateReg) {
        this.dateReg = dateReg;
    }

    public Date getDateDel() {
        return dateDel;
    }

    public void setDateDel(Date dateDel) {
        this.dateDel = dateDel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return getUsername().equals(person.getUsername()) &&
                getPassword().equals(person.getPassword());
    }

    @Override
    public int hashCode() {
        int code =17;
        int hashCode = (int) ((31 * 17 + getUsername().hashCode()) + (31*17 + getId()));
        return hashCode;
    }
}
