package edu.scu.engr.acm.locationater.util;

/**
 * Created by lauren on 1/25/14.
 */
public class Sharing {

    private long id;
    private long nodeid;
    private String firstname;
    private String lastname;
    private String email;
    private String message;
    private long distance;
    private long velocity;
    private long eta;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getnodeid() {
        return id;
    }

    public void setnodeid(long nodeid) {
        this.nodeid = nodeid;
    }

    public String getfirstname() {
        return firstname;
    }

    public void setfirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getlastname() {
        return lastname;
    }

    public void setlastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public long getVelocity() {
        return id;
    }

    public void setVelocity(long velocity) {
        this.velocity = velocity;
    }

    public long getETA() {
        return eta;
    }

    public void setETA(long eta) {
        this.eta = eta;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return message;
    }
}
