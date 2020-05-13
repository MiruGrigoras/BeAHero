package com.bearingpoint.bah.commands;

import com.bearingpoint.bah.server.Lobby;

interface LobbyCommand extends Command {

	void setLobby(Lobby lobby);
}