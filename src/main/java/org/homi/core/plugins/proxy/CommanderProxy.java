package org.homi.core.plugins.proxy;

import java.util.concurrent.Future;


import org.homi.plugin.api.Commander;
import org.homi.plugin.api.TypeMismatchException;
import org.homi.plugin.specification.ISpecification;

public class CommanderProxy<T extends Enum<T> & ISpecification> extends Commander<T>{

	private Commander<T> commander;

	public CommanderProxy(Class<T> spec,Commander<T> commander) {
		super(spec, null);
		this.commander = commander;
	}

	@Override
	public <C extends Enum<?> & ISpecification, R> R execute(C command, Object... args)
			throws TypeMismatchException, ClassCastException, IllegalArgumentException {
		if(commander == null)
			return null;
		return commander.execute(command, args);
	}

	@Override
	public <C extends Enum<?> & ISpecification, R> Future<R> executeAsync(C command, Object... args)
			throws TypeMismatchException, ClassCastException, IllegalArgumentException {
		if(commander == null)
			return null;
		return commander.executeAsync(command, args);
	}

	public void detach() {
		this.commander=null;
	}
	

}
