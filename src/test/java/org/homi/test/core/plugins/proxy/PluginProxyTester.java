package org.homi.test.core.plugins.proxy;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.homi.core.plugins.proxy.PluginProxy;
import org.homi.plugin.api.AbstractPlugin;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PluginProxyTester {
	static AbstractPlugin ap;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		ap = new DummyPlugin();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void pluginProxyConstructorTakesAnAbstractPluginArgument() {
		PluginProxy pp = new PluginProxy(ap);
	}
	
	@Test
	void pluginProxyReturnsCommanderOfGivenSpec() {
		PluginProxy pp = new PluginProxy(ap);
		pp.setup();
		Assertions.assertNotNull(pp.getCommander(TestSpec.class));
		Assertions.assertNull(pp.getCommander(TestSpec.class).execute(TestSpec.RETURN_NULL));
	}

	@Test
	void getCommanderReturnsNullWhenPassingUnimplementedSpec() {
		PluginProxy pp = new PluginProxy(ap);
		pp.setup();
		Assertions.assertNull(pp.getCommander(TestSpec2.class));
	}
	
	@Test
	void pluginProxyTeardownMakesAllCommandsReturnNull() {
		PluginProxy pp = new PluginProxy(ap);
		pp.setup();
		Assertions.assertNotNull(pp.getCommander(TestSpec.class).execute(TestSpec.RETURN_OBJECT));
		pp.teardown();
		Assertions.assertNull(pp.getCommander(TestSpec.class).execute(TestSpec.RETURN_OBJECT));
		Assertions.assertNull(pp.getCommander(TestSpec.class).executeAsync(TestSpec.RETURN_OBJECT));
	}

}
