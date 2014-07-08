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

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Stream;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.http.JavaParamaterNameProviderTest.Airplane.Route;

public class JavaParamaterNameProviderTest {

	private JavaParameterNameProvider provider;

	@Before
	public void setup() {
		provider = new JavaParameterNameProvider();
	}

	@Test
	public void shouldNamePrimitiveTypeAsItsSimpleName() throws Exception {
		Method method = Airplane.class.getDeclaredMethod("fly", String.class, Route.class, int.class);
		Parameter[] namesFor = provider.parametersFor(method);
		assertThat(toNames(namesFor), Matchers.contains("registration", "route", "ceilingService"));
	}

	@Test
	public void shouldNameArrayAsItsSimpleTypeName() throws Exception {
		Method method = Airplane.class.getDeclaredMethod("add", String[].class);
		Parameter[] namesFor = provider.parametersFor(method);
		assertThat(toNames(namesFor), Matchers.contains("pilots"));
	}

	@Test
	public void shouldNameGenericCollectionUsingOf() throws Exception {
		Method method = Airplane.class.getDeclaredMethod("add", List.class);
		Parameter[] namesFor = provider.parametersFor(method);
		assertThat(toNames(namesFor), Matchers.contains("passengers"));
	}

	public static class Airplane {
		public static class Route {
		}

		public void fly(String registration, Route route, int ceilingService) {
		}

		void add(String[] pilots) {
		}

		void add(List<String> passengers) {
		}
	}

	private List<String> toNames(Parameter[] parameters) {
		return Stream.of(parameters).map(Parameter::getName).collect(toList());
	}
}
