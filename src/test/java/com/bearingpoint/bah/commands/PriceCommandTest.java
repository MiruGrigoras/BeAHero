package com.bearingpoint.bah.commands;

import com.bearingpoint.bah.server.Marketplace;
import com.bearingpoint.bah.unit.Archer;
import com.bearingpoint.bah.unit.Cavalry;
import com.bearingpoint.bah.unit.Footman;
import com.bearingpoint.bah.unit.Unit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class PriceCommandTest {
    private PriceCommand priceCommand = new PriceCommand();

    @Before
    public void setUp() {
        Unit footman = new Footman();
        Unit archer = new Archer();
        Unit cavalry = new Cavalry();

        Marketplace marketplace = new Marketplace();
        marketplace.put(footman);
        marketplace.put(archer);
        marketplace.put(cavalry);

        priceCommand.setMarketplace(marketplace);
        priceCommand.setParameters(Collections.emptyList());
    }

    @After
    public void tearDown() {
    }

    @Test
    public void PriceUnsuccessfulParametersNotNull() {
        priceCommand.setParameters(Collections.singletonList("123"));
        String result = priceCommand.execute();
        assertEquals("price.help", result);
    }

    @Test
    public void PriceSuccessful() {
        String result = priceCommand.execute();
        assertEquals("price.success footman 100&price.success archer 120&price.success cavalry 200", result);
    }
}
