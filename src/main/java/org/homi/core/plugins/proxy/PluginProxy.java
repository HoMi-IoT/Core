package org.homi.core.plugins.proxy;

import org.homi.plugin.api.AbstractPlugin;
import org.homi.plugin.api.Commander;
import org.homi.plugin.specification.ISpecification;
import org.homi.plugin.specification.SpecificationID;

public class PluginProxy extends AbstractPlugin {
	
	private AbstractPlugin plugin;

	public PluginProxy(AbstractPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void setup() {
		if(this.plugin != null) {
			this.plugin.setup();
			this.plugin.getSpecifications().forEach((spec)->{
				addCommanderProxy( spec, plugin.getCommander(spec));
			});
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <T extends Enum<T> & ISpecification> void addCommanderProxy(Class<?> spec, Commander<?> c) {
		addCommander((Class<T>) spec, new CommanderProxy(spec, plugin.getCommander((Class<T>) spec)));
	}

	@Override
	public void teardown() {
		plugin.teardown();
		this.plugin = null;
		this.getSpecifications().forEach((spec)->{
			((CommanderProxy<?>)this.getCommander(spec)).detach();
		});
	}

}
