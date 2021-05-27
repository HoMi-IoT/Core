package org.homi.core.modulelayers;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import org.homi.core.pluginregistry.PluginRegistry;
import org.homi.core.plugins.proxy.BasicPluginProxy;
import org.homi.plugin.api.IPluginProvider;
import org.homi.plugin.api.PluginID;
import org.homi.plugin.api.basicplugin.AbstractBasicPlugin;
import org.homi.plugin.api.basicplugin.IBasicPlugin;
import org.homi.plugin.api.exceptions.PluginUnavailableException;

public class BasicPluginParser implements IModuleLayerParser {
	
	private PluginRegistry pr;
	private  IPluginProvider pluginProvider;
	
	public BasicPluginParser(PluginRegistry pr, IPluginProvider iPluginProvider) {
		this.pr = pr;
		this.pluginProvider = iPluginProvider;
	}
	
	@Override
	public void addPlugins(String bundle, ModuleLayer layer) {
        getBasicPluginsFromLayer(layer).forEach((plugin)->{
            	if(hasPluginID(plugin))
            		addPlugin(bundle, (AbstractBasicPlugin) plugin);
            });
	}

	@Override
	public void removePlugins(String bundle) {
		this.pr.removePlugins(bundle);
	}

	private List<IBasicPlugin> getBasicPluginsFromLayer(ModuleLayer layer) {
		return ServiceLoader
				.load(layer, IBasicPlugin.class)
	        	.stream()
	            .map(p -> p.get())
	            .collect(Collectors.toList());
	}

	private boolean hasPluginID(IBasicPlugin plugin) {
		return plugin.getClass().isAnnotationPresent(PluginID.class);
	}

	private void addPlugin(String bundle, AbstractBasicPlugin plugin) {
		plugin.setPluginProvider(this.pluginProvider);
		BasicPluginProxy bpp = new BasicPluginProxy(plugin);
		try {
			bpp.setup();
			pr.addPlugin(bundle, bpp);
		} catch (PluginUnavailableException e) {
			e.printStackTrace();
		}
	}
}
