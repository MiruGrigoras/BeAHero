package com.bearingpoint.bah.datasource;

import com.bearingpoint.bah.hero.Hero;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

public class RuntimeDataSourceTest {
    private RuntimeDataSource runtimeDataSource = new RuntimeDataSource();
    private Map<Hero, String> heroes = new HashMap<>();

    Hero hero1 = new Hero("hero1");
    Hero hero2 = new Hero("hero2");

    @Before
    public void setUp() {
        heroes.put(hero1, "pass1");
        heroes.put(hero2, "pass2");
        runtimeDataSource.addHero(hero1, "pass1");
        runtimeDataSource.addHero(hero2, "pass2");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void addHeroTest(){
        Assert.assertEquals(runtimeDataSource.getHeroes(), heroes);
    }

    @Test
    public void checkGoodPasswordTest(){
        Assert.assertTrue(runtimeDataSource.checkPassword(hero1, "pass1"));
    }

    @Test
    public void checkWrongPasswordTest(){
        Assert.assertFalse(runtimeDataSource.checkPassword(hero1, "pass2"));
    }

    @Test
    public void getHeroTest(){
        Hero hero = new Hero("hero1");
        Assert.assertEquals(hero, runtimeDataSource.getHero("hero1"));
    }

    @Test
    public void createHeroSuccessfulTest(){
        Assert.assertNotNull(runtimeDataSource.create("hero3", "pass3"));
    }

    @Test
    public void createHeroUnsuccessfulNullIDTest(){
        Assert.assertNull(runtimeDataSource.create("", "pass3"));
    }

    @Test
    public void createHeroUnsuccessfulNullPasswordTest(){
        Assert.assertNull(runtimeDataSource.create("hero3", ""));
    }

    @Test
    public void createHeroUnsuccessfulExistingIDTest(){
        Assert.assertNull(runtimeDataSource.create("hero1", "pass3"));
    }

    @Test
    public void loginHeroSuccessfulTest(){
        Assert.assertNotNull(runtimeDataSource.login("hero1", "pass1"));
    }

    @Test
    public void loginHeroUnsuccessfulNullIDTest(){
        Assert.assertNull(runtimeDataSource.login("", "pass3"));
    }

    @Test
    public void loginHeroUnsuccessfulNullPasswordTest(){
        Assert.assertNull(runtimeDataSource.login("hero3", ""));
    }

    @Test
    public void loginHeroUnsuccessfulWrongPasswordTest(){
        Assert.assertNull(runtimeDataSource.login("hero1", "pass3"));
    }
}
