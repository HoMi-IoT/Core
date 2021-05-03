module org.homi.core {
	requires org.moditect.layrry.platform;
	requires org.homi.plugin.api;
	requires org.homi.plugin.specification;
	
	provides org.moditect.layrry.platform.PluginLifecycleListener 
		with org.homi.core.plugins.PluginMonitor;
}