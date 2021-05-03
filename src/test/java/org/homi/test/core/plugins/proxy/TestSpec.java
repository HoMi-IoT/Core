package org.homi.test.core.plugins.proxy;

import org.homi.plugin.specification.ISpecification;
import org.homi.plugin.specification.SpecificationID;

@SpecificationID(id = "TestSpec")
public enum TestSpec implements ISpecification{

	RETURN_NULL(Void.class),
	RETURN_STRING(String.class),
	RETURN_INTEGER(Integer.class),
	RETURN_FLOAT(Float.class),
	RETURN_OBJECT(Object.class),
	SEND_STRING(String.class, String.class),
	SEND_INTEGER(Void.class, Integer.class);
	

	private Class<?>[] parameterTypes;
	private Class<?> returnType;
	TestSpec(Class<?> returnType, Class<?> ...parameterTypes ) {
		this.parameterTypes = parameterTypes;
		this.returnType = returnType;
	}
	
	@Override
	public Class<?>[] getParameterTypes() {
		return this.parameterTypes;
	}
	
	@Override
	public Class<?> getReturnType() {
		return this.returnType;
	}

	
}