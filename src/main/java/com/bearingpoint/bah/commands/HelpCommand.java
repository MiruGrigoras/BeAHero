package com.bearingpoint.bah.commands;

import com.bearingpoint.bah.hero.Hero;

import java.util.List;

public class HelpCommand implements HeroCommand {
	private Hero hero;
	private List<String> parameters;

	public String execute() {
		String result;
		if (parameters.isEmpty()) {
			if (isOffline()) {
				result = "offline.help";
			} else {
				result = "dev.help";
			}
		} else if (parameters.size() == 1){
			String specificCommand = parameters.get(0);
			if ("create".equals(specificCommand)) {
				result = "create.help";
			} else if ("login".equals(specificCommand)) {
				result = "login.help";
			} else if ("help".equals(specificCommand)) {
				result = "help.help";
			} else if ("exit".equals(specificCommand)) {
				result = "exit.help";
			} else if ("vault".equals(specificCommand)) {
				result = "vault.help";
			} else if ("price".equals(specificCommand)) {
				result = "price.help";
			} else if ("buy".equals(specificCommand)) {
				result = "buy.help";
			} else if ("sell".equals(specificCommand)) {
				result = "sell.help";
			} else if ("tactics".equals(specificCommand)) {
				result = "tactics.help";
			} else {
				result = "incorrect.help";
			}
		} else {
			result = "incorrect.help";
		}
		return result;
	}

	private boolean isOffline() {
		return hero == null;
	}

	@Override
	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}

	@Override
	public void setHero(Hero hero) {
		this.hero = hero;
	}
}

