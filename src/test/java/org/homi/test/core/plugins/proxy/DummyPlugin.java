package org.homi.test.core.plugins.proxy;

import java.util.HashMap;

import org.homi.plugin.api.AbstractPlugin;
import org.homi.plugin.api.CommanderBuilder;
import org.homi.plugin.api.IReceiver;

public class DummyPlugin extends AbstractPlugin{

	@Override
	public void setup() {
		CommanderBuilder<TestSpec> cb = new CommanderBuilder<>(TestSpec.class);
		var mappings = new HashMap<String, IReceiver>();
		
		mappings.put("RETURN_NULL", (Object ...args)->{
			return null;
			});
		mappings.put("RETURN_STRING", (Object ...args)->{
			return "String";
			});
		mappings.put("RETURN_INTEGER", (Object ...args)->{
			return 2;
			});
		mappings.put("RETURN_FLOAT", (Object ...args)->{
			return 1.1f;
			});
		mappings.put("RETURN_OBJECT", (Object ...args)->{
			return new Object();
			});
		mappings.put("RETURN_WRONG_TYPE", (Object ...args)->{
			return "";
			});
		mappings.put("SEND_STRING", (Object ...args)->{
			return (String)args[0];
			});
		mappings.put("SEND_INTEGER", (Object ...args)->{
			int x = (int) args[0];
			return null;
			});
		this.addCommander(TestSpec.class, 
				cb.onCommandEquals(TestSpec.RETURN_NULL, (Object ...args)->{ return null;})
				.onCommandEquals(TestSpec.RETURN_STRING, (Object ...args)->{ return "String";})
				.onCommandEquals(TestSpec.RETURN_INTEGER, (Object ...args)->{ return 2;})
				.onCommandEquals(TestSpec.RETURN_OBJECT, (Object ...args)->{ return new Object();})
				.onCommandEquals(TestSpec.SEND_STRING, (Object ...args)->{ return (String)args[0];})
				.onCommandEquals(TestSpec.SEND_INTEGER, (Object ...args)->{ int x = (int) args[0];	return null; })
				.build()
		);
	}

	@Override
	public void teardown() {
		// TODO Auto-generated method stub
		
	}

}
