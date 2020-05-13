package com.bearingpoint.bah.commands;

import com.bearingpoint.bah.hero.Hero;

import java.util.List;

public class VaultCommand implements HeroCommand {
    private List<String> parameters;
    private Hero hero;

    @Override
    public String execute() {
        String result = "vault.help";
        if (parameters.isEmpty() || parameters == null) {
            result = "vault.success " + hero.getGold();
        }
        return result;
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
