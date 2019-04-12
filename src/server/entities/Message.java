package server.entities;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class Message implements Serializable {

    private Type type;

    private String login;
    private String message;
    private Date time;
    /*
    Client-side constructor
     */
    public Message(String login, String message, Type type) {
        this.login = login;
        this.message = message;
        this.time = java.util.Calendar.getInstance().getTime();
        this.type = type;
    }

    public Date getTime() {
        return time;
    }

    public String getLogin(){
        return this.login;
    }

    public String getMessage(){
        return this.message;
    }

    public Type getType() {
        return type;
    }

    public String setTime() {
        Time tm = new Time(this.time.getTime());
        return tm.toString();
    }

    public enum Type {
        PING,
        TEXT,
        LOGIN,
        SYSTEM
    }
}
