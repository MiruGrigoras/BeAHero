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

public class CLIClientBuyTest {
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
    public void BuyClientUnsuccessfulParametresNull() {
        String response = server.process("buy", hero);
        String expected = "BE a Hero: (b)uy - buys the specified number of units. Syntax: buy NUMBER UNIT";
        assertEquals(expected, client.processReply(response));
    }

    @Test
    public void BuyClientUnsuccessfulTooExpensive() {
        hero.setGold(100);
        String response = server.process("buy 10 archer", hero);
        String expected = "BE a Hero: archer: 10 of archer units are too expensive for you. Try less!";
        assertEquals(expected, client.processReply(response));
    }

    @Test
    public void BuyClientUnsuccessfulUnitUnavailable() {
        String response = server.process("buy 10 horse", hero);
        String expected = "BE a Hero: horse is not available.";
        assertEquals(expected, client.processReply(response));
    }

    @Test
    public void BuyClientUnSuccessfulNoSpaceAvailable() {
        Unit[] unit = new Unit[6];
        unit[0] = new Archer();
        unit[0].setWarriorsNumber(20);
        unit[1] = new Archer();
        unit[1].setWarriorsNumber(10);
        unit[2] = new Archer();
        unit[2].setWarriorsNumber(15);
        unit[4] = new Archer();
        unit[4].setWarriorsNumber(2);
        unit[3] = new Footman();
        unit[3].setWarriorsNumber(10);
        unit[5] = new Footman();
        unit[5].setWarriorsNumber(20);
        hero.setUnits(unit);
        String response = server.process("buy 10 cavalry", hero);
        String expected = "BE a Hero: Not enough room to insert cavalry units into your army. Try using tactics or sell commands!";
        assertEquals(expected, client.processReply(response));
    }

    @Test
    public void BuyClientSuccessful() {
        String response = server.process("buy 10 archer", hero);
        String expected = "BE a Hero: 10 of archer units join your army.";
        assertEquals(expected, client.processReply(response));
    }
    @Test
    public void BuyClientSuccessfulShortcutName() {
        String response = server.process("b 10 archer", hero);
        String expected = "BE a Hero: 10 of archer units join your army.";
        assertEquals(expected, client.processReply(response));
    }
}