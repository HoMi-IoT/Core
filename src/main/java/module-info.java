module org.homi.core {
	requires org.moditect.layrry.platform;
	requires org.homi.plugin.api;
	requires org.homi.plugin.specification;
	requires org.slf4j;
	//requires layrry.core;

	exports org.homi.core;
	exports org.homi.core.modulelayers;
	
	provides org.moditect.layrry.platform.PluginLifecycleListener 
		with org.homi.core.modulelayers.PluginMonitor;

	uses org.homi.plugin.api.IPlugin;
	uses org.homi.plugin.api.basicplugin.IBasicPlugin;
	
}