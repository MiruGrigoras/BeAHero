package com.bearingpoint.bah.datasource;

import com.bearingpoint.bah.hero.Hero;

public abstract class DataSource {

	public Hero create(String id, String password) {
		Hero hero = null;
		if (getHero(id) == null && password != null && !password.equals("") && id != null && !id.equals("")) {
			hero = new Hero(id);
			addHero(hero, password);
		}
		return hero;
	}

	public Hero login(String id, String password) {
		Hero hero = getHero(id);
		if (hero != null) {
			if (!checkPassword(hero, password))
				hero = null;
		}
		return hero;
	}

	public boolean update(Hero hero, String password) {
		boolean result = false;
		String id = hero.getID();
		Hero heroToUpdate = getHero(id);
		if (heroToUpdate != null) {
			if (checkPassword(heroToUpdate, password)) {
				heroToUpdate.update(hero);
				result = true;
			}
		}
		return result;
	}

	abstract public Hero getHero(String id);

	abstract public boolean checkPassword(Hero hero, String password);

	abstract public void addHero(Hero hero, String password);
}
