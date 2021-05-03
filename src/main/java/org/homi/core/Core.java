package org.homi.core;

import java.util.List;

import org.homi.core.plugins.PluginRegistry;
import org.homi.plugin.api.AbstractPlugin;
import org.homi.plugin.api.IPlugin;
import org.homi.plugin.api.IPluginProvider;
import org.homi.plugin.api.IPluginRegistryListener;

/**
 * 
 * @author Nicolas Hanout
 *
 */
public class Core {
	private static PluginRegistry pr;

	public static void main(String[] args) {
		pr = new PluginRegistry();
		
	}

	public static void pluginAdded(IPlugin p) {
		AbstractPlugin  ap = (AbstractPlugin) p;
		ap.setPluginProvider(new IPluginProvider() {

			@Override
			public void addPluginRegistryListener(IPluginRegistryListener arg0) {
				// TODO Auto-generated method stub
				pr.attach(arg0);
				
			}

			@Override
			public IPlugin getPluginByID(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<IPlugin> getPluginsBySpecID(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void removePluginRegistryListener(IPluginRegistryListener arg0) {
				// TODO Auto-generated method stub
				pr.detach(arg0);
				
			}} );
		ap.setup();
		List<Runnable> rl =  ap.getWorkers(null);
		for(Runnable r: rl) {
			ExecutorServiceManager.getExecutorService().execute(r);
		}
		pr.addPlugin("null", p);
	}
}
