package org.homi.core.plugins;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


import org.homi.plugin.api.IPlugin;
import org.homi.plugin.api.IPluginRegistryListener;

public class PluginRegistry implements IPluginRegistrySubject {
	private Map <String, List<IPlugin>> modulePlugins = new ConcurrentHashMap<>();
	private List<IPluginRegistryListener> listeners = new ArrayList<>();
	
	public void addPlugin(String bundle, IPlugin plugin) {
		List<IPlugin> pluginsList = getPluginListForBundle(bundle);
		pluginsList.add(plugin);
		notifyListenersOfPluginAdded(plugin);
	}
	
	private List<IPlugin> getPluginListForBundle(String bundle) {
		List<IPlugin> l = modulePlugins.getOrDefault(bundle, new ArrayList<IPlugin>());
		modulePlugins.putIfAbsent(bundle, l);
		return l;
	}

	public void removePlugin(String bundle, String pluginID) {
		// List<IPlugin> pluginsList = getPluginListForBundle(bundle);
		// TODO remove plugin
		List<IPlugin> pluginsList = getPluginListForBundle(bundle);
		//pluginsList.remove(plugin);
	}

	@Override
	public void attach(IPluginRegistryListener listener) {
		this.listeners.add(listener);
		
	}
	
	@Override
	public void detach(IPluginRegistryListener listener) {
		this.listeners.remove(listener);
	}
	
	@Override
	public void notifyListenersOfPluginAdded(IPlugin plugin) {
		for(IPluginRegistryListener listener: this.listeners) {
			listener.addPlugin(plugin);
		}
	}
	
	@Override
	public void notifyListenersOfPluginRemoved(IPlugin plugin) {
		for(IPluginRegistryListener listener: this.listeners) {
			listener.removePlugin(plugin);
		}
	}
	
}
