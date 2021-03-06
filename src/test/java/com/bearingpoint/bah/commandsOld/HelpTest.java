package com.bearingpoint.bah.commandsOld;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import com.bearingpoint.bah.client.CLIClient;
import com.bearingpoint.bah.server.Server;

public class HelpTest {
	private CLIClient client = new CLIClient();
	private Server server = new Server();

	@Before
	public void setUp() {
		OutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
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

	}

	@Test
	public void helpTestBeforeCreation() {
		String response = "";

		response = client.sendRequest("help");
		assertEquals("The request was submitted.\n", response);
		response = "";

		response = server.process("help");
		assertEquals(
				"Be a Hero: BE a Hero! Today! Available commands: \n" + "(c)reate - Creates a new hero. Syntax: create john.doe\n"
						+ "(l)ogin - logins to an already created hero. Syntax: login john.doe\n"
						+ "(h)elp - See the available commands at any point in time\n" + "(e)xit - Exit the application at any point in time\n",
				response);

	}

	@Test
	public void helpTestAfterCreation() {
		String response = "";

		response = client.sendRequest("create john johnpassword");
		assertEquals("The request was submitted.\n", response);
		response = "";

		response = server.process("create john johnpassword");
		assertEquals("Be a Hero: Creation Successful! Welcome john! Use (h)elp to display the available commands at any time!\n"
				+ "Be a Hero: You have 5000 gold!\n", response);

		client.sendRequest("help");
		assertEquals("The request was submitted.\n", response);
		response = "";

		server.process("help");
		assertEquals("Transactions:\r\n" + "(v)ault - displays the current amount of your hero's Gold. Syntax: vault\n"
				+ "(p)rice - lists the price of each type of unit that can be bought. Syntax: price\n"
				+ "(b)uy - buys the specified number of units. Syntax: buy NUMBER UNIT\n"
				+ "(s)ell - sells the specified number of units. Syntax: sell NUMBER UNIT\n", response);

	}

}
