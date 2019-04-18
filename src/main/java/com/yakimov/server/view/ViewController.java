package com.yakimov.server.view;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ViewController {
    @FXML
    private ListView<String> userList;

    @FXML
    private TextArea consoleInputArea;

    @FXML
    private TextArea consoleOutputArea;

    @FXML
    private TextArea chatArea;

    private ObservableList<String> commandQueue = FXCollections.observableArrayList();


    @FXML
    protected void consoleInputKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            commandQueue.add(consoleInputArea.getText());
            consoleInputArea.setText("");
        }
    }


    public void initialize() {
        consoleOutputArea.setText("Test\n");
    }

    void addUser(String user) {
        userList.getItems().add(user);
    }

    void removeUser(String user) {
        userList.getItems().remove(user);
    }

    void addCommandListener(ListChangeListener<String> listener) {
        commandQueue.addListener(listener);
    }

    public void removeCommandListener(ListChangeListener<String> listener) {
        commandQueue.removeListener(listener);
    }

    public void print(String text) {
        consoleOutputArea.appendText(text);
    }

    public void printOnChat(String text) {
        chatArea.appendText(text);
    }
}
