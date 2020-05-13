package com.bearingpoint.bah.commands;

import com.bearingpoint.bah.server.Marketplace;
import com.bearingpoint.bah.unit.Unit;

import java.util.ArrayList;
import java.util.List;

public class PriceCommand implements MarketplaceCommand {
	private List<String> parameters;
	private Marketplace marketplace;

	@Override
	public String execute() {
		String result = "price.help";
		if (parameters.isEmpty() || parameters == null) {
			result = "";
			ArrayList<Unit> marketplaceItems = marketplace.getMarketplace();
			String separator = "";
			for (Unit unit : marketplaceItems) {
				result += separator + "price.success " + unit.getName() + " " + unit.getPrice();
				separator = "&";
			}
		}
		return result;
	}

	@Override
	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}

	@Override
	public void setMarketplace(Marketplace marketplace) {
		this.marketplace = marketplace;
	}

}
