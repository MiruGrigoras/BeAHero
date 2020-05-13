package com.bearingpoint.bah.commands;

import com.bearingpoint.bah.hero.Hero;
import com.bearingpoint.bah.server.Marketplace;
import com.bearingpoint.bah.unit.Archer;
import com.bearingpoint.bah.unit.Cavalry;
import com.bearingpoint.bah.unit.Footman;
import com.bearingpoint.bah.unit.Unit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class BuyCommandTest {
    private BuyCommand buyCommand = new BuyCommand();
    private Hero hero;

    @Before
    public void setUp() {
        hero = new Hero("heroID");

        Unit footman = new Footman();
        Unit archer = new Archer();
        Unit cavalry = new Cavalry();

        Marketplace marketplace = new Marketplace();
        marketplace.put(footman);
        marketplace.put(archer);
        marketplace.put(cavalry);

        buyCommand.setHero(hero);
        buyCommand.setMarketplace(marketplace);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void BuyUnsuccessfulParametresNull() {
        buyCommand.setParameters(Collections.emptyList());
        String result = buyCommand.execute();
        assertEquals("buy.help", result);
    }

    @Test
    public void BuyUnsuccessfulLessParametres() {
        buyCommand.setParameters(Collections.singletonList("10"));
        String result = buyCommand.execute();
        assertEquals("buy.help", result);
    }

    @Test
    public void BuyUnsuccessfulMoreParametres() {
        buyCommand.setParameters(Arrays.asList("10", "archer", "cavalry"));
        String result = buyCommand.execute();
        assertEquals("buy.help", result);
    }

    @Test
    public void BuyUnsuccessfulParametresNotCorrect1() {
        buyCommand.setParameters(Arrays.asList("archer", "10"));
        String result = buyCommand.execute();
        assertEquals("buy.help", result);
    }

    @Test
    public void BuyUnsuccessfulParametresNotCorrect2() {
        buyCommand.setParameters(Arrays.asList("archer", "archer"));
        String result = buyCommand.execute();
        assertEquals("buy.help", result);
    }

    @Test
    public void BuyUnsuccessfulParametresNotCorrect3() {
        buyCommand.setParameters(Arrays.asList("10", "10"));
        String result = buyCommand.execute();
        assertEquals("buy.help", result);
    }


    @Test
    public void BuyUnsuccessfulTooExpensive() {
        hero.setGold(100);
        buyCommand.setHero(hero);
        buyCommand.setParameters(Arrays.asList("10", "archer"));
        String result = buyCommand.execute();
        assertEquals("buy.fail.gold 10 archer", result);
    }

    @Test
    public void BuyUnsuccessfulUnitUnavailable() {
        buyCommand.setParameters(Arrays.asList("10", "horses"));
        String result = buyCommand.execute();
        assertEquals("buy.fail.unit horses", result);
    }


    @Test
    public void BuyUnSuccessfulNoSpaceAvailable() {
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
        buyCommand.setHero(hero);
        buyCommand.setParameters(Arrays.asList("10", "cavalry"));
        String result = buyCommand.execute();
        assertEquals("buy.fail.space cavalry", result);
    }

    @Test
    public void BuySuccessfulUnitExistent1() {
        Unit[] unit = new Unit[6];
        unit[0] = new Archer();
        unit[0].setWarriorsNumber(100);
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
        buyCommand.setHero(hero);
        buyCommand.setParameters(Arrays.asList("10", "archer"));
        String result = buyCommand.execute() + '\n' + hero.getArmyStatus() + "\nHero gold: " + hero.getGold();
        String expected = "buy.success 10 archer\n" + "Unit{name='archer', price=120, warriorsNumber=110}\n" +
                "Unit{name='archer', price=120, warriorsNumber=10}\n" +
                "Unit{name='archer', price=120, warriorsNumber=15}\n" +
                "Unit{name='footman', price=100, warriorsNumber=10}\n" +
                "Unit{name='archer', price=120, warriorsNumber=2}\n" +
                "Unit{name='footman', price=100, warriorsNumber=20}\n" +
                "Hero gold: 3800";
        assertEquals(expected, result);
    }


    @Test
    public void BuySuccessfulUnitExistent2() {
        Unit[] unit = new Unit[6];
        unit[0] = new Archer();
        unit[0].setWarriorsNumber(1);
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
        buyCommand.setHero(hero);
        buyCommand.setParameters(Arrays.asList("10", "archer"));
        String result = buyCommand.execute() + '\n' + hero.getArmyStatus() + "\nHero gold: " + hero.getGold();
        String expected = "buy.success 10 archer\n" + "Unit{name='archer', price=120, warriorsNumber=1}\n" +
                "Unit{name='archer', price=120, warriorsNumber=10}\n" +
                "Unit{name='archer', price=120, warriorsNumber=25}\n" +
                "Unit{name='footman', price=100, warriorsNumber=10}\n" +
                "Unit{name='archer', price=120, warriorsNumber=2}\n" +
                "Unit{name='footman', price=100, warriorsNumber=20}\n" +
                "Hero gold: 3800";
        assertEquals(expected, result);
    }

    @Test
    public void BuySuccessfulUnitNotExistent() {
        Unit[] unit = new Unit[6];
        unit[0] = new Archer();
        unit[0].setWarriorsNumber(1);
        unit[1] = new Archer();
        unit[1].setWarriorsNumber(10);
        unit[2] = null;
        unit[4] = new Archer();
        unit[4].setWarriorsNumber(2);
        unit[3] = new Footman();
        unit[3].setWarriorsNumber(10);
        unit[5] = null;
        hero.setUnits(unit);
        buyCommand.setHero(hero);
        buyCommand.setParameters(Arrays.asList("10", "cavalry"));
        String result = buyCommand.execute() + '\n' + hero.getArmyStatus() + "\nHero gold: " + hero.getGold();
        String expected = "buy.success 10 cavalry\n" + "Unit{name='archer', price=120, warriorsNumber=1}\n" +
                "Unit{name='archer', price=120, warriorsNumber=10}\n" +
                "null\n" +
                "Unit{name='footman', price=100, warriorsNumber=10}\n" +
                "Unit{name='archer', price=120, warriorsNumber=2}\n" +
                "Unit{name='cavalry', price=200, warriorsNumber=10}\n" +
                "Hero gold: 3000";
        assertEquals(expected, result);
    }
}
