package org.homi.core.pluginprovider;
import java.util.ArrayList;
import java.util.List;

import org.homi.core.pluginregistry.PluginRegistry;
import org.homi.plugin.api.IPlugin;
import org.homi.plugin.api.IPluginProvider;
import org.homi.plugin.api.IPluginRegistryListener;
import org.homi.plugin.api.basicplugin.IBasicPlugin;
import org.homi.plugin.api.exceptions.PluginException;
import org.homi.plugin.specification.ISpecification;
import org.homi.plugin.specification.SpecificationID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicPluginProvider implements IPluginProvider {
	private final PluginRegistry pr;
	private static final Logger logger = LoggerFactory.getLogger(BasicPluginProvider.class);

	public BasicPluginProvider(PluginRegistry pr) {
		this.pr = pr;
	}

	@Override
	public void addPluginRegistryListener(IPluginRegistryListener prl) {
		pr.attach(prl);
	}

	@Override
	public void removePluginRegistryListener(IPluginRegistryListener prl) {
		pr.detach(prl);		
	}

	@Override
	public IPlugin getPluginByID(String pluginId) {
		BasicPluginProvider.logger.trace("getting plugin with id {}", pluginId);
		for(String bundle: pr.getBundles()) {
			for(IPlugin plugin: pr.getPlugins(bundle)) {
				if(plugin.id() == pluginId) {
					return plugin;
				}
			}
		}
		return null;
	}

	@Override
	public List<IPlugin> getPluginsBySpecID(String specId) {
		BasicPluginProvider.logger.trace("getting plugin that implement the spec {}", specId);
		List<IPlugin> plugins = new ArrayList<>();
		for(String bundle: pr.getBundles()) {
			for(IPlugin plugin: pr.getPlugins(bundle)) {
				if(plugin instanceof IBasicPlugin) {
					IBasicPlugin p = (IBasicPlugin)plugin;
					try {
						for(Class<? extends ISpecification> spec : p.getSpecifications()) {
							if(spec.getClass().isAnnotationPresent(SpecificationID.class) && spec.getClass().getAnnotation(SpecificationID.class).id() == specId) {
								plugins.add(plugin);
							}
						}
					} catch (PluginException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return plugins;
	}
}
