package com.bearingpoint.bah.commands;

import com.bearingpoint.bah.datasource.DataSource;
import com.bearingpoint.bah.datasource.RuntimeDataSource;
import com.bearingpoint.bah.hero.Hero;
import com.bearingpoint.bah.server.Lobby;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class LoginCommandTest {
    private LoginCommand loginCommand = new LoginCommand();
    private Lobby lobby;
    @Before
    public void setUp() {
        Hero hero1 = new Hero("hero1");
        Hero hero2 = new Hero("hero2");
        Hero hero3 = new Hero("hero3");
        Hero hero4 = new Hero("hero4");

        DataSource dataSource = new RuntimeDataSource();
        dataSource.addHero(hero1, "pass1");
        dataSource.addHero(hero2, "pass2");
        dataSource.addHero(hero3, "pass3");
        dataSource.addHero(hero4, "pass4");

        lobby = new Lobby();
        lobby.add(hero2);
        lobby.add(hero4);

        loginCommand.setDataSource(dataSource);
        loginCommand.setLobby(lobby);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void LoginUnsuccessfulNoParameters() {
        loginCommand.setParameters(Collections.emptyList());
        String response = loginCommand.execute();
        assertEquals("login.help", response);
    }

    @Test
    public void LoginUnsuccessfulLessParameters() {
        loginCommand.setParameters(Collections.singletonList("hero5"));
        String response = loginCommand.execute();
        assertEquals("login.help", response);
    }

    @Test
    public void LoginUnsuccessfulMoreParameters() {
        loginCommand.setParameters(Arrays.asList("hero5", "pass1", "pass2"));
        String response = loginCommand.execute();
        assertEquals("login.help", response);
    }

    @Test
    public void LoginUnsuccessfulInexistentUsername() {
        loginCommand.setParameters(Arrays.asList("hero5", "pass1"));
        String response = loginCommand.execute();
        assertEquals("login.fail", response);
    }

    @Test
    public void LoginUnsuccessfulWrongPassword() {
        loginCommand.setParameters(Arrays.asList("hero4", "pass1"));
        String response = loginCommand.execute();
        assertEquals("login.fail", response);
    }

    @Test
    public void LoginSuccessfulWithLobbyVerification() {
        loginCommand.setParameters(Arrays.asList("hero3", "pass3"));
        String response = loginCommand.execute() + "\n" + lobby.getLobby();
        assertEquals("login.success hero3\nhero2\nhero4\nhero3\n", response);
    }

}