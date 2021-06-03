package org.homi.core.modulelayers;

import java.util.ArrayList;
import java.util.List;

import org.homi.core.ExecutorServiceManager;
import org.homi.core.modulelayers.IModuleLayerParser;
import org.moditect.layrry.platform.PluginDescriptor;
import org.moditect.layrry.platform.PluginLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PluginMonitor implements PluginLifecycleListener {
	private static List<IModuleLayerParser> moduleLayerParsers = new ArrayList<>();
	private static final Logger logger = LoggerFactory.getLogger(PluginMonitor.class);

	public static void addModuleLayerConsumer(IModuleLayerParser c) {
		PluginMonitor.logger.trace("adding ModuleLayerParser: {}", c.getClass().getTypeName());
		moduleLayerParsers.add(c);
	}
	
	public static void removeModuleLayerConsumer(IModuleLayerParser c) {
		PluginMonitor.logger.trace("removing ModuleLayerParser: {}", c.getClass().getTypeName());
		moduleLayerParsers.remove(c);
	}
	
	@Override
	public void pluginAdded(PluginDescriptor plugin) {
		PluginMonitor.logger.trace("Plugin Added: {}", plugin.getName());
		for(IModuleLayerParser parser: moduleLayerParsers) {
			ExecutorServiceManager.getExecutorService().execute(()->{parser.addPlugins(plugin.getName(), plugin.getModuleLayer());});
		}
	}

	@Override
	public void pluginRemoved(PluginDescriptor plugin) {
		PluginMonitor.logger.trace("Plugin Removed: {}", plugin.getName());
		for(IModuleLayerParser parser: moduleLayerParsers) {
			ExecutorServiceManager.getExecutorService().execute(()->{parser.removePlugins(plugin.getName());});
		}
	}

}
