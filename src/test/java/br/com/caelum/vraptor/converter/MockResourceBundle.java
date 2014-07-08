package br.com.caelum.vraptor.converter;

import static java.util.Collections.emptyEnumeration;

import java.util.Enumeration;
import java.util.ResourceBundle;

public class MockResourceBundle
	extends ResourceBundle {

	@Override
	public Enumeration<String> getKeys() {
		return emptyEnumeration();
	}

	@Override
	protected Object handleGetObject(String key) {
		return key;
	}
}
