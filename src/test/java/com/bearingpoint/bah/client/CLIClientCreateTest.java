package com.bearingpoint.bah.client;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bearingpoint.bah.commands.CommandFactory;
import com.bearingpoint.bah.datasource.DataSource;
import com.bearingpoint.bah.datasource.RuntimeDataSource;
import com.bearingpoint.bah.hero.Hero;
import com.bearingpoint.bah.server.Lobby;

public class CLIClientCreateTest {
	private CLIClient client = new CLIClient();

	@Before
	public void setUp() {
		client.init();
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

		CommandFactory cf = new CommandFactory(dataSource, lobby, null);
	}

	@After
	public void tearDown() {
	}

	@Test
	public void CreateClientUnsuccessfulNoParameters() {
		assertEquals("BE a Hero: (c)reate - Creates a new hero. Syntax: create john.doe password123", client.processReply("create.help"));
	}

	@Test
	public void CreateClientUnsuccessfulLessParameters() {
		// String response = server.process("create hero5");
		String expected = "BE a Hero: (c)reate - Creates a new hero. Syntax: create john.doe password123";
		// assertEquals(expected, client.processReply(response));
	}

	@Test
	public void CreateClientUnsuccessfulMoreParameters() {
		// String response = server.process("create hero5 pass1 pass2");
		String expected = "BE a Hero: (c)reate - Creates a new hero. Syntax: create john.doe password123";
		// assertEquals(expected, client.processReply(response));
	}

	@Test
	public void CreateClientUnsuccessfulUsernameTaken() {
		// String response = server.process("create hero3 hero3");
		String expected = "BE a Hero: Creation unsuccessful! hero3 is already taken! Please choose a different one!";
		// assertEquals(expected, client.processReply(response));
	}

	@Test
	public void CreateClientUnsuccessfulIllegalCharacters() {
		// String response = server.process("create hero@#$%^ heropass");
		String expected = "BE a Hero: Creation unsuccessful! hero@#$%^ contains illegal characters! Allowed hero name characters: az,A-Z,0-9,_,-";
		// assertEquals(expected, client.processReply(response));
	}

	@Test
	public void CreateClientSuccessful() {
		// String response = server.process("create hero_5 hero5");
		String expected = "BE a Hero: Creation Successful! Welcome hero_5! Use (h)elp to display the available commands at any time!\n"
				+ "Entering the Development Mode!\n" + "Login Successful! Welcome hero_5!";
		// assertEquals(expected, client.processReply(response));
	}

	@Test
	public void CreateClientSuccessfulShortcutName() {
		// String response = server.process("c hero_5 hero5");
		String expected = "BE a Hero: Creation Successful! Welcome hero_5! Use (h)elp to display the available commands at any time!\n"
				+ "Entering the Development Mode!\n" + "Login Successful! Welcome hero_5!";
		// assertEquals(expected, client.processReply(response));
	}
}