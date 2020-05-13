package com.bearingpoint.bah.commands;

import com.bearingpoint.bah.hero.Hero;
import com.bearingpoint.bah.unit.Archer;
import com.bearingpoint.bah.unit.Footman;
import com.bearingpoint.bah.unit.Unit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class TacticsCommandTest {
    private TacticsCommand tacticsCommand = new TacticsCommand();
    private Hero hero;

    @Before
    public void setUp() {
        hero = new Hero("heroID");
        tacticsCommand.setHero(hero);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void TacticsUnsuccessfulLessThan6Parametres() {
        tacticsCommand.setParameters(Arrays.asList("10", "a", "free"));
        String result = tacticsCommand.execute();
        assertEquals("tactics.help", result);
    }

    @Test
    public void TacticsUnsuccessfulMoreThan12Parametres() {
        tacticsCommand.setParameters(Arrays.asList("10", "a", "10", "a", "10", "a", "10", "a", "10", "a", "10", "a", "10", "a"));
        String result = tacticsCommand.execute();
        assertEquals("tactics.help", result);
    }

    @Test
    public void TacticsUnsuccessfulUnitNotAvailable() {
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
        tacticsCommand.setHero(hero);
        tacticsCommand.setParameters(Arrays.asList("free", "30", "archer", "10", "cavalry", "30", "footman", "20", "archer", "free"));
        String result = tacticsCommand.execute();
        assertEquals("tactics.fail.unit cavalry", result);
    }

    @Test
    public void TacticsUnsuccessfulSumIncorrect() {
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
        tacticsCommand.setHero(hero);
        tacticsCommand.setParameters(Arrays.asList("free", "20", "archer", "free", "30", "footman", "20", "archer", "free"));
        String result = tacticsCommand.execute();
        assertEquals("tactics.fail.unit.count 40 archer", result);
    }

    @Test
    public void TacticsUnuccessfulSimpleCommandNoExistingUnits() {
        tacticsCommand.setParameters(Collections.emptyList());
        String result = tacticsCommand.execute();
        assertEquals("tactics.fail.nounit", result);
    }

    @Test
    public void TacticsSuccessfulSimpleCommandNoNullField() {
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
        tacticsCommand.setHero(hero);
        tacticsCommand.setParameters(Collections.emptyList());
        String result = tacticsCommand.execute();
        assertEquals("tactics.unit.display 30 footman&tactics.unit.display 50 archer&tactics.slot 1 20 archer&tactics.slot 2 10 archer&tactics.slot 3 15 archer&tactics.slot 4 10 footman&tactics.slot 5 5 archer&tactics.slot 6 20 footman", result);
    }

    @Test
    public void TacticsSuccessfulSimpleCommandNullFields() {
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
        tacticsCommand.setHero(hero);
        tacticsCommand.setParameters(Collections.emptyList());
        String result = tacticsCommand.execute();
        assertEquals("tactics.unit.display 10 footman&tactics.unit.display 35 archer&tactics.slot 1 20 archer&tactics.slot 2 10 archer&tactics.slot.free 3&tactics.slot 4 10 footman&tactics.slot 5 5 archer&tactics.slot.free 6", result);
    }

    @Test
    public void TacticsSuccessfulComplexCommand() {
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
        tacticsCommand.setHero(hero);
        tacticsCommand.setParameters(Arrays.asList("5", "footman", "free", "10", "archer", "20", "archer", "5", "footman", "5", "archer"));
        String result = tacticsCommand.execute() + '\n' + hero.getArmyStatus();
        String expected = "tactics.success\n" +
                "Unit{name='footman', price=100, warriorsNumber=5}\n" +
                "null\n" +
                "Unit{name='archer', price=120, warriorsNumber=10}\n" +
                "Unit{name='archer', price=120, warriorsNumber=20}\n" +
                "Unit{name='footman', price=100, warriorsNumber=5}\n" +
                "Unit{name='archer', price=120, warriorsNumber=5}";
        assertEquals(expected, result);
    }

}