package com.bearingpoint.bah.client;

import com.bearingpoint.bah.hero.Hero;
import com.bearingpoint.bah.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CLIClientVaultTest {
    private Server server;
    private CLIClient client;
    private Hero hero;

    @Before
    public void setUp(){
        server = new Server();
        client = new CLIClient();
        client.init();
        hero = new Hero("heroID");
    }

    @After
    public void tearDown(){
    }

    @Test
    public void VaultClientSuccessfulTestWithDefaultGold() {
        String response = server.process("vault", hero);
        String expected = "BE a Hero: You have 5000 Gold available!";
        assertEquals(expected, client.processReply(response));
    }
    @Test
    public void VaultClientSuccessfulTestWithDefaultGoldShortcutName() {
        String response = server.process("v", hero);
        String expected = "BE a Hero: You have 5000 Gold available!";
        assertEquals(expected, client.processReply(response));
    }

    @Test
    public void VaultClientSuccessfulTestWithPresetZeroGold() {
        hero.setGold(0);
        String response = server.process("vault", hero);
        client.processReply(response);
        String expected = "BE a Hero: You have 0 Gold available!";
        assertEquals(expected, client.processReply(response));
    }

    @Test
    public void VaultClientUnsuccessfulParametresNotNull() {
        String response = server.process("vault 123", hero);
        client.processReply(response);
        String expected = "BE a Hero: (v)ault - displays the current amount of your hero's Gold. Syntax: vault";
        assertEquals(expected, client.processReply(response));
    }
}