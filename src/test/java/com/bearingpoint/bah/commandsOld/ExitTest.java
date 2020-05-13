package com.bearingpoint.bah.commandsOld;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.Test;

import com.bearingpoint.bah.client.CLIClient;
import com.bearingpoint.bah.server.Server;

public class ExitTest {
	private CLIClient client = new CLIClient();
	private Server server = new Server();

	@Test
	public void exitSuccessfulTestIfAUserISLoggedIn() {
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
		response = "";

		response = client.sendRequest("exit");
		assertEquals("The request was submitted.\n", response);
		response = "";

		response = server.process("exit");
		assertEquals("Goodbye! See you soon, john!", response);

	}

	@Test
	public void exitSuccessfulTestIfNoOneIsLogs() {
		String response = "";

		response = client.sendRequest("exit");
		assertEquals("The request was submitted.\n", response);
		response = "";

		response = server.process("exit");
		assertEquals("Goodbye! See you soon, HERO!", response);

	}

	@Test
	public void exitUnsuccessfulTestIfTheSyntaxIsIncorrect() {
		String response = "";

		response = client.sendRequest("ext");
		assertEquals("The request was submitted.\n", response);
		response = "";

		response = server.process("ext");
		assertEquals("The command doesn't exist", response);
	}

}
