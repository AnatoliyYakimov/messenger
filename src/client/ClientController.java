package client;

import server.Config;
import server.Message;
import utility.ClientUtil;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;


class ClientController {
    ClientGUI client;
    Socket socket;

    ClientController(ClientGUI client){
        this.client = client;
        try {
            socket = new Socket("localhost", Config.PORT);
        }
        catch(IOException e){
            System.err.println(e.toString());
        }
    }

    Message getMessage(){
        String text = client.getMessagePanel().getText();
        client.getMessagePanel().setText("");
        return new Message(client.getLogin(), text);
    }

    void renderMessage(Message msg){
        String text = client.getChatArea().getText();
        client.getChatArea().setText(text + ClientUtil.formatMessage(msg));
    }


}
