package com.bearingpoint.bah.hero;

import com.bearingpoint.bah.unit.Unit;

public class Hero {
	private String id;
	private int gold;
	private Unit []units;
	private boolean devMode;
	private boolean combMode;

	public Hero(String id) {
		this.id = id;
		this.gold = 5000;
		setUnits(new Unit[6]);
		setDevMode(true);
		setCombMode(false);
	}

	public void update(Hero hero) {
		this.gold = hero.getGold();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hero other = (Hero) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getArmyStatus(){
		String result = "";
		for(Unit unit: units){
			result = result + unit + '\n';
		}
		result = result.substring(0, result.lastIndexOf('\n'));
		return result;
	}

	public String getID() {
		return this.id;
	}

	public int getGold() {
		return this.gold;
	}

	public void setGold(int nr) {
		this.gold = nr;
	}

	public Unit [] getUnits() {
		return units;
	}

	public void setUnits(Unit [] units) {
		this.units = units;
	}

	public boolean isDevMode() {
		return devMode;
	}

	public void setDevMode(boolean devMode) {
		this.devMode = devMode;
	}

	public boolean isCombMode() {
		return combMode;
	}

	public void setCombMode(boolean combMode) {
		this.combMode = combMode;
	}
}
