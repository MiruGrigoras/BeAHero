package com.bearingpoint.bah.commands;

import com.bearingpoint.bah.datasource.DataSource;

interface DataSourceCommand extends Command {

	public void setDataSource(DataSource dataSource);
}