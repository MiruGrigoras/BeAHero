package com.bearingpoint.bah.commands;

import com.bearingpoint.bah.hero.Hero;

interface HeroCommand extends Command {

	void setHero(Hero hero);
}