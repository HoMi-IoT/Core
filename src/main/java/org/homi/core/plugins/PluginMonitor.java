package org.homi.core.plugins;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import org.homi.core.Core;
import org.homi.plugin.api.IPlugin;
import org.moditect.layrry.platform.PluginDescriptor;
import org.moditect.layrry.platform.PluginLifecycleListener;

/**
 * 
 * @author Nicolas Hanout
 *
 * @since 0.0.1
 */
public class PluginMonitor implements PluginLifecycleListener {
	private static Map<String ,PluginDescriptor> pluginDescriptors = new HashMap<>();

	@Override
	public void pluginAdded(PluginDescriptor plugin) {
		pluginDescriptors.put(plugin.getName(), plugin);
		List<IPlugin> plugins = getPlugins(plugin.getModuleLayer());
		for(IPlugin p: plugins) {
			Core.pluginAdded(p);
		}
		
	}

	@Override
	public void pluginRemoved(PluginDescriptor plugin) {
		PluginMonitor.pluginDescriptors.remove(plugin.getName());
	}
	
	/**
	 * 
	 * @param layer
	 * @return
	 */
    private List<IPlugin> getPlugins(ModuleLayer layer) {
        List<IPlugin> plugins = new ArrayList<>();
        ServiceLoader.load(layer, IPlugin.class)
        	.stream()
            .map(p -> p.get())
            .forEach(plugins::add);
        return plugins;
    }

}
