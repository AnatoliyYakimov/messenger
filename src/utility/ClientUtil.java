package utility;

import server.Message;

public class ClientUtil {
    public static String formatMessage(Message msg){
        return new String("[" + msg.getLogin() + "]: " + msg.getMessage() + "\n");
    }
}
