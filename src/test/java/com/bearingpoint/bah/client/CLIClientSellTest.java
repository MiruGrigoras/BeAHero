package com.bearingpoint.bah.client;

import com.bearingpoint.bah.commands.CommandFactory;
import com.bearingpoint.bah.hero.Hero;
import com.bearingpoint.bah.server.Marketplace;
import com.bearingpoint.bah.server.Server;
import com.bearingpoint.bah.unit.Archer;
import com.bearingpoint.bah.unit.Cavalry;
import com.bearingpoint.bah.unit.Footman;
import com.bearingpoint.bah.unit.Unit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CLIClientSellTest {
    private Server server;
    private CLIClient client;
    private Hero hero;

    @Before
    public void setUp(){
        server = new Server();
        client = new CLIClient();
        client.init();
        hero = new Hero("heroID");
        Unit footman = new Footman();
        Unit archer = new Archer();
        Unit cavalry = new Cavalry();

        Marketplace marketplace = new Marketplace();
        marketplace.put(footman);
        marketplace.put(archer);
        marketplace.put(cavalry);

        CommandFactory cf = new CommandFactory(null, null, marketplace);
        server.setCf(cf);
    }

    @After
    public void tearDown(){
    }

    @Test
    public void SellClientUnsuccessfulParametresNull() {
        String response = server.process("sell", hero);
        String expected = "BE a Hero: (s)ell - sells the specified number of units. Syntax: sell NUMBER UNIT";
        assertEquals(expected, client.processReply(response));
    }

    @Test
    public void SellClientUnsuccessfulTooExpensive() {
        Unit[] unit = new Unit[6];
        unit[0] = new Archer();
        unit[0].setWarriorsNumber(20);
        hero.setUnits(unit);
        String response = server.process("sell 30 archer", hero);
        String expected = "BE a Hero: 30 of archer units not available. Try less!";
        assertEquals(expected, client.processReply(response));
    }

    @Test
    public void SellClientUnsuccessfulUnitUnavailable() {
        String response = server.process("sell 10 horse", hero);
        String expected = "BE a Hero: horse is not available.";
        assertEquals(expected, client.processReply(response));
    }

    @Test
    public void SellClientSuccessful() {
        Unit[] unit = new Unit[6];
        unit[0] = new Archer();
        unit[0].setWarriorsNumber(20);
        hero.setUnits(unit);
        String response = server.process("sell 10 archer", hero);
        String expected = "BE a Hero: 10 of archer units left your army.";
        assertEquals(expected, client.processReply(response));
    }
    @Test
    public void SellClientSuccessfulShortcutName() {
        Unit[] unit = new Unit[6];
        unit[0] = new Archer();
        unit[0].setWarriorsNumber(20);
        hero.setUnits(unit);
        String response = server.process("s 10 archer", hero);
        String expected = "BE a Hero: 10 of archer units left your army.";
        assertEquals(expected, client.processReply(response));
    }
}