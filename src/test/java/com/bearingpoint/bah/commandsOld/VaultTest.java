package com.bearingpoint.bah.commandsOld;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import com.bearingpoint.bah.client.CLIClient;
import com.bearingpoint.bah.datasource.RuntimeDataSource;
import com.bearingpoint.bah.hero.Hero;
import com.bearingpoint.bah.server.Server;

public class VaultTest {
	private CLIClient client = new CLIClient();
	private Server server = new Server();

	@Before
	public void setUp() {
		OutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		os = new ByteArrayOutputStream();
		System.setOut(ps);
		client.connect();
		assertEquals("Connected\n", os.toString());

		os = new ByteArrayOutputStream();
		System.setOut(ps);
		server.acceptClient(5328);
		assertEquals("Server started\n" + "Waiting for a client ...\n" + "Client accepted\n", os.toString());

		os = new ByteArrayOutputStream();
		System.setOut(ps);
		client.start();

		assertEquals("Be a Hero: BE a Hero! Today! Available commands:\n" + "(c)reate � Creates a new Hero. Syntax:  create john.doe\n"
				+ "(l)ogin - logins to an already created hero. Syntax: login john.doe\n"
				+ "(h)elp � see the available commands at any point in the execution\n" + "(e)xit � Exit the application at any point in time\n",
				os.toString());

		String response = "";

		response = client.sendRequest("create john johnpassword");
		assertEquals("The request was submitted.\n", response);
		response = "";

		response = server.process("create john johnpassword");
		assertEquals("Be a Hero: Creation Successful! Welcome john! Use (h)elp to display the available commands at any time!\n"
				+ "Be a Hero: You have 5000 gold!\n", response);

	}

	@Test
	public void VaultSuccessfulTest() {
		String response = "";
		RuntimeDataSource rds = new RuntimeDataSource();
		Hero hero;
		int gold;

		hero = rds.getHero("john");
		gold = hero.getGold();

		response = client.sendRequest("vault");
		assertEquals("The request was submitted.\n", response);
		response = "";

		response = server.process("vault");
		assertEquals("You have " + gold + " Gold available.\n", response);
	}

	@Test
	public void VaultUnsuccessfulTestIfTheSyntaxIsIncorrect() {
		String response = "";

		response = client.sendRequest("valuta");
		assertEquals("The request was submitted.\n", response);
		response = "";

		response = server.process("valuta");
		assertEquals("Transactions:\n" + "(v)ault -displays the current amount of your hero�s Gold. Syntax: vault"
				+ "(p)rice -lists the prices of each type of unit that can be bought. Syntax: price"
				+ "(b)uy -buys the specified number of units. Syntax: buy NUMBER UNIT"
				+ "(s)ell -sells the specified number of units. Syntax: sell NUMBERUNIT" + "Tactics:\n"
				+ "(t)actics:-displays the current armies of your hero -total number of units per type and unit division per strategic position."
				+ "Syntax: tactics" + "-divides the existing armies as specified by your hero. Syntax: tactics free/NUMBER UNIT ..free/NUMBERUNIT\n",
				response);

	}
}
