package com.yakimov.server.utility;

import com.yakimov.server.model.entities.Message;

import java.text.SimpleDateFormat;

public class MessageFormatter {
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("hh:mm");

    public static String formatMessage(Message msg) {
        return "[" + dateFormatter.format(msg.getTime()) + "]: " + msg.getLogin() + " :>> " + msg.getMessage();
    }
}
