package com.bearingpoint.bah.server;

import com.bearingpoint.bah.unit.Unit;

import java.util.ArrayList;

public class Marketplace {

	private ArrayList<Unit> marketplace = new ArrayList<Unit>();

	public void put(Unit newUnit) {
		marketplace.add(newUnit);
	}

	public ArrayList<Unit> getMarketplace() {
		return this.marketplace;
	}
}
