package com.yakimov.server.view;

import javafx.collections.ListChangeListener;

public class ViewInterface {
    private ViewController controller;


    public ViewInterface(ViewUI ui) {
        controller = ui.getController();
    }

    public void addUser(String user) {
        controller.addUser(user);
    }

    public void removeUser(String user) {
        controller.removeUser(user);
    }

    public void addCommandListener(ListChangeListener<String> listener) {
        controller.addCommandListener(listener);
    }

    public void removeCommandListener(ListChangeListener<String> listener) {
        controller.addCommandListener(listener);
    }

    public void printOnConsole(String text) {
        controller.print(text);
    }

    public void printOnChat(String text) {
        controller.printOnChat(text);
    }
}
