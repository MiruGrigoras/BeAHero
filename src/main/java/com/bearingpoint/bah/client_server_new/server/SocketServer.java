package com.bearingpoint.bah.client_server_new.server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class SocketServer {
	private int portNumber;
	private ServerSocket serverSocket;
	private Properties prop = new Properties();
	
	public void runServer() {
		try {
			openPropertiesFiles();
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(true) {
			try {
				Socket clientSocket = serverSocket.accept();
				PrinterRunnable runnable = new PrinterRunnable(clientSocket);
				System.out.println("Creating thread for client: " + clientSocket.getPort());
				new Thread(runnable).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void openPropertiesFiles() {
		FileReader i = null;
		try {
			i = new FileReader("src/main/resources/server.properties");
			prop.load(i);
			portNumber = Integer.parseInt(prop.getProperty("server.port"));
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
}
