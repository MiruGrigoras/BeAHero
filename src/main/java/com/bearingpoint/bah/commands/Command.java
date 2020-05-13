package com.bearingpoint.bah.commands;

import java.util.List;

public interface Command {

	String execute();

	void setParameters(List<String> parameters);
}
