package com.bearingpoint.bah.unit;

public class Archer extends Unit {
	private int rangedDamage;

	public Archer() {
		this.price = 120;
		this.name = "archer";
	}
	
	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

	@Override
	public void attack() {
		// TODO Auto-generated method stub

	}
	
	public int getRangedDamage() {
		return rangedDamage;
	}

	public void setRangedDamage(int rangedDamage) {
		this.rangedDamage = rangedDamage;
	}

}
