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

public class CLIClientTacticsTest {
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
    public void TacticsClientUnsuccessfulLessThan6Parametres() {
        String response = server.process("tactics 10 a free", hero);
        String expected = "BE a Hero: (t)actics:\n" +
                "\t- displays the current armies of your hero - total number of units per type and unit division per strategic position. Syntax: tactics\n" +
                "\t- divides the existing armies as specified by your hero. Syntax: tactics free/NUMBER UNIT ... free/NUMBER UNIT";
        assertEquals(expected, client.processReply(response));
    }

    @Test
    public void TacticsClientUnsuccessfulUnitNotAvailable() {
        Unit[] unit = new Unit[6];
        unit[0] = new Archer();
        unit[0].setWarriorsNumber(20);
        unit[1] = new Archer();
        unit[1].setWarriorsNumber(10);
        unit[2] = new Archer();
        unit[2].setWarriorsNumber(15);
        unit[3] = new Footman();
        unit[3].setWarriorsNumber(10);
        unit[4] = new Archer();
        unit[4].setWarriorsNumber(5);
        unit[5] = new Footman();
        unit[5].setWarriorsNumber(20);
        hero.setUnits(unit);
        String response = server.process("tactics free 30 archer 10 cavalry 30 footman 20 archer free", hero);
        String expected = "BE a Hero: cavalry is not available.";
        assertEquals(expected, client.processReply(response));
    }

    @Test
    public void TacticsClientUnsuccessfulSumIncorrect() {
        Unit[] unit = new Unit[6];
        unit[0] = new Archer();
        unit[0].setWarriorsNumber(20);
        unit[1] = new Archer();
        unit[1].setWarriorsNumber(10);
        unit[2] = new Archer();
        unit[2].setWarriorsNumber(15);
        unit[3] = new Footman();
        unit[3].setWarriorsNumber(10);
        unit[4] = new Archer();
        unit[4].setWarriorsNumber(5);
        unit[5] = new Footman();
        unit[5].setWarriorsNumber(20);
        hero.setUnits(unit);
        String response = server.process("tactics free 20 archer free 30 footman 20 archer free", hero);
        String expected = "BE a Hero: 40 of archer units not available.";
        assertEquals(expected, client.processReply(response));
    }

    @Test
    public void TacticsClientUnsuccessfulSimpleCommandNoExistingUnits() {
        String response = server.process("tactics", hero);
        String expected = "BE a Hero: No unit owned. Try using a buy command!";
        assertEquals(expected, client.processReply(response));
    }

    @Test
    public void TacticsClientSuccessfulSimpleCommandNoNullField() {
        Unit[] unit = new Unit[6];
        unit[0] = new Archer();
        unit[0].setWarriorsNumber(20);
        unit[1] = new Archer();
        unit[1].setWarriorsNumber(10);
        unit[2] = new Archer();
        unit[2].setWarriorsNumber(15);
        unit[3] = new Footman();
        unit[3].setWarriorsNumber(10);
        unit[4] = new Archer();
        unit[4].setWarriorsNumber(5);
        unit[5] = new Footman();
        unit[5].setWarriorsNumber(20);
        hero.setUnits(unit);
        String response = server.process("tactics", hero);
        String expected = "BE a Hero: footman: 30 units\n" +
                "archer: 50 units\n" +
                "1: 20 archer units\n" +
                "2: 10 archer units\n" +
                "3: 15 archer units\n" +
                "4: 10 footman units\n" +
                "5: 5 archer units\n" +
                "6: 20 footman units";
        assertEquals(expected, client.processReply(response));
    }

    @Test
    public void TacticsClientSuccessfulSimpleCommandNullFields() {
        Unit[] unit = new Unit[6];
        unit[0] = new Archer();
        unit[0].setWarriorsNumber(20);
        unit[1] = new Archer();
        unit[1].setWarriorsNumber(10);
        unit[2] = null;
        unit[3] = new Footman();
        unit[3].setWarriorsNumber(10);
        unit[4] = new Archer();
        unit[4].setWarriorsNumber(5);
        unit[5] = null;
        hero.setUnits(unit);
        String response = server.process("tactics", hero);
        String expected = "BE a Hero: footman: 10 units\n" +
                "archer: 35 units\n" +
                "1: 20 archer units\n" +
                "2: 10 archer units\n" +
                "3: FREE\n" +
                "4: 10 footman units\n" +
                "5: 5 archer units\n" +
                "6: FREE";
        assertEquals(expected, client.processReply(response));
    }

    @Test
    public void TacticsClientSuccessfulSimpleCommandNullFieldsShortcutName() {
        Unit[] unit = new Unit[6];
        unit[0] = new Archer();
        unit[0].setWarriorsNumber(20);
        unit[1] = new Archer();
        unit[1].setWarriorsNumber(10);
        unit[2] = null;
        unit[3] = new Footman();
        unit[3].setWarriorsNumber(10);
        unit[4] = new Archer();
        unit[4].setWarriorsNumber(5);
        unit[5] = null;
        hero.setUnits(unit);
        String response = server.process("t", hero);
        String expected = "BE a Hero: footman: 10 units\n" +
                "archer: 35 units\n" +
                "1: 20 archer units\n" +
                "2: 10 archer units\n" +
                "3: FREE\n" +
                "4: 10 footman units\n" +
                "5: 5 archer units\n" +
                "6: FREE";
        assertEquals(expected, client.processReply(response));
    }

    @Test
    public void TacticsClientSuccessfulComplexCommand() {
        Unit[] unit = new Unit[6];
        unit[0] = new Archer();
        unit[0].setWarriorsNumber(20);
        unit[1] = new Archer();
        unit[1].setWarriorsNumber(10);
        unit[2] = null;
        unit[3] = new Footman();
        unit[3].setWarriorsNumber(10);
        unit[4] = new Archer();
        unit[4].setWarriorsNumber(5);
        unit[5] = null;
        hero.setUnits(unit);
        String response = server.process("tactics 5 footman free 10 archer 20 archer 5 footman 5 archer", hero) + '\n' + hero.getArmyStatus();
        String expected = "BE a Hero: Tactic arrangements were executed successfully!";
        assertEquals(expected, client.processReply(response));
    }
    @Test
    public void TacticsClientSuccessfulComplexCommandShortcutName() {
        Unit[] unit = new Unit[6];
        unit[0] = new Archer();
        unit[0].setWarriorsNumber(20);
        unit[1] = new Archer();
        unit[1].setWarriorsNumber(10);
        unit[2] = null;
        unit[3] = new Footman();
        unit[3].setWarriorsNumber(10);
        unit[4] = new Archer();
        unit[4].setWarriorsNumber(5);
        unit[5] = null;
        hero.setUnits(unit);
        String response = server.process("t 5 footman free 10 archer 20 archer 5 footman 5 archer", hero) + '\n' + hero.getArmyStatus();
        String expected = "BE a Hero: Tactic arrangements were executed successfully!";
        assertEquals(expected, client.processReply(response));
    }
}