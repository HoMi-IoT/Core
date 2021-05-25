package org.homi.test.core.plugins.proxy;

import org.homi.core.plugins.proxy.BasicPluginProxy;
import org.homi.plugin.api.basicplugin.AbstractBasicPlugin;
import org.homi.plugin.api.exceptions.InternalPluginException;
import org.homi.plugin.api.exceptions.PluginUnavailableException;
import org.homi.plugin.specification.exceptions.ArgumentLengthException;
import org.homi.plugin.specification.exceptions.InvalidArgumentException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PluginProxyTester {
	static AbstractBasicPlugin ap;
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
		BasicPluginProxy pp = new BasicPluginProxy(ap);
	}
	
	@Test
	void pluginProxyReturnsCommanderOfGivenSpec() throws ArgumentLengthException, InternalPluginException, PluginUnavailableException, InvalidArgumentException {
		BasicPluginProxy pp = new BasicPluginProxy(ap);
		pp.setup();
		Assertions.assertNotNull(pp.getCommander(TestSpec.class));
		Assertions.assertNull(pp.getCommander(TestSpec.class).execute(TestSpec.RETURN_NULL));
	}

	@Test
	void getCommanderReturnsNullWhenPassingUnimplementedSpec() throws PluginUnavailableException {
		BasicPluginProxy pp = new BasicPluginProxy(ap);
		pp.setup();
		Assertions.assertNull(pp.getCommander(TestSpec2.class));
	}
	
	@Test
	void pluginProxyTeardownMakesAllCommandsReturnNull() throws InvalidArgumentException, ArgumentLengthException, InternalPluginException, PluginUnavailableException {
		BasicPluginProxy pp = new BasicPluginProxy(ap);
		pp.setup();
		Assertions.assertNotNull(pp.getCommander(TestSpec.class).execute(TestSpec.RETURN_OBJECT));
		pp.teardown();
		Assertions.assertThrows(PluginUnavailableException.class, () -> {pp.getCommander(TestSpec.class).execute(TestSpec.RETURN_OBJECT);});
		Assertions.assertThrows(PluginUnavailableException.class, () -> { pp.getCommander(TestSpec.class).executeAsync(TestSpec.RETURN_OBJECT);});
	}

}
