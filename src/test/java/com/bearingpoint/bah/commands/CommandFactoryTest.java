package com.bearingpoint.bah.commands;

import com.bearingpoint.bah.datasource.DataSource;
import com.bearingpoint.bah.datasource.RuntimeDataSource;
import com.bearingpoint.bah.hero.Hero;
import com.bearingpoint.bah.server.Lobby;
import com.bearingpoint.bah.server.Marketplace;
import com.bearingpoint.bah.unit.Archer;
import com.bearingpoint.bah.unit.Cavalry;
import com.bearingpoint.bah.unit.Footman;
import com.bearingpoint.bah.unit.Unit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommandFactoryTest {
    private CommandFactory commandFactory;

    @Before
    public void setUp(){
        Unit footman = new Footman();
        Unit archer = new Archer();
        Unit cavalry = new Cavalry();

        Marketplace marketplace = new Marketplace();
        marketplace.put(footman);
        marketplace.put(archer);
        marketplace.put(cavalry);

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

        commandFactory = new CommandFactory(dataSource, lobby, marketplace);
    }

    @After
    public void tearDown(){
    }

    @Test
    public void createLongBuyCommand(){
        Command expectedCommand = commandFactory.createCommand(null, "buy", null);
        assertEquals("BuyCommand", expectedCommand.getClass().getSimpleName());
    }
    @Test
    public void createShortBuyCommand(){
        Command expectedCommand = commandFactory.createCommand(null, "b", null);
        assertEquals("BuyCommand", expectedCommand.getClass().getSimpleName());
    }

    @Test
    public void createLongSellCommand(){
        Command expectedCommand = commandFactory.createCommand( "sell", null);
        assertEquals("SellCommand", expectedCommand.getClass().getSimpleName());
    }
    @Test
    public void createShortSellCommand(){
        Command expectedCommand = commandFactory.createCommand("s", null);
        assertEquals("SellCommand", expectedCommand.getClass().getSimpleName());
    }

    @Test
    public void createLongPriceCommand(){
        Command expectedCommand = commandFactory.createCommand("price", null);
        assertEquals("PriceCommand", expectedCommand.getClass().getSimpleName());
    }
    @Test
    public void createShortPriceCommand(){
        Command expectedCommand = commandFactory.createCommand("p", null);
        assertEquals("PriceCommand", expectedCommand.getClass().getSimpleName());
    }

    @Test
    public void createLongVaultCommand(){
        Command expectedCommand = commandFactory.createCommand( "vault", null);
        assertEquals("VaultCommand", expectedCommand.getClass().getSimpleName());
    }
    @Test
    public void createShortVaultCommand(){
        Command expectedCommand = commandFactory.createCommand("v", null);
        assertEquals("VaultCommand", expectedCommand.getClass().getSimpleName());
    }

    @Test
    public void createLongTacticsCommand(){
        Command expectedCommand = commandFactory.createCommand("tactics", null);
        assertEquals("TacticsCommand", expectedCommand.getClass().getSimpleName());
    }
    @Test
    public void createShortTacticsCommand(){
        Command expectedCommand = commandFactory.createCommand( "t", null);
        assertEquals("TacticsCommand", expectedCommand.getClass().getSimpleName());
    }

    @Test
    public void createLongCreateCommand(){
        Command expectedCommand = commandFactory.createCommand("create", null);
        assertEquals("CreateCommand", expectedCommand.getClass().getSimpleName());
    }
    @Test
    public void createShortCreateCommand(){
        Command expectedCommand = commandFactory.createCommand( "c", null);
        assertEquals("CreateCommand", expectedCommand.getClass().getSimpleName());
    }

    @Test
    public void createLongLoginCommand(){
        Command expectedCommand = commandFactory.createCommand("login", null);
        assertEquals("LoginCommand", expectedCommand.getClass().getSimpleName());
    }
    @Test
    public void createShortLoginCommand(){
        Command expectedCommand = commandFactory.createCommand("l", null);
        assertEquals("LoginCommand", expectedCommand.getClass().getSimpleName());
    }
}