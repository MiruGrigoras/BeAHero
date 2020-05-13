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

public class LoginTest {
	private Server server = new Server();
	private CLIClient client = new CLIClient();

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

	@After
	public void tearDown() {
	}

	@Test
	public void loginSuccesfulTest() {
		String response = "";

		response = client.sendRequest("login john johnpassword");
		assertEquals("The request was submitted.\n", response);
		response = "";

		response = server.process("login john johnpassword");
		assertEquals("Be a Hero: Login Successful! Welcome john.doe!\n" + "Be a Hero: Entering the Development Mode!\n", response);
	}

	@Test
	public void loginUnsuccesfulTest() {
		String response = "";

		response = client.sendRequest("login john johnpass");
		assertEquals("The request was submitted.\n", response);
		response = "";

		response = server.process("login john johnpass");
		assertEquals("Be a Hero: Login failed! Please check your username and/or password!\n", response);

	}
}
