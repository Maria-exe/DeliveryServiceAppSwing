package model;

import java.io.Serializable;

/**
 * @author Tirlea Maria Cristina
 * class used to model the users of the application
 */
public class User implements Serializable {
    public enum Type {ADMIN, EMPLOYEE, CLIENT};
    private String email;
    private String password;
    private String name;
    private Type userType;
    private int UID;
    public User() {};

    public User(int uid,String email, String password, String name, Type userType) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.userType = userType;
        this.UID = uid;
    }

    public User(int uid,String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.UID = uid;
    }
    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getUserType() {
        return userType;
    }

    public void setUserType(Type userType) {
        this.userType = userType;
    }

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

}
