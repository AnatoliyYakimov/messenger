package com.yakimov.server.model;

import com.yakimov.client.TestClient;
import com.yakimov.server.model.entities.Message;

import java.util.StringTokenizer;

class CommandHandler {
    private static final String helpMsg = "helpmessage";

    static String handleCommand(String command) {
        String response = null;
        StringTokenizer tokens = new StringTokenizer(command, " \n");
        switch (tokens.nextToken()) {
            case "disconnect":
                response = disconnect(tokens);
                break;
            case "broadcast":
                response = null;
                broadcast(tokens);
                break;
            case "help":
                response = helpMsg;
                break;
            case "shutdown":
                System.exit(0);
                break;
            case "startTest":
                TestClient.initTest();
                break;
            default:
                response = "Wrong command. Type \"help\" to see all commands availible";
        }
        return response;
    }

    private static void broadcast(StringTokenizer tokens) {
        StringBuilder text = new StringBuilder();
        while (tokens.hasMoreTokens()) {
            text.append(tokens.nextToken("\n\""));
        }
        PostingService.broadcast(new Message("Server", text.toString(), Message.Type.TEXT));
    }

    private static String disconnect(StringTokenizer tokens) {
        StringBuilder response = null;
        while (tokens.hasMoreTokens()) {
            String user = tokens.nextToken();
            if (!OnlineManager.disconnectUser(user)) {
                if (response == null) {
                    response = new StringBuilder("No such users online: ");
                }
                response.append(user).append(" ");
            }
        }
        return response == null ? null : response.toString();
    }
}
