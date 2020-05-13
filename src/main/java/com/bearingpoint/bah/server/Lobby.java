package com.bearingpoint.bah.server;

import com.bearingpoint.bah.hero.Hero;

import java.util.ArrayList;
import java.util.List;

public class Lobby {

	private List<Hero> heroes = new ArrayList<>();

	public void add(Hero hero) {
		heroes.add(hero);
	}

	public List<Hero> getHeroes() {
		return heroes;
	}

	public void setHeroes(List<Hero> heroes) {
		this.heroes = heroes;
	}

	public String getLobby(){
		String result = "";
		for (Hero h: heroes){
			result += h.getID() + '\n';
		}
		return result;
	}
}
