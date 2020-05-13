package com.bearingpoint.bah.client;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bearingpoint.bah.commands.CommandFactory;
import com.bearingpoint.bah.server.Marketplace;
import com.bearingpoint.bah.server.Server;
import com.bearingpoint.bah.unit.Archer;
import com.bearingpoint.bah.unit.Cavalry;
import com.bearingpoint.bah.unit.Footman;
import com.bearingpoint.bah.unit.Unit;

public class CLIClientPriceTest {
	private Server server;
	private CLIClient client;

	@Before
	public void setUp() {
		server = new Server();
		client = new CLIClient();
		client.init();
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
	public void tearDown() {
	}

	@Test
	public void PriceClientUnsuccessfulParametersNotNull() {
		String response = server.process("price 123");
		String expected = "BE a Hero: (p)rice - lists the prices of each type of unit that can be bought. Syntax: price";
		assertEquals(expected, client.processReply(response));
	}

	@Test
	public void PriceClientSuccessful() {
		String response = server.process("price");
		String expected = "BE a Hero: footman: 100 Gold per Unit\n" + "archer: 120 Gold per Unit\n" + "cavalry: 200 Gold per Unit";
		assertEquals(expected, client.processReply(response));
	}

	@Test
	public void PriceClientSuccessfulShortcutName() {
		String response = server.process("p");
		String expected = "BE a Hero: footman: 100 Gold per Unit\n" + "archer: 120 Gold per Unit\n" + "cavalry: 200 Gold per Unit";
		assertEquals(expected, client.processReply(response));
	}
}