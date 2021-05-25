package org.homi.test.core.plugins.proxy;

import org.homi.plugin.specification.ISpecification;
import org.homi.plugin.specification.SpecificationHelper;
import org.homi.plugin.specification.SpecificationID;
import org.homi.plugin.specification.TypeDef;

@SpecificationID(id = "TestSpec")
public enum TestSpec implements ISpecification{

	RETURN_NULL(Void.class),
	RETURN_STRING(String.class),
	RETURN_INTEGER(Integer.class),
	RETURN_FLOAT(Float.class),
	RETURN_OBJECT(Object.class),
	SEND_STRING(String.class, String.class),
	SEND_INTEGER(Void.class, Integer.class);
	
	private TypeDef<?>[] parameterTypes;
	private TypeDef<?> returnType;
	TestSpec(Object returnType, Object...parameterTypes ) {
		try {
			this.returnType = SpecificationHelper.processType(returnType);
			this.parameterTypes = new TypeDef<?>[parameterTypes.length];
			
			for(int i =0; i<parameterTypes.length; i++) {
				this.parameterTypes[i] = SpecificationHelper.processType(parameterTypes[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public TypeDef<?>[] getParameterTypes() {
		return this.parameterTypes;
	}
	
	@Override
	public TypeDef<?> getReturnType() {
		return this.returnType;
	}
	
}