package com.bearingpoint.bah.commands;

import com.bearingpoint.bah.datasource.DataSource;
import com.bearingpoint.bah.hero.Hero;
import com.bearingpoint.bah.server.Lobby;

import java.util.List;

class LoginCommand implements DataSourceCommand, LobbyCommand {

    private List<String> parameters;
    private DataSource dataSource;
    private Lobby lobby;

    public String execute() {
        String result = "login.help";
        if (parameters.size() == 2) {

            String username = parameters.get(0);
            result = "login.success " + username;
            String password = parameters.get(1);
            Hero hero = dataSource.login(username, password);
            if (hero == null) {
                result = "login.fail";
            } else {
                lobby.add(hero);
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
}