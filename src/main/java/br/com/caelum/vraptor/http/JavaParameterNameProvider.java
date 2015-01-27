/***
 * Copyright (c) 2009 Caelum - www.caelum.com.br/opensource
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.caelum.vraptor.http;

import static com.google.common.base.Preconditions.checkState;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.Arrays;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.interceptor.Interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides parameter names for a {@link Method} or {@link Constructor}. This class is really not necessary
 * since JDK 8 can discovery natively. But keep easily to maintain a branch compatible with JDK 1.7 that needs
 * Paranamer.
 * 
 * @author Otávio Scherer Garcia
 */
@ApplicationScoped
@Alternative
@Priority(Interceptor.Priority.LIBRARY_BEFORE + 10)
public class JavaParameterNameProvider implements ParameterNameProvider {

	private static final Logger logger = LoggerFactory.getLogger(JavaParameterNameProvider.class);

	@Override
	public Parameter[] parametersFor(AccessibleObject executable) {
		java.lang.reflect.Parameter[] parameters = getMethodParameters(executable);
		Parameter[] out = new Parameter[parameters.length];

		for (int i = 0; i < out.length; i++) {
			checkIfNameIsPresent(parameters[i]);
			out[i] = new Parameter(i, parameters[i].getName(), executable);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("parameter names for {}: {}", executable, Arrays.toString(out));
		}

		return out;
	}

	private void checkIfNameIsPresent(java.lang.reflect.Parameter parameter) {
		if (!parameter.isNamePresent()) {
			String msg = String.format("Parameters aren't present for %s. You must compile your code with -parameters argument. \n If you are using eclipse, set 'Store information about method parameters' to true at 'Compiler' options", 
					parameter.getDeclaringExecutable().getName());
			throw new AssertionError(msg);
		}

	}

	private java.lang.reflect.Parameter[] getMethodParameters(AccessibleObject executable) {
		checkState(executable instanceof Executable, "Only methods or constructors are available");
		return ((Executable) executable).getParameters();
	}
}
