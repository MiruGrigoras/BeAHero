package com.bearingpoint.bah.commands;

import com.bearingpoint.bah.hero.Hero;
import com.bearingpoint.bah.unit.Unit;

import java.util.List;
import java.util.regex.Pattern;

public class SellCommand implements HeroCommand {
	private List<String> parameters;
	private Hero hero;

	@Override
	public String execute() {
		String result = "sell.success";
		if (parameters == null || parameters.size() != 2 || !Pattern.matches("\\d+", parameters.get(0))
				|| !Pattern.matches("[a-zA-z]+", parameters.get(1))) {
			result = "sell.help";
		} else {
			int numberUnitsToSell = Integer.parseInt(parameters.get(0));
			String unitToSell = parameters.get(1);
			boolean unitFound = false;
			Unit[] heroUnits = hero.getUnits();
			Unit neededUnit = null;
			int noAvailableUnits = 0;
			for (Unit unitItem : heroUnits) {
				if (unitItem != null && unitItem.getName().equals(unitToSell)) {
					unitFound = true;
					neededUnit = unitItem;
					noAvailableUnits += neededUnit.getWarriorsNumber();
				}
			}
			if (!unitFound) {
				result = "sell.fail.unit " + unitToSell;
			} else if (noAvailableUnits < numberUnitsToSell) {
				result = "sell.fail.count " + numberUnitsToSell + " " + unitToSell;
			} else {
				hero.setGold(hero.getGold() + neededUnit.getPrice() * numberUnitsToSell);
				this.extractUnits(numberUnitsToSell, unitToSell);
				result += " " + numberUnitsToSell + " " + unitToSell;
			}
		}
		return result;
	}

	private void extractUnits(int numberUnitsToSell, String unitToSell) {
		int remainedUnits = numberUnitsToSell;
		Unit[] heroUnits = hero.getUnits();
		int indexNeededUnit = 0;
		while (remainedUnits > 0) {
			int maxNoWarrior = 0, i;
			for (i = 0; i < heroUnits.length; i++) {
				if (heroUnits[i] != null && heroUnits[i].getName().equals(unitToSell)) {
					if (heroUnits[i].getWarriorsNumber() > maxNoWarrior) {
						maxNoWarrior = heroUnits[i].getWarriorsNumber();
						indexNeededUnit = i;
					}
				}
			}
			if (maxNoWarrior >= remainedUnits) {
				heroUnits[indexNeededUnit].setWarriorsNumber(maxNoWarrior - remainedUnits);
				remainedUnits = 0;
			} else {
				heroUnits[indexNeededUnit] = null;
				remainedUnits -= maxNoWarrior;
			}
		}
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