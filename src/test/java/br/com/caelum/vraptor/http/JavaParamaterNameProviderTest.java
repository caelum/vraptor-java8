package br.com.caelum.vraptor.http;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.reflect.Method;
import java.util.List;

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
		return asList(parameters).stream().map(p -> p.getName()).collect(toList());
	}
}
