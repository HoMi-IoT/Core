package org.homi.core;

import org.homi.core.modulelayers.BasicPluginParser;
import org.homi.core.modulelayers.IModuleLayerParser;
import org.homi.core.modulelayers.PluginMonitor;
import org.homi.core.pluginprovider.BasicPluginProvider;
import org.homi.core.pluginregistry.PluginRegistry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.Level;

public class Core {


	public static void main(String[] args) {
		((ch.qos.logback.classic.Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)).setLevel(Level.ALL);
		Logger logger = LoggerFactory.getLogger(Core.class);
		logger.trace("Core starting setup");
		
		PluginRegistry pr = new PluginRegistry();
		IModuleLayerParser BasicParser = new BasicPluginParser(pr, new BasicPluginProvider(pr) );
		PluginMonitor.addModuleLayerConsumer(BasicParser);

		logger.trace("Core finished setup");
	}
	
}
