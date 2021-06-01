package org.homi.core.modulelayers;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

import org.homi.core.ExecutorServiceManager;
import org.homi.core.pluginregistry.PluginRegistry;
import org.homi.core.plugins.proxy.BasicPluginProxy;
import org.homi.plugin.api.IPluginProvider;
import org.homi.plugin.api.PluginID;
import org.homi.plugin.api.basicplugin.AbstractBasicPlugin;
import org.homi.plugin.api.basicplugin.IBasicPlugin;
import org.homi.plugin.api.exceptions.PluginException;
import org.homi.plugin.api.exceptions.PluginUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicPluginParser implements IModuleLayerParser {

	private static final Logger logger = LoggerFactory.getLogger(BasicPluginParser.class);
	
	private PluginRegistry pr;
	private IPluginProvider pluginProvider;
	
	public BasicPluginParser(PluginRegistry pr, IPluginProvider iPluginProvider) {
		this.pr = pr;
		this.pluginProvider = iPluginProvider;
	}
	
	@Override
	public void addPlugins(String bundle, ModuleLayer layer) {
		BasicPluginParser.logger.trace("addingPlugins");
        getBasicPluginsFromLayer(layer).forEach((plugin)->{
    		BasicPluginParser.logger.trace("Processing Plugin: {}", plugin);
            if(hasPluginID(plugin)) {
        		BasicPluginParser.logger.trace("Plugin {} has id {}", plugin, plugin.id());
            	addPlugin(bundle, (AbstractBasicPlugin) plugin);
            	try {
					plugin.getWorkers().forEach((worker)->{
						ExecutorServiceManager.getExecutorService().execute(worker);
					});
				} catch (PluginException e) {
					e.printStackTrace();
				}
            }
            });
	}

	@Override
	public void removePlugins(String bundle) {
		BasicPluginParser.logger.trace("removing bundle {}", bundle);
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
