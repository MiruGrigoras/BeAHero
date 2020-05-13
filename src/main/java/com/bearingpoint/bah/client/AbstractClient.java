package com.bearingpoint.bah.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

public abstract class AbstractClient {

	public static final String SERVER_PORT_PROPERTY = "server.port";
	public static final String SERVER_IP_PROPERTY = "server.ip";
	public static final String CLIENT_PROPERTIES_DEFAULT_FILEPATH = "src/main/resources/client.properties";
	public static final String CLIENT_PROPERTIES_FILEPATH = "client.properties";

	protected Properties properties = new Properties();
	protected boolean online = false;
	private String clientPort = null;
	private String serverIp = null;
	private int serverPort;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;

	public void init() {
		String clientPropertiesFilePath = CLIENT_PROPERTIES_FILEPATH;
		File f = new File(clientPropertiesFilePath);
		if (!f.exists()) {
			clientPropertiesFilePath = CLIENT_PROPERTIES_DEFAULT_FILEPATH;
		}
		try (FileReader fileReader = new FileReader(clientPropertiesFilePath)) {
			properties.load(fileReader);
			serverIp = properties.getProperty(SERVER_IP_PROPERTY);
			String serverPortPropertyValue = properties.getProperty(SERVER_PORT_PROPERTY);
			serverPort = Integer.parseInt(serverPortPropertyValue);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public abstract void start();

	public void run() {
		init();
		start();
		connect();
	}

	public abstract String readRequest();

	public String sendRequest(String request) {
		String result = "exit";
		try {
			dataOutputStream.writeUTF(request);
			dataOutputStream.flush();
			result = dataInputStream.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String processReply(String reply) {
		if (reply.startsWith("create.success") || reply.startsWith("login.success")) {
			online = true;
		}
		return null;
	}

	public void connect() {
		Socket socket = null;
		try {
			socket = new Socket(serverIp, serverPort);
			dataInputStream = new DataInputStream(socket.getInputStream());
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
			String reply = null;
			while (!"exit".equals(reply)) {
				String request = readRequest();
				reply = sendRequest(request);
				processReply(reply);
			}
			dataOutputStream.close();
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties prop) {
		this.properties = prop;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public static void main(String[] args) {
		AbstractClient client = new CLIClient();
		client.run();
	}

	public String getClientPort() {
		return clientPort;
	}

	public void setClientPort(String clientPort) {
		this.clientPort = clientPort;
	}
}
