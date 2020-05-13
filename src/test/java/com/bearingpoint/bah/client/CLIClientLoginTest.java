package com.bearingpoint.bah.client;

import com.bearingpoint.bah.commands.CommandFactory;
import com.bearingpoint.bah.datasource.DataSource;
import com.bearingpoint.bah.datasource.RuntimeDataSource;
import com.bearingpoint.bah.hero.Hero;
import com.bearingpoint.bah.server.Lobby;
import com.bearingpoint.bah.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CLIClientLoginTest {
	private Server server;
	private CLIClient client;

	@Before
	public void setUp() {
		server = new Server();
		client = new CLIClient();
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
		server.setCf(cf);
	}

	@After
	public void tearDown() {
	}

	@Test
	public void LoginClientUnsuccessfulNoParameters() {
		String response = server.process("login");
		String expected = "BE a Hero: (l)ogin - Logins to an already created hero. Syntax: login john.doe password123";
		assertEquals(expected, client.processReply(response));
	}

	@Test
	public void LoginClientUnsuccessfulLessParameters() {
		String response = server.process("login hero5");
		String expected = "BE a Hero: (l)ogin - Logins to an already created hero. Syntax: login john.doe password123";
		assertEquals(expected, client.processReply(response));
	}

	@Test
	public void LoginClientUnsuccessfulMoreParameters() {
		String response = server.process("login hero5 pass1 pass2");
		String expected = "BE a Hero: (l)ogin - Logins to an already created hero. Syntax: login john.doe password123";
		assertEquals(expected, client.processReply(response));
	}

	@Test
	public void LoginClientUnsuccessfulInexistentUsername() {
		String response = server.process("login hero5 pass1");
		String expected = "BE a Hero: Login failed! Please check your username and/or password!";
		assertEquals(expected, client.processReply(response));
	}

	@Test
	public void LoginClientUnsuccessfulWrongPassword() {
		String response = server.process("login hero4 pass1");
		String expected = "BE a Hero: Login failed! Please check your username and/or password!";
		assertEquals(expected, client.processReply(response));
	}

	@Test
	public void LoginClientSuccessful() {
		String response = server.process("login hero4 hero4");
		String expected = "BE a Hero: Login Successful! Welcome hero4!";
		assertEquals(expected, client.processReply(response));
	}

	@Test
	public void LoginClientSuccessfulShortcutName() {
		String response = server.process("l hero4 hero4");
		String expected = "BE a Hero: Login Successful! Welcome hero4!";
		assertEquals(expected, client.processReply(response));
	}
}