package edu.scu.engr.acm.locationater.util;

/**
 * Created by lauren on 1/25/14.
 */
public class FriendsSetGet {


    private long id;
    private long nodeid;
    private String firstname;
    private String lastname;
    private String email;
    private String message;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getnodeid() {
        return nodeid;
    }

    public void setnodeid(long nodeid) {
        this.nodeid = nodeid;
    }

    public String getfirstname() {
        return firstname;
    }

    public String setfirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getlastname() {
        return lastname;
    }

    public String setlastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public String setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return message;
    }





}
