package org.homi.test.core.plugins.proxy;


import org.homi.core.plugins.proxy.CommanderProxy;
import org.homi.plugin.api.AbstractPlugin;
import org.homi.plugin.api.Commander;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommanderProxyTester {

	private static Commander<TestSpec> commander;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		AbstractPlugin ap = new DummyPlugin();
		ap.setup();
		commander = ap.getCommander(TestSpec.class);
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
	void InstantiateNewCommanderProxy() {
		new CommanderProxy(TestSpec.class, commander);
	}
	
	@Test
	void sendCommandThroughCommanderProxy() {
		Commander<TestSpec> testCommander =  new CommanderProxy(TestSpec.class, commander);
		Assertions.assertDoesNotThrow(()->testCommander.execute(TestSpec.RETURN_NULL));
		Assertions.assertDoesNotThrow(()->testCommander.executeAsync(TestSpec.RETURN_NULL));
	}
	
	@Test
	void passParameterWithCommandThroughCommanderProxy() {
		Commander<TestSpec> testCommander =  new CommanderProxy(TestSpec.class, commander);
		Assertions.assertDoesNotThrow(()->testCommander.execute(TestSpec.SEND_INTEGER, 13));
	}
}
