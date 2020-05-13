package com.bearingpoint.bah.client;
import java.io.BufferedReader;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.regex.Pattern;

public class CLIClient extends AbstractClient {

	public static final String CLICLIENT_PROPERTIES_FILEPATH = "src/main/resources/CLIClient.properties";
	private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

	@Override
	public void init() {
		super.init();
		try (FileReader fileReader = new FileReader(CLICLIENT_PROPERTIES_FILEPATH)) {
			properties.load(fileReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start() {
		String welcomeMessage = properties.getProperty("CLIClient.messages.welcome");
		System.out.println(welcomeMessage);
	}

	public String readPassword() {
		String result = null;
		Console console = System.console();
		if (console != null) {
			result = console.readPassword().toString();
		} else {
			try {
				result = bufferedReader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private String getConsole() {
		String result = "Be a Hero>";
		if (online) {
			result = sendRequest("console");
		}
		return result;
	}

	@Override
	public String readRequest() {
		System.out.print(getConsole());
		String result = "exit";
		try {
			result = bufferedReader.readLine();
			if (Pattern.matches("c\\s+[^\\s]+", result) || Pattern.matches("create\\s+[^\\s]+", result) || Pattern.matches("l\\s+[^\\s]+", result)
					|| Pattern.matches("login\\s+[^\\s]+", result)) {
				String password = readPassword();
				result = result + " " + password;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String processReply(String reply) {
		String finalReply = "";
		String[] subReplies = reply.split("&");
		for (String subReply : subReplies) {
			String[] words = subReply.split("\\s");
			String messageKey = words[0];
			String[] arguments = Arrays.copyOfRange(words, 1, words.length);
			MessageFormat format = new MessageFormat(properties.getProperty(messageKey));
			String formattedReply = format.format(arguments);
			if (finalReply == "")
				finalReply += "BE a Hero: " + formattedReply;
			else finalReply += "\n" + formattedReply;
		}
		return finalReply;
	}
}