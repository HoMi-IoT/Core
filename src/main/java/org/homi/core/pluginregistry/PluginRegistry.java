package org.homi.core.pluginregistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.homi.core.plugins.proxy.BasicPluginProxy;
import org.homi.plugin.api.IPlugin;
import org.homi.plugin.api.IPluginRegistryListener;
import org.homi.plugin.api.basicplugin.IBasicPlugin;
import org.homi.plugin.api.exceptions.PluginUnavailableException;

public class PluginRegistry implements IPluginRegistrySubject {
	private Map <String, List<IPlugin>> modulePlugins = new ConcurrentHashMap<>();
	
	private List<IPluginRegistryListener> listeners = new ArrayList<>();
	
	
	public Set<String> getBundles(){
		return this.modulePlugins.keySet();
	}
	
	public List<IPlugin> getPlugins(String bundle){
		return this.modulePlugins.getOrDefault(bundle, List.of());
	}
	
	public void addPlugin(String bundle, IPlugin plugin) {
		modulePlugins.putIfAbsent(bundle, new ArrayList<>());
		modulePlugins.get(bundle).add(plugin);
		notifyListenersOfPluginAdded(plugin);
	}
	
	public void removePlugins(String bundle) {
		modulePlugins.getOrDefault(bundle, List.of())
			.forEach((plugin)->{
				if(plugin instanceof IBasicPlugin) {
					BasicPluginProxy p = (BasicPluginProxy) plugin;
					try {
						notifyListenersOfPluginRemoved(p);
						p.teardown();
					} catch (PluginUnavailableException e) {
						e.printStackTrace();
					}
				}
			});
		modulePlugins.remove(bundle);
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
