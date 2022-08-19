package com.bank.controller.command;

import com.bank.controller.command.impl.*;
import com.bank.controller.command.impl.admin.UnlockCommand;
import com.bank.controller.command.impl.client.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Contains all commands
 * Change name!
 * */
public class CommandContainer {

    private static CommandContainer instance;
    private static final Map<String, Command> commands;
    public void put(String login, Command command){
        commands.put(login,command);
    }

    static {
        commands = new HashMap<>();
    }
    public CommandContainer() {

    }

    public synchronized static CommandContainer getInstance() {
        if(Objects.isNull(instance))
            instance = new CommandContainer();
        return instance;
    }

    public Command getCommand(String path) {
        if(!commands.containsKey(path))
            return commands.get("/login");
        return commands.get(path);
    }
}
