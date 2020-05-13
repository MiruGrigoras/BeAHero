package com.bearingpoint.bah.client_server_new.server;

public class ServerMain {

	public static void main(String[] args) {
		System.out.println("Application Started!");
		SocketServer server = new SocketServer();
		server.runServer();
	}
}
