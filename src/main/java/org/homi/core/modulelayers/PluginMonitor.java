package org.homi.core.modulelayers;

import java.util.ArrayList;
import java.util.List;
import org.homi.core.modulelayers.IModuleLayerParser;
import org.moditect.layrry.platform.PluginDescriptor;
import org.moditect.layrry.platform.PluginLifecycleListener;

public class PluginMonitor implements PluginLifecycleListener {
	private static List<IModuleLayerParser> moduleLayerParsers = new ArrayList<>();

	public static void addModuleLayerConsumer(IModuleLayerParser c) {
		moduleLayerParsers.add(c);
	}
	
	public static void removeModuleLayerConsumer(IModuleLayerParser c) {
		moduleLayerParsers.remove(c);
	}
	
	@Override
	public void pluginAdded(PluginDescriptor plugin) {
		for(IModuleLayerParser parser: moduleLayerParsers) {
			parser.addPlugins(plugin.getName(), plugin.getModuleLayer());
		}
	}

	@Override
	public void pluginRemoved(PluginDescriptor plugin) {
		for(IModuleLayerParser parser: moduleLayerParsers) {
			parser.removePlugins(plugin.getName());
		}
	}

}
