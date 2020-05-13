package com.bearingpoint.bah.commands;

import com.bearingpoint.bah.hero.Hero;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class VaultCommandTest {
    private VaultCommand vaultCommand = new VaultCommand();
    private Hero hero = new Hero("heroID");

    @Before
    public void setUp() {
        vaultCommand.setHero(hero);
        vaultCommand.setParameters(Collections.emptyList());
    }
 
    @After
    public void tearDown() {
    }

    @Test
    public void VaultSuccessfulTestWithDefaultGold() {
        String result = vaultCommand.execute();
        assertEquals("vault.success 5000", result);
    }

    @Test
    public void VaultSuccessfulTestWithPresetZeroGold() {
        hero.setGold(0);
        vaultCommand.setHero(hero);
        String result = vaultCommand.execute();
        assertEquals("vault.success 0", result);
    }

    @Test
    public void VaultUnsuccessfulParametresNotNull() {
        vaultCommand.setParameters(Collections.singletonList("123"));
        String result = vaultCommand.execute();
        assertEquals("vault.help", result);
    }

}
