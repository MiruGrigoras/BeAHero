package com.bearingpoint.bah.client_server_new.server;

import com.bearingpoint.bah.commands.Command;
import com.bearingpoint.bah.commands.CommandFactory;
import com.bearingpoint.bah.hero.Hero;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class PrinterRunnable implements Runnable{
	
	protected Socket clientSocket;
	private CommandFactory cf = new CommandFactory(null, null, null);

	public PrinterRunnable(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		try {
			Scanner clientInput = new Scanner(clientSocket.getInputStream());
			PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
			while(clientInput.hasNext()) {
				String message = clientInput.nextLine();
				System.out.println("Client " + clientSocket.getPort() + ": " + message);
				String serverResponse = process(message);
				printWriter.println(serverResponse);
				if(message.contains("exit") || message.contains("e ")) {
					clientSocket.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String process(String command) {
		return process(command, null);
	}

	public String process(String command, Hero hero) {
		String result;
		String[] words = command.split(" ");
		ArrayList<String> parameters = new ArrayList<>();
		parameters.addAll(Arrays.asList(words).subList(1, words.length));
		Command com = cf.createCommand(hero, words[0], parameters);
		if (com != null) {
			result = com.execute();
		} else {
			result = "invalid_command";
		}
		return result;
	}


	public CommandFactory getCf() {
		return cf;
	}

	public void setCf(CommandFactory cf) {
		this.cf = cf;
	}

}
