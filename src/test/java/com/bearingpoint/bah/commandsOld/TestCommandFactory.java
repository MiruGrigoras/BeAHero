package com.bearingpoint.bah.commandsOld;

import java.io.IOException;
import java.util.ArrayList;

import com.bearingpoint.bah.commands.Command;
import com.bearingpoint.bah.commands.CommandFactory;
import com.bearingpoint.bah.datasource.DataSource;
import com.bearingpoint.bah.datasource.RuntimeDataSource;
import com.bearingpoint.bah.hero.Hero;
import com.bearingpoint.bah.server.Lobby;
import com.bearingpoint.bah.server.Marketplace;
import com.bearingpoint.bah.unit.Archer;
import com.bearingpoint.bah.unit.Cavalry;
import com.bearingpoint.bah.unit.Footman;
import com.bearingpoint.bah.unit.Unit;

public class TestCommandFactory {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {

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

		Command command = factory.createCommand("mocke", params);
		if (command == null)
			System.out.println("Command name doesn't exist!\n");
		else
			command.execute();

		Command comm = factory.createCommand("one", params);
		if (comm == null)
			System.out.println("Command name doesn't exist!\n");
		else
			comm.execute();

		Command comma = factory.createCommand("two", params);
		if (comma == null)
			System.out.println("Command name doesn't exist!\n");
		else
			comma.execute();

	}
}
