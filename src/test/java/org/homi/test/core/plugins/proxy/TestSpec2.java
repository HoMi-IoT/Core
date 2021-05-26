package org.homi.test.core.plugins.proxy;

import java.util.List;

import org.homi.plugin.specification.ISpecification;
import org.homi.plugin.specification.SpecificationHelper;
import org.homi.plugin.specification.SpecificationID;
import org.homi.plugin.specification.TypeDef;


@SpecificationID(id = "TestSpec2")
public enum TestSpec2 implements ISpecification{

	RETURN_NULL(Void.class),
	RETURN_FLOAT(Integer.class),
	C1(Void.class);
	
	private List<TypeDef<?>> parameterTypes;
	private TypeDef<?> returnType;
	TestSpec2(Object returnType, Object...parameterTypes ) {
		try {
			this.returnType = SpecificationHelper.processType(returnType);
			this.parameterTypes = SpecificationHelper.processTypes(parameterTypes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<TypeDef<?>> getParameterTypes() {
		return this.parameterTypes;
	}
	
	@Override
	public TypeDef<?> getReturnType() {
		return this.returnType;
	}
	
}