package com.bearingpoint.bah.commandsOld;

import java.util.ArrayList;

import com.bearingpoint.bah.commands.Command;
import com.bearingpoint.bah.commands.CommandFactory;
import com.bearingpoint.bah.datasource.DataSource;
import com.bearingpoint.bah.datasource.RuntimeDataSource;
import com.bearingpoint.bah.hero.Hero;
import com.bearingpoint.bah.server.Lobby;
import com.bearingpoint.bah.server.Marketplace;
import com.bearingpoint.bah.unit.*;

//import org.junit.*;
public class Main {
	public static void main(String[] args) {
		System.out.println("Hello World!");

		DataSource dataSource = new RuntimeDataSource();

		Hero h1 = new Hero("aa");
		Hero h2 = new Hero("bb");
		Lobby lobby = new Lobby();
		lobby.add(h1);
		lobby.add(h2);
		
		Unit footman = new Footman();
		Unit archer = new Archer();
		Unit cavalry = new Cavalry();
		
		Marketplace marketplace = new Marketplace();
		marketplace.put(footman);
		marketplace.put(archer);
		marketplace.put(cavalry);

		CommandFactory factory = new CommandFactory(dataSource, lobby, marketplace);
		ArrayList<String> params = new ArrayList<String>();
		params.add("john-doe");
		params.add("1234");
		Command c = factory.createCommand("CreateCommand", params);
		System.out.println(c.execute());
		Command c1 = factory.createCommand("LogInCommand", params);
		System.out.println(c1.execute());
		Command c2 = factory.createCommand("MockCommand2", params);
		System.out.println(c2.execute());
		Command c3 = factory.createCommand("COmanda", params);
		System.out.println(c3 == null);

		Hero x1 = new Hero("erou2");
		Hero hero = dataSource.create(x1.getID(), "pass1"); // invalid - contine cifra
		// assertNull(hero);
		if (hero != null) {
			System.out.println("Assert failed - user created but shoulldnt have been");
		}

		Hero x2 = new Hero("eroul");

		dataSource.create(x2.getID(), "pass2"); // valid
		dataSource.create("erou", "pass3"); // valid
		dataSource.create("#erou", "pass4"); // invalid - caracter special

		dataSource.toString();

		x2.setGold(100);
		dataSource.update(x2, "pass2");

		dataSource.toString();
	}
}
