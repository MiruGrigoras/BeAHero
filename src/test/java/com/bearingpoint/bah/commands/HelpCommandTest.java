package com.bearingpoint.bah.commands;

import com.bearingpoint.bah.hero.Hero;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class HelpCommandTest {
	private HelpCommand helpCommand = new HelpCommand();

	@Before
	public void initialize() {
		Hero hero = new Hero("heroId");
		helpCommand.setHero(hero);
	}

	@Test
	public void offlineHelpTest() {
		helpCommand.setHero(null);
		helpCommand.setParameters(Collections.emptyList());
		String actual = helpCommand.execute();
		assertEquals("offline.help", actual);
	}

	@Test
	public void devHelpTest() {
		helpCommand.setParameters(Collections.emptyList());
		String actual = helpCommand.execute();
		assertEquals("dev.help", actual);
	}

	@Test
	public void createHelpTest() {
		helpCommand.setParameters(Collections.singletonList("create"));
		String actual = helpCommand.execute();
		assertEquals("create.help", actual);
	}

	@Test
	public void loginHelpTest() {
		helpCommand.setParameters(Collections.singletonList("login"));
		String actual = helpCommand.execute();
		assertEquals("login.help", actual);
	}

	@Test
	public void helpHelpTest() {
		helpCommand.setParameters(Collections.singletonList("help"));
		String actual = helpCommand.execute();
		assertEquals("help.help", actual);
	}

	@Test
	public void exitHelpTest() {
		helpCommand.setParameters(Collections.singletonList("exit"));
		String actual = helpCommand.execute();
		assertEquals("exit.help", actual);
	}

	@Test
	public void vaultHelpTest() {
		helpCommand.setParameters(Collections.singletonList("vault"));
		String actual = helpCommand.execute();
		assertEquals("vault.help", actual);
	}

	@Test
	public void priceHelpTest() {
		helpCommand.setParameters(Collections.singletonList("price"));
		String actual = helpCommand.execute();
		assertEquals("price.help", actual);
	}

	@Test
	public void buyHelpTest() {
		helpCommand.setParameters(Collections.singletonList("buy"));
		String actual = helpCommand.execute();
		assertEquals("buy.help", actual);
	}

	@Test
	public void sellHelpTest() {
		helpCommand.setParameters(Collections.singletonList("sell"));
		String actual = helpCommand.execute();
		assertEquals("sell.help", actual);
	}

	@Test
	public void tacticsHelpTest() {
		helpCommand.setParameters(Collections.singletonList("tactics"));
		String actual = helpCommand.execute();
		assertEquals("tactics.help", actual);
	}

	@Test
	public void toManyParametersHelpTest() {
		helpCommand.setParameters(Arrays.asList("create", "login"));
		String actual = helpCommand.execute();
		assertEquals("incorrect.help", actual);
	}

	@Test
	public void toManyInvalidParametersHelpTest() {
		helpCommand.setParameters(Arrays.asList("a", "b", "c"));
		String actual = helpCommand.execute();
		assertEquals("incorrect.help", actual);
	}

}
