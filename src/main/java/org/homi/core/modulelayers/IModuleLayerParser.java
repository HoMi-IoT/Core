package org.homi.core.modulelayers;

public interface IModuleLayerParser {
	void addPlugins(String bundle, ModuleLayer layer);
	void removePlugins(String bundle);
}
