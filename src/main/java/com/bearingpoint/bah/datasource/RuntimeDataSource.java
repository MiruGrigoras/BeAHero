package com.bearingpoint.bah.datasource;

import com.bearingpoint.bah.hero.Hero;

import java.util.HashMap;
import java.util.Map;

public class RuntimeDataSource extends DataSource {

	private Map<Hero, String> heroes = new HashMap<>();

	public Map<Hero, String> getHeroes() {
		return heroes;
	}

	@Override
	public Hero getHero(String id) {
		Hero result = null;
		for (Hero hero : heroes.keySet()) {
			if (id.equals(hero.getID())) {
				result = hero;
				break;
			}
		}
		return result;
	}

	@Override
	public boolean checkPassword(Hero hero, String password) {
		boolean result = false;
		String passwordToCheck = heroes.get(hero);
		if (passwordToCheck != null && password.equals(passwordToCheck)) {
			result = true;
		}
		return result;
	}

	@Override
	public void addHero(Hero hero, String pass) {
		heroes.put(hero, pass);
	}

	@Override
	public String toString() {
		String result = "";
		for (Map.Entry<Hero, String> entry : heroes.entrySet()) {
			result += entry.getKey().getID() + "/" + entry.getKey().getGold() + "/" + entry.getValue()+'\n';
		}

		return result;
	}
}