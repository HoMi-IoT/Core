package org.homi.core;

import org.homi.core.modulelayers.BasicPluginParser;
import org.homi.core.modulelayers.IModuleLayerParser;
import org.homi.core.modulelayers.PluginMonitor;
import org.homi.core.pluginprovider.BasicPluginProvider;
import org.homi.core.pluginregistry.PluginRegistry;

public class Core {


	public static void main(String[] args) {
		PluginRegistry pr = new PluginRegistry();
		IModuleLayerParser BasicParser = new BasicPluginParser(pr, new BasicPluginProvider(pr) );
		PluginMonitor.addModuleLayerConsumer(BasicParser);
	}
	
}
