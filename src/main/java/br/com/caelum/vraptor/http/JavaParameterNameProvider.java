package br.com.caelum.vraptor.http;

import static com.google.common.base.Preconditions.checkState;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.interceptor.Interceptor;

/**
 * Provides parameter names for a {@link Method} or {@link Constructor}. This class is really not necessary
 * since JDK 8 can discovery natively. But keep easily to maintain a branch compatible with JDK 1.7 that needs
 * Paranamer.
 * 
 * @author Otávio Scherer Garcia
 */
@ApplicationScoped
@Alternative
@Priority(Interceptor.Priority.LIBRARY_AFTER + 10)
public class JavaParameterNameProvider implements ParameterNameProvider {

	@Override
	public Parameter[] parametersFor(AccessibleObject executable) {
		checkState(executable instanceof Executable, "Only methods or constructors are available");

		java.lang.reflect.Parameter[] parameters = getMethodParameters(executable);
		Parameter[] out = new Parameter[parameters.length];

		for (int i = 0; i < out.length; i++) {
			out[i] = new Parameter(i, parameters[i].getName(), executable);
		}

		return out;
	}

	private java.lang.reflect.Parameter[] getMethodParameters(AccessibleObject executable) {
		return ((Executable) executable).getParameters();
	}
}
