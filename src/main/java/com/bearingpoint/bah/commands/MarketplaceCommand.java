package com.bearingpoint.bah.commands;

import com.bearingpoint.bah.server.Marketplace;

interface MarketplaceCommand extends Command {

	void setMarketplace(Marketplace marketplace);
}