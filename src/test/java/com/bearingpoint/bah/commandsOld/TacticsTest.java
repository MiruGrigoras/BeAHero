package com.bearingpoint.bah.commandsOld;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import com.bearingpoint.bah.client.CLIClient;
import com.bearingpoint.bah.server.Server;

public class TacticsTest {

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
	public void tacticsSuccessfulTestToDisplayYourArmysCurrentStatus() {
		String response = "";

		response = client.sendRequest("buy 5 Archer");
		assertEquals("The request was submitted.\n", response);
		response = "";
		response = server.process("buy 5 Archer");
		assertEquals("5 of Archer units join your army.\n", response);

		response = client.sendRequest("buy 5 Footman");
		assertEquals("The request was submitted.\n", response);
		response = "";
		response = server.process("buy 5 Footman");
		assertEquals("5 of Archer units join your army.\n", response);
		response = "";

		response = client.sendRequest("tactics");
		assertEquals("The request was submitted.\n", response);
		response = "";
		response = server.process("tactics");
		assertEquals("Archer: 5 units\n" + "Footman: 5 units\n", response);

		// cum le aseaza initial cand face doar buy ?
		// buy trebuie urmat de tactics mereu?
	}

	@Test
	public void tacticsSuccessfulTestToDivideTheExistingArmiesOfYourHero() {
		String response = "";

		response = client.sendRequest("buy 5 Archer");
		assertEquals("The request was submitted.\n", response);
		response = server.process("buy 5 Archer");
		assertEquals("5 of Archer units join your army.\n", response);

		response = client.sendRequest("buy 5 Footman");
		assertEquals("The request was submitted.\n", response);
		response = "";
		response = server.process("buy 5 Footman");
		assertEquals("5 of Archer units join your army.\n", response);
		response = "";

		response = client.sendRequest("tactics 3 Archer 2 Archer 2 Footman free 2 Footman 1 Footman");
		assertEquals("The request was submitted.\n", response);
		response = "";

		response = server.process("tactics 3 Archer 2 Archer 2 Footman free 2 Footman 1 Footman");
		assertEquals("De completat cu mesajul respectiv", response);
		response = "";

		response = client.sendRequest("tactics");
		assertEquals("The request was submitted.\n", response);
		response = "";

		response = server.process("tactics");
		assertEquals("Archer: 5 units\n" + "Footman: 5 units\n" + "1: 3 Archer units\n" + "2: 2 Archer units\n" + "3: 2 Footman\n" + "4: free\n"
				+ "5: 2 Footman\n" + "6: 1 Footman\n", response);

	}

	@Test
	public void tacticsUnsuccessfulTestToDivideTheExistingArmiesOfYourHeroIfTheUserDoesntHaveTheSpecifiedUnit() {
		String response = "";

		response = client.sendRequest("buy 5 Archer");
		assertEquals("The request was submitted.\n", response);
		response = "";
		response = server.process("buy 5 Archer");
		assertEquals("5 of Archer units join your army.\n", response);

		response = client.sendRequest("buy 5 Footman");
		assertEquals("The request was submitted.\n", response);
		response = "";
		response = server.process("buy 5 Footman");
		assertEquals("5 of Archer units join your army.\n", response);
		response = "";

		response = client.sendRequest("tactics 3 Archer 2 Archer 2 Footman 5 Cavalry 2 Footman 1 Footman");
		assertEquals("The request was submitted.\n", response);
		response = "";
		// daca sunt mai multe unituri, le voi afisa numele tuturor?
		response = server.process("tactics 3 Archer 2 Archer 2 Footman 5 Cavalry 2 Footman 1 Footman");
		assertEquals("Cavalry is not available", response);

	}

	@Test
	public void tacticsUnsuccessfulTestIfTheSumOfASpecifiedUnitIsNotEqualToTheTotalNumberUnits() {
		String response = "";

		response = client.sendRequest("buy 5 Archer");
		assertEquals("The request was submitted.\n", response);
		response = "";
		response = server.process("buy 5 Archer");
		assertEquals("5 of Archer units join your army.\n", response);

		response = client.sendRequest("buy 5 Footman");
		assertEquals("The request was submitted.\n", response);
		response = "";
		response = server.process("buy 5 Footman");
		assertEquals("5 of Archer units join your army.\n", response);
		response = "";

		response = client.sendRequest("tactics 5 Archer 2 Archer 2 Footman 5 free 2 Footman 1 Footman");
		assertEquals("The request was submitted.\n", response);
		response = "";

		response = server.process("tactics 5 Archer 2 Archer 2 Footman 5 Cavalry 2 Footman 1 Footman");
		assertEquals("7 of Archer units not available.", response);

	}

	@Test
	public void tacticsUnsuccessfulIfTheSyntaxIsIncorrect() {
		String response = "";

		response = client.sendRequest("tacsfsf");
		assertEquals("The request was submitted.\n", response);
		response = "";

		response = server.process("pret");
		assertEquals("Transactions:\n" + "(v)ault -displays the current amount of your hero�s Gold. Syntax: vault"
				+ "(p)rice -lists the prices of each type of unit that can be bought. Syntax: price"
				+ "(b)uy -buys the specified number of units. Syntax: buy NUMBER UNIT"
				+ "(s)ell -sells the specified number of units. Syntax: sell NUMBERUNIT" + "Tactics:\n"
				+ "(t)actics:-displays the current armies of your hero -total number of units per type and unit division per strategic position."
				+ "Syntax: tactics" + "-divides the existing armies as specified by your hero. Syntax: tactics free/NUMBER UNIT ..free/NUMBERUNIT\n",
				response);

	}

}
