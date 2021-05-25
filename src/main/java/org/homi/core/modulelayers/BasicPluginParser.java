package org.homi.core.modulelayers;

import java.util.ServiceLoader;

import org.homi.core.pluginregistry.PluginRegistry;
import org.homi.core.plugins.proxy.BasicPluginProxy;
import org.homi.plugin.api.IPluginProvider;
import org.homi.plugin.api.PluginID;
import org.homi.plugin.api.basicplugin.AbstractBasicPlugin;
import org.homi.plugin.api.basicplugin.IBasicPlugin;

public class BasicPluginParser implements IModuleLayerParser {
	
	private PluginRegistry pr;
	private  IPluginProvider pluginProvider;
	
	public BasicPluginParser(PluginRegistry pr, IPluginProvider iPluginProvider) {
		this.pr = pr;
		this.pluginProvider = iPluginProvider;
	}
	
	@Override
	public void addPlugins(String bundle, ModuleLayer layer) {
        ServiceLoader.load(layer, IBasicPlugin.class)
        	.stream()
            .map(p -> p.get())
            .forEach((plugin)->{
            	if(plugin.getClass().isAnnotationPresent(PluginID.class)) {
	            	AbstractBasicPlugin p = (AbstractBasicPlugin) plugin;
	            	p.setPluginProvider(this.pluginProvider);
	            	BasicPluginProxy bpp = new BasicPluginProxy(p);
	            	bpp.setup();
	            	pr.addPlugin(bundle, plugin);
            	}
            });
	}

	@Override
	public void removePlugins(String bundle) {
		this.pr.removePlugins(bundle);
	}
}
