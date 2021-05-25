package org.homi.core;

import java.util.ArrayList;
import java.util.List;

import org.homi.core.modulelayers.BasicPluginParser;
import org.homi.core.modulelayers.IModuleLayerParser;
import org.homi.core.modulelayers.PluginMonitor;
import org.homi.core.pluginregistry.PluginRegistry;
import org.homi.plugin.api.IPlugin;
import org.homi.plugin.api.IPluginProvider;
import org.homi.plugin.api.IPluginRegistryListener;
import org.homi.plugin.api.PluginID;
import org.homi.plugin.api.basicplugin.IBasicPlugin;
import org.homi.plugin.api.exceptions.PluginException;
//import org.moditect.layrry.Layers;
import org.homi.plugin.specification.ISpecification;
import org.homi.plugin.specification.SpecificationID;

public class Core {
	private static PluginRegistry pr = new PluginRegistry();
	private static IPlugin actionReg;

	public static void main(String[] args) {
		PluginRegistry pr = new PluginRegistry();
		IModuleLayerParser BasicParser = new BasicPluginParser(pr, new IPluginProvider() {

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
						if(plugin.getClass().getAnnotation(PluginID.class).id() == pluginId) {
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
			}} );
		
		PluginMonitor.addModuleLayerConsumer(BasicParser);
		
//		Layers layers = Layers.builder()
//				.pluginsDirectory("plugins", null, List.of("api"))
//				.layer("layrry-platform")
//					.withModule("org.moditect.layrry:layrry-platform:1.0.0Alpha1")
//				.layer("api")
//					.withModule("org.homi.plugin:specification:0.0.1-SNAPSHOT")
//					.withModule("org.homi.plugin:api:0.0.1-SNAPSHOT")
//				.layer("core")
//					.withModule("org.homi:core:0.0.1-SNAPSHOT")
//					.withParent("api")
//					.withParent("layrry-platform")
//				.build();
		
	}

//	public static void pluginAdded(IPlugin p) {	
//		
//		AbstractBasicPlugin  ap = (AbstractBasicPlugin) p;
//		if(Core.actionReg == null) {
//			Core.actionReg = ap;
//		}
////		ap.setPluginProvider();
//		ap.setup();
//		
//		List<Runnable> rl =  ap.getWorkers();
//		System.out.println(rl.size());
//		for(Runnable r: rl) {
//			ExecutorServiceManager.getExecutorService().execute(r);
//		}
//		pr.addPlugin("null", p);
//	}
	
}
