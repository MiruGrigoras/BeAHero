package com.bearingpoint.bah.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;

import com.bearingpoint.bah.commands.Command;
import com.bearingpoint.bah.commands.CommandFactory;
import com.bearingpoint.bah.hero.Hero;

public class Server {
	private Properties prop = new Properties();
	private Socket socket;
	private ServerSocket server;
	private static String serverPort;
	private CommandFactory cf = new CommandFactory(null, null, null);

	public CommandFactory getCf() {
		return cf;
	}

	public void setCf(CommandFactory cf) {
		this.cf = cf;
	}

	public void processClientInput() {

		DataInputStream in;
		try {
			in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			try {
				new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String line = "";
			while (!"exit".equals(line)) {
				String command;
				try {
					command = in.readUTF();
					if (command != null) {
						command = command.toLowerCase();
					}
					process(command);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String process(String command) {
		return process(command, null);
	}

	public String process(String command, Hero hero) {
		String result = "";
		String[] words = command.split(" ");
		ArrayList<String> parameters = new ArrayList<String>();
		for (int i = 1; i < words.length; i++) {
			parameters.add(words[i]);
		}
		Command com;
		com = cf.createCommand(hero, words[0], parameters);
		if (com != null) {
			System.out.println("Command not null");
			System.out.println(result=com.execute());
		} else {
			return "Invalid command! Use (h)elp to display the available commands at any time!";
		}
		return result;
	}
	
	public void acceptClient(int port) {
		try {
			server = new ServerSocket(port);
			System.out.println("Server started");
			System.out.println("Waiting for a client ...");
			socket = server.accept();
			System.out.println("Client accepted");

			this.processClientInput();

			System.out.println("Closing connection");
			socket.close();

		} catch (IOException i) {
			System.out.println(i);
		}
	}

	public void openPropertiesFiles() {
		FileReader i = null;
		try {
			i = new FileReader("src/main/resources/server.properties");
			prop.load(i);
			serverPort = prop.getProperty("serverPort");
		} catch (FileNotFoundException e1) { // for opening the file
			e1.printStackTrace();
		} catch (IOException e) { // for loading the properties
			System.out.println(e);
		} finally {
			try {
				i.close();
			} catch (IOException e) { // for closing the input
				e.printStackTrace();
			}
		}
	}

	public static void main(String args[]) {
		Server server = new Server();
		server.openPropertiesFiles();
		Integer port = Integer.parseInt(serverPort);
		server.acceptClient(port);
	}
}
