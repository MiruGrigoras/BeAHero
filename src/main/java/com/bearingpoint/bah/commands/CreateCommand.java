package com.bearingpoint.bah.commands;

import com.bearingpoint.bah.datasource.DataSource;
import com.bearingpoint.bah.hero.Hero;
import com.bearingpoint.bah.server.Lobby;

import java.util.List;

class CreateCommand implements DataSourceCommand, LobbyCommand, ChainCommand {

    private List<String> parameters;
    private DataSource dataSource;
    private Lobby lobby;
    private CommandFactory commandFactory;

    public String execute() {
        String result = "create.help";
        if (parameters.size() == 2) {
            result = "create.success";
            String username = parameters.get(0);
            result += " " + username + "&development";
            String password = parameters.get(1);
            if (!(username.matches("[a-zA-Z0-9_-]+"))) {
                result = "create.fail.user.regex " + username;
            } else {
                Hero hero = dataSource.create(username, password);
                if (hero == null) {
                    result = "create.fail.user.exists " + username;
                }
                if (result.equals("create.success " + username + "&development")) {
                    Command loginCommand = commandFactory.createCommand("login", parameters);
                    ((DataSourceCommand) loginCommand).setDataSource(dataSource);
                    ((LobbyCommand) loginCommand).setLobby(lobby);
                    result = result + "&" + loginCommand.execute();
                }
            }
        }
        return result;
    }

    @Override
    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    @Override
    public void setCommandFactory(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }
}