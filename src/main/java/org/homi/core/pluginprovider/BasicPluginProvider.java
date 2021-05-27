package org.homi.core.pluginprovider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.homi.core.pluginregistry.PluginRegistry;
import org.homi.plugin.api.IPlugin;
import org.homi.plugin.api.IPluginProvider;
import org.homi.plugin.api.IPluginRegistryListener;
import org.homi.plugin.api.PluginID;
import org.homi.plugin.api.basicplugin.IBasicPlugin;
import org.homi.plugin.api.exceptions.PluginException;
import org.homi.plugin.specification.ISpecification;
import org.homi.plugin.specification.SpecificationID;

public class BasicPluginProvider implements IPluginProvider {
	private final PluginRegistry pr;

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
		List<IPlugin> plugins = new ArrayList<>();
		for(String bundle: pr.getBundles()) {
			for(IPlugin plugin: pr.getPlugins(bundle)) {
				if(plugin instanceof IBasicPlugin) {
					IBasicPlugin p = (IBasicPlugin)plugin;
					try {
						for(Class<? extends ISpecification> spec : p.getSpecifications()) {
							if(spec.getClass().getAnnotation(SpecificationID.class).id() == specId) {
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
