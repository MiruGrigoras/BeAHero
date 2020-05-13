package com.bearingpoint.bah.client_server_new.client;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ClientNew {

	public static final String SERVER_PORT_PROPERTY = "server.port";
	public static final String SERVER_IP_PROPERTY = "server.ip";
	public static final String CLIENT_PROPERTIES_DEFAULT_FILEPATH = "src/main/resources/client.properties";
	public static final String CLICLIENT_PROPERTIES_FILEPATH = "src/main/resources/CLIClient.properties";

	private static Properties properties = new Properties();
	private static String hostName;
	private static int portNumber;

	private static PrintWriter clientRequest;
	private static Scanner consoleInput;

	public static String consoleOutput;
	private static boolean onlineMode = false; //false before a login command

	public static void main(String[] args){
		start();
	}

	public static void start(){
		openPropertiesFiles();
		Socket clientSocket;

		try {
			clientSocket = new Socket(hostName,portNumber);
			clientRequest = new PrintWriter(clientSocket.getOutputStream(), true);
			Scanner serverMessage = new Scanner(clientSocket.getInputStream());
			consoleInput = new Scanner(System.in);
			System.out.println(properties.getProperty("CLIClient.messages.welcome") + "\n" + consoleOutput);
			while(consoleInput.hasNextLine()) {
				String clientStringRequest = processRequest(consoleInput.nextLine());
				String serverStringResponse = processReply(serverMessage.nextLine());
				System.out.println(serverStringResponse);
				if(!clientStringRequest.contains("exit") || !clientStringRequest.contains("e ")){
					/*if(onlineMode == true){
						consoleOutput = Hero.name; //has to be done somehow
					}*/
					System.out.println("\n" + consoleOutput);
				}
				if(clientStringRequest.contains("exit") || clientStringRequest.contains("e ")){
					clientRequest.close();
					serverMessage.close();
					clientSocket.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static String processRequest(String clientRequest) {
		String request = "";
		String[] tokens = clientRequest.split("\\s");
		if(tokens.length >= 1 && !"".equals(tokens[0])) {
			request = Arrays.toString(tokens).replaceAll(",|\\[|]", "");
			if(Pattern.matches("c[^\\s]+", request) || Pattern.matches("create\\s+[^\\s]+", request) || Pattern.matches("l\\s+[^\\s]+", request)
					|| Pattern.matches("login\\s+[^\\s]+", request)){
				String password = readPassword();
				request += " " + password;
			}
			ClientNew.clientRequest.println(request) ;
		}
		return request;
	}

	private static String readPassword() { //nu merge citirea, ia urmatorul rand drept comanda normala pe care vrea sa o prelucreze
		return consoleInput.nextLine();
	}

	public static String processReply(String serverReply){
		String response = "";
		String[] subReplies = serverReply.split("&");
		for (String subReply : subReplies) {
			String[] words = subReply.split("\\s");
			String messageKey = words[0];
			if(messageKey.contains("login")){
				onlineMode = true;
			}
			String[] arguments = Arrays.copyOfRange(words, 1, words.length);
			MessageFormat format = new MessageFormat(properties.getProperty(messageKey));
			response = format.format(arguments);
		}
		return response;
	}

	public static void openPropertiesFiles() {
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(CLIENT_PROPERTIES_DEFAULT_FILEPATH);
			properties.load(fileReader);
			hostName = properties.getProperty(SERVER_IP_PROPERTY);
			portNumber = Integer.parseInt(properties.getProperty(SERVER_PORT_PROPERTY));
			fileReader = new FileReader(CLICLIENT_PROPERTIES_FILEPATH);
			properties.load(fileReader);
			consoleOutput = properties.getProperty("consoleOutput.offline");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) { 
			System.out.println(e);
		} finally {
			try {
				assert fileReader != null;
				fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
