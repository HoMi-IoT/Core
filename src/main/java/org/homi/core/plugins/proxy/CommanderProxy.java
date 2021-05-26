package org.homi.core.plugins.proxy;

import java.util.concurrent.Future;

import org.homi.plugin.api.commander.Commander;
import org.homi.plugin.api.exceptions.InternalPluginException;
import org.homi.plugin.specification.ISpecification;
import org.homi.plugin.specification.exceptions.ArgumentLengthException;
import org.homi.plugin.specification.exceptions.InvalidArgumentException;

public class CommanderProxy<T extends Enum<T> & ISpecification> extends Commander<T>{

	private Commander<T> commander;

	public CommanderProxy(Class<T> spec,Commander<T> commander) {
		super(spec, null);
		this.commander = commander;
	}

	@Override
	public <C extends Enum<?> & ISpecification, R> R execute(C command, Object... args) throws InvalidArgumentException, ArgumentLengthException, InternalPluginException{
		if(commander == null)
			throw new InternalPluginException("comander not available");
		return commander.execute(command, args);
	}

	@Override
	public <C extends Enum<?> & ISpecification, R> Future<R> executeAsync(C command, Object... args){
		if(commander == null)
			throw new RuntimeException(new InternalPluginException("comander not available"));
		return commander.executeAsync(command, args);
	}

	public void detach() {
		this.commander=null;
	}
	
}
