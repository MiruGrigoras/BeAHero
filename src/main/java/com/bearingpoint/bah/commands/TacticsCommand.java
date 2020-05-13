package com.bearingpoint.bah.commands;

import com.bearingpoint.bah.hero.Hero;
import com.bearingpoint.bah.unit.Unit;
import com.bearingpoint.bah.unit.UnitBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TacticsCommand implements HeroCommand {
	private List<String> parameters;
	private Hero hero;

	@Override
	public String execute() {
		String result = "";
		if ((parameters != null && !parameters.isEmpty()) && (parameters.size() < 6 || parameters.size() > 12)) {
			result = "tactics.help";
		} else {
			Unit[] units = hero.getUnits();
			if (checkEmpty(units))
				result = "tactics.fail.nounit";
			else {
				HashMap<String, Integer> heroUnits = new HashMap<>();
				extractHeroUnits(units, heroUnits);
				if (parameters.isEmpty()) {
					int unitIndex;
					for (Map.Entry<String, Integer> entry : heroUnits.entrySet()) {
						if (result.equals("")) {
							result += "tactics.unit.display " + entry.getValue() + " " + entry.getKey();
						} else {
							result += "&tactics.unit.display " + entry.getValue() + " " + entry.getKey();
						}
					}
					for (unitIndex = 0; unitIndex < units.length; unitIndex++) {
						if (units[unitIndex] == null)
							result += "&tactics.slot.free " + (unitIndex + 1);
						else
							result += "&tactics.slot " + (unitIndex + 1) + " " + units[unitIndex].getWarriorsNumber() + " "
									+ units[unitIndex].getName();
					}
				} else {
					HashMap<String, Integer> wantedUnits = new HashMap<>();
					result = getWantedHeroUnits(result, heroUnits, wantedUnits);
					if (result.equals("")) {
						for (Map.Entry<String, Integer> entry : wantedUnits.entrySet()) {
							if (!entry.getValue().equals(heroUnits.get(entry.getKey()))) {
								result = "tactics.fail.unit.count " + entry.getValue() + " " + entry.getKey();
								break;
							}
						}
						if (result.equals("")) {
							Unit[] newUnits = setWantedHeroUnits();
							hero.setUnits(newUnits);
							result = "tactics.success";
						}
					}
				}
			}
		}
		return result;
	}

	private boolean checkEmpty(Unit[] units) {
		int i;
		for (i = 0; i < units.length; i++) {
			if (units[i] != null)
				return false;
		}
		return true;
	}

	private Unit[] setWantedHeroUnits() {
		int i;
		int unitIndex = 0;
		Unit[] newUnits = new Unit[6];
		for (i = 0; i < parameters.size(); i++) {
			if (parameters.get(i).equals("free")) {
				newUnits[unitIndex] = null;
				unitIndex++;
			} else {
				String unit = parameters.get(i + 1);
				int unitNo = Integer.parseInt(parameters.get(i));
				UnitBuilder unitBuilder = new UnitBuilder();
				newUnits[unitIndex] = unitBuilder.createInstance(unit);
				newUnits[unitIndex].setWarriorsNumber(unitNo);
				unitIndex++;
				i++;
			}
		}
		return newUnits;
	}

	private String getWantedHeroUnits(String result, HashMap<String, Integer> heroUnits, HashMap<String, Integer> wantedUnits) {
		int i;
		for (i = 0; i < parameters.size(); i++) {
			if (!parameters.get(i).equals("free")) {
				String unit = parameters.get(i + 1);
				int unitNo = Integer.parseInt(parameters.get(i));
				if (heroUnits.containsKey(unit)) {
					if (wantedUnits.containsKey(unit)) {
						wantedUnits.replace(unit, wantedUnits.get(unit) + unitNo);
					} else {
						wantedUnits.put(unit, unitNo);
					}
					i++;
				} else {
					result = "tactics.fail.unit " + unit;
					break;
				}
			}
		}
		return result;
	}

	private void extractHeroUnits(Unit[] units, HashMap<String, Integer> heroUnits) {
		for (Unit unitItem : units) {
			if (unitItem != null) {
				String unitName = unitItem.getName();
				int unitNo = unitItem.getWarriorsNumber();
				if (heroUnits.containsKey(unitName)) {
					heroUnits.replace(unitName, heroUnits.get(unitName) + unitNo);
				} else {
					heroUnits.put(unitName, unitNo);
				}
			}
		}
	}

	@Override
	public void setHero(Hero hero) {
		this.hero = hero;
	}

	@Override
	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}
}
