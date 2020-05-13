package com.bearingpoint.bah.commands;

import com.bearingpoint.bah.hero.Hero;
import com.bearingpoint.bah.server.Marketplace;
import com.bearingpoint.bah.unit.Unit;
import com.bearingpoint.bah.unit.UnitBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BuyCommand implements HeroCommand, MarketplaceCommand {
    private List<String> parameters;
    private Marketplace marketplace;
    private Hero hero;

    @Override
    public String execute() {
        String result = "buy.success";
        if (parameters == null || parameters.size() != 2 || !Pattern.matches("\\d+", parameters.get(0)) || !Pattern.matches("[a-zA-z]+", parameters.get(1))) {
            result = "buy.help";
        } else {
            int numberUnitsToBuy = Integer.parseInt(parameters.get(0));
            String unitToBuy = parameters.get(1);
            boolean unitFound = false;
            ArrayList<Unit> marketplaceItems = marketplace.getMarketplace();
            Unit neededUnit = null;
            for (Unit unitItem : marketplaceItems) {
                if (unitItem.getName().equals(unitToBuy)) {
                    unitFound = true;
                    neededUnit = unitItem;
                }
            }
            if (!unitFound) {
                result = "buy.fail.unit " + unitToBuy;
            } else if (neededUnit.getPrice() * numberUnitsToBuy > hero.getGold()) {
                result = "buy.fail.gold " + numberUnitsToBuy + " " + unitToBuy;
            } else {
                hero.setGold(hero.getGold() - neededUnit.getPrice() * numberUnitsToBuy);
                result += " " + numberUnitsToBuy + " " + unitToBuy;
                Unit[] heroUnits = hero.getUnits();
                int i, maxNoWarriors = 0, unitToCompleteIndex = 0, lastNullUnitIndex = -1;
                for (i = 0; i < heroUnits.length; i++) {
                    if (heroUnits[i] != null) {
                        if (heroUnits[i].getName().equals(unitToBuy) && heroUnits[i].getWarriorsNumber() > maxNoWarriors) {
                            maxNoWarriors = heroUnits[i].getWarriorsNumber();
                            unitToCompleteIndex = i;
                        }
                    } else {
                        lastNullUnitIndex = i;
                    }
                }
                if (maxNoWarriors != 0) {
                    heroUnits[unitToCompleteIndex].setWarriorsNumber(maxNoWarriors + numberUnitsToBuy);
                } else if (lastNullUnitIndex != -1) {
                    UnitBuilder unitBuilder = new UnitBuilder();
                    heroUnits[lastNullUnitIndex] = unitBuilder.createInstance(unitToBuy);
                    heroUnits[lastNullUnitIndex].setWarriorsNumber(numberUnitsToBuy);
                } else {
                    result = "buy.fail.space " + unitToBuy;
                }
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

    @Override
    public void setHero(Hero hero) {
        this.hero = hero;
    }

}
