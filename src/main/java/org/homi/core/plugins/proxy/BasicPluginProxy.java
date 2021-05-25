package org.homi.core.plugins.proxy;

import org.homi.plugin.api.basicplugin.*;
import org.homi.plugin.api.exceptions.PluginUnavailableException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.homi.plugin.api.Commander;
import org.homi.plugin.specification.ISpecification;
import org.homi.plugin.specification.SpecificationID;

public class BasicPluginProxy implements IBasicPlugin {
	
	private Map<String, Commander<? extends ISpecification>> commanders = new HashMap<>();
	private AbstractBasicPlugin plugin;

	public BasicPluginProxy(AbstractBasicPlugin plugin) {
		this.plugin = plugin;
	}

	public void setup() {
		if(this.plugin != null) {
			this.plugin.setup();
			this.plugin.getSpecifications().forEach((spec)->{
				addCommanderProxy( spec, plugin.getCommander(spec));
			});
		}
	}

	
	public void teardown() throws PluginUnavailableException {
		plugin.teardown();
		this.getSpecifications().forEach((spec)->{
			try {
				((CommanderProxy<?>)this.getCommander(spec)).detach();
			} catch (PluginUnavailableException e) {
				throw new RuntimeException(e);
			}
		});
		this.plugin = null;
	}

	private <T extends Enum<T> & ISpecification> void addCommander(Class<T> spec,Commander<T> commander) {
		this.commanders.put(spec.getAnnotation(SpecificationID.class).id(), commander);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <T extends Enum<T> & ISpecification> void addCommanderProxy(Class<?> spec, Commander<?> c) {
		this.addCommander((Class<T>) spec, new CommanderProxy(spec, plugin.getCommander((Class<T>) spec)));
	}
	
	@Override
	public <T extends Enum<T> & ISpecification> Commander<T> getCommander(Class<? extends ISpecification> spec) throws PluginUnavailableException{
		if(plugin != null)
			return (Commander<T>) commanders.get(spec.getAnnotation(SpecificationID.class).id());
		throw new PluginUnavailableException();
	};
	
	@Override
	public List<Class<? extends ISpecification>> getSpecifications() throws PluginUnavailableException{
		if(plugin != null)
			return plugin.getSpecifications();
		throw new PluginUnavailableException();
	}

	@Override
	public List<Runnable> getWorkers() throws PluginUnavailableException {
		if(plugin != null)
			return List.of();
		throw new PluginUnavailableException();
	}

}
