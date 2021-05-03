package org.homi.core.plugins;

import org.homi.plugin.api.IPlugin;
import org.homi.plugin.api.IPluginRegistryListener;

/**
 * 
 * @author Nicolas Hanout
 *
 */
public interface IPluginRegistrySubject {
	void attach(IPluginRegistryListener listener);
	void detach(IPluginRegistryListener listener);
	void notifyListenersOfPluginAdded(IPlugin plugin);
	void notifyListenersOfPluginRemoved(IPlugin plugin);
}
