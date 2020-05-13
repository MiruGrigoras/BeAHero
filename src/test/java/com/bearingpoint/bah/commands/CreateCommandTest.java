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

public class CreateCommandTest {
    private CreateCommand createCommand = new CreateCommand();

    @Before
    public void setUp(){

        Hero hero1 = new Hero("hero1");
        Hero hero2 = new Hero("hero2");
        Hero hero3 = new Hero("hero3");
        Hero hero4 = new Hero("hero4");

        DataSource dataSource = new RuntimeDataSource();
        dataSource.addHero(hero1, "hero1");
        dataSource.addHero(hero2, "hero2");
        dataSource.addHero(hero3, "hero3");
        dataSource.addHero(hero4, "hero4");

        Lobby lobby = new Lobby();
        lobby.add(hero2);
        lobby.add(hero4);

        CommandFactory commandFactory = new CommandFactory(dataSource, lobby, null);

        createCommand.setCommandFactory(commandFactory);
        createCommand.setDataSource(dataSource);
        createCommand.setLobby(lobby);
    }

    @After
    public void tearDown(){
    }

    @Test
    public void CreateUnsuccessfulNoParameters() {
        createCommand.setParameters(Collections.emptyList());
        String result = createCommand.execute();
        assertEquals("create.help", result);
    }

    @Test
    public void CreateUnsuccessfulLessParameters() {
        createCommand.setParameters(Collections.singletonList("hero5"));
        String result = createCommand.execute();
        assertEquals("create.help", result);
    }

    @Test
    public void CreateUnsuccessfulMoreParameters() {

        createCommand.setParameters(Arrays.asList("hero5", "pass1","pass2"));
        String result = createCommand.execute();
        assertEquals("create.help", result);
    }

    @Test
    public void CreateUnsuccessfulUsernameTaken() {
        createCommand.setParameters(Arrays.asList("hero3", "hero3"));
        String result = createCommand.execute();
        assertEquals("create.fail.user.exists hero3", result);
    }

    @Test
    public void CreateUnsuccessfulIllegalCharacters() {
        createCommand.setParameters(Arrays.asList("hero@#$%^", "heropass"));
        String result = createCommand.execute();
        assertEquals("create.fail.user.regex hero@#$%^", result);
    }

    @Test
    public void CreateSuccessful() {
        createCommand.setParameters(Arrays.asList("hero_5", "hero5"));
        String result = createCommand.execute();
        assertEquals("create.success hero_5&development&login.success hero_5", result);
    }
}