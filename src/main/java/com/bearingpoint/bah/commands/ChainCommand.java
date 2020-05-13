package com.bearingpoint.bah.commands;

interface ChainCommand extends Command {

	void setCommandFactory(CommandFactory commandFactory);
}