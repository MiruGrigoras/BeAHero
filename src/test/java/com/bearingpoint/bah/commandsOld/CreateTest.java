package com.bearingpoint.bah.commandsOld;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bearingpoint.bah.client.CLIClient;
import com.bearingpoint.bah.server.Server;

public class CreateTest {
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

	@After
	public void tearDown() {
	}

	@Test
	public void creationSuccessfulTest() {
		String response = "";

		response = client.sendRequest("create john johnpassword");
		assertEquals("The request was submitted.\n", response);
		response = "";

		response = server.process("create john johnpassword");
		assertEquals("Be a Hero: Creation Successful! Welcome john! Use (h)elp to display the available commands at any time!\n"
				+ "Be a Hero: You have 5000 gold!\n", response);

	}

	@Test
	public void creationUnsuccessfulTestIfTheUserAlreadyExists() {
		String response = "";

		response = client.sendRequest("create john johnpassword1");
		assertEquals("The request was submitted.\n", response);
		response = "";

		response = server.process("create john johnpassword1");
		assertEquals("Be a Hero: Creation Successful! Welcome john! Use (h)elp to display the available commands at any time!\n"
				+ "Be a Hero: You have 5000 gold!\n", response);
		response = "";

		response = client.sendRequest("create john johnpassword2");
		assertEquals("The request was submitted.\n", response);

		response = server.process("create john johnpassword");
		assertEquals("Creation Unsuccessful! John is already taken Please choose a different one!\n", response);

	}

	@Test
	public void creationUnsuccessfulTestIfTheUserContainsIllegalCharacters() {
		String response = "";

		response = client.sendRequest("create john@%$ johnpassword");
		assertEquals("The request was submitted.\n", response);
		response = "";

		response = server.process("create john johnpassword1");
		assertEquals("Be a Hero:Creation unsuccessful! john contains illegal characters! Allowed hero name characters: a-z,A-Z,0-9,_,-\\n", response);

	}

	@Test
	public void creationUnsuccessfulTestIfThePasswordContainsIllegalCharacters() {

		String response = "";

		response = client.sendRequest("create john johnpassword@#%");
		assertEquals("The request was submitted.\n", response);
		response = "";

		response = server.process("create john johnpassword@#%");
		assertEquals("Be a Hero:Creation unsuccessful! password contains illegal characters! Allowed hero password characters: a-z,A-Z,0-9,_,-\\n",
				response);
	}

}
