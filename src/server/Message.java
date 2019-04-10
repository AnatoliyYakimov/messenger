package server;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class Message implements Serializable {

    private String login;
    private String message;
    private String[] users;
    private Date time;

    /*
    Client-side constructor
     */
    public Message(String login, String message){
        this.login = login;
        this.message = message;
        this.time = java.util.Calendar.getInstance().getTime();
    }

    /*
        Server-side constructor
    */
    Message(String login, String message, String[] users){
        this(login,message);
        this.users = users;
    }

    void setUsers(String[] users){
        this.users = users;
    }

    public String getLogin(){
        return this.login;
    }

    public String getMessage(){
        return this.message;
    }

    String setTime(){
        Time tm = new Time(this.time.getTime());
        return tm.toString();
    }
    String to_string(){
        return message;
    }
}
