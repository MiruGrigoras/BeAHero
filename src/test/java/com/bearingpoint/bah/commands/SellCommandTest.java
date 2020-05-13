package com.bearingpoint.bah.commands;

import com.bearingpoint.bah.hero.Hero;
import com.bearingpoint.bah.unit.Archer;
import com.bearingpoint.bah.unit.Unit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class SellCommandTest {
    private SellCommand sellCommand = new SellCommand();
    private Hero hero;

    @Before
    public void setUp() {
        hero = new Hero("heroID");
        sellCommand.setHero(hero);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void SellUnsuccessfulParametresNull() {
        sellCommand.setParameters(Collections.emptyList());
        String result = sellCommand.execute();
        assertEquals("sell.help", result);
    }

    @Test
    public void SellUnsuccessfulLessParametres() {
        sellCommand.setParameters(Collections.singletonList("10"));
        String result = sellCommand.execute();
        assertEquals("sell.help", result);
    }

    @Test
    public void SellUnsuccessfulMoreParametres() {
        sellCommand.setParameters(Arrays.asList("10", "archer", "cavalry"));
        String result = sellCommand.execute();
        assertEquals("sell.help", result);
    }

    @Test
    public void SellUnsuccessfulParametresNotCorrect1() {
        sellCommand.setParameters(Arrays.asList("archer", "10"));
        String result = sellCommand.execute();
        assertEquals("sell.help", result);
    }

    @Test
    public void SellUnsuccessfulParametresNotCorrect2() {
        sellCommand.setParameters(Arrays.asList("archer", "archer"));
        String result = sellCommand.execute();
        assertEquals("sell.help", result);
    }

    @Test
    public void SellUnsuccessfulParametresNotCorrect3() {
        sellCommand.setParameters(Arrays.asList("10", "10"));
        String result = sellCommand.execute();
        assertEquals("sell.help", result);
    }


    @Test
    public void SellUnsuccessfulTooExpensive() {
        Unit[] unit = new Unit[6];
        unit[0] = new Archer();
        unit[0].setWarriorsNumber(20);
        hero.setUnits(unit);
        sellCommand.setHero(hero);
        sellCommand.setParameters(Arrays.asList("30", "archer"));
        String result = sellCommand.execute();
        assertEquals("sell.fail.count 30 archer", result);
    }

    @Test
    public void SellUnsuccessfulUnitUnavailable() {
        sellCommand.setParameters(Arrays.asList("10", "horses"));
        String result = sellCommand.execute();
        assertEquals("sell.fail.unit horses", result);
    }

    @Test
    public void SellSuccessfulExtractFromOnePlace() {
        Unit[] unit = new Unit[6];
        unit[0] = new Archer();
        unit[0].setWarriorsNumber(20);
        unit[3] = new Archer();
        unit[3].setWarriorsNumber(50);
        hero.setUnits(unit);
        sellCommand.setHero(hero);
        sellCommand.setParameters(Arrays.asList("10", "archer"));
        String result = sellCommand.execute() + '\n' + hero.getArmyStatus() + "\nHero gold: " + hero.getGold();
        String expected = "sell.success 10 archer\n" + "Unit{name='archer', price=120, warriorsNumber=20}\n" +
                "null\n" +
                "null\n" +
                "Unit{name='archer', price=120, warriorsNumber=40}\n" +
                "null\n" +
                "null\n" +
                "Hero gold: 6200";
        assertEquals(expected, result);
    }

    @Test
    public void SellSuccessfulExtractFromMorePlaces() {
        Unit[] unit = new Unit[6];
        unit[0] = new Archer();
        unit[0].setWarriorsNumber(2);
        unit[3] = new Archer();
        unit[3].setWarriorsNumber(7);
        unit[5] = new Archer();
        unit[5].setWarriorsNumber(7);
        hero.setUnits(unit);
        sellCommand.setHero(hero);
        sellCommand.setParameters(Arrays.asList("10", "archer"));
        String result = sellCommand.execute() + '\n' + hero.getArmyStatus() + "\nHero gold: " + hero.getGold();
        String expected = "sell.success 10 archer\n" + "Unit{name='archer', price=120, warriorsNumber=2}\n" +
                "null\n" +
                "null\n" +
                "null\n" +
                "null\n" +
                "Unit{name='archer', price=120, warriorsNumber=4}\n" +
                "Hero gold: 6200";
        assertEquals(expected, result);
    }

}
