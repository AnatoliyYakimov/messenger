package com.yakimov.server.presenter;

import com.yakimov.server.model.ModelInterface;
import com.yakimov.server.model.entities.Client;
import com.yakimov.server.model.entities.Message;
import com.yakimov.server.utility.MessageFormatter;
import com.yakimov.server.view.ViewInterface;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;

public class Presenter {
    private ModelInterface model;
    private ViewInterface view;

    public Presenter(ViewInterface view) {
        this.view = view;
        model = new ModelInterface();
        bindUsers();
        bindCommands();
        bindChat();
    }

    private void bindUsers() {
        model.addUsersListener(new MapChangeListener<String, Client>() {
            @Override
            public void onChanged(Change<? extends String, ? extends Client> change) {
                if (change.wasAdded()) {
                    view.addUser(change.getKey());
                } else {
                    view.removeUser(change.getKey());
                }
            }
        });
    }

    private void bindCommands() {
        view.addCommandListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> change) {
                while (change.next()) {
                    if (change.wasAdded()) {
                        String command = change.getAddedSubList().get(0);
                        String response = model.handleCommand(command);
                        view.printOnConsole(command);
                        if (response != null)
                            view.printOnConsole(response);
                    }
                }
            }
        });
    }

    private void bindChat() {
        model.addMessageListener(new ChangeListener<Message>() {
            @Override
            public void changed(ObservableValue<? extends Message> observableValue, Message message, Message t1) {
                view.printOnChat(MessageFormatter.formatMessage(message) + "\n");
            }
        });
    }


}
