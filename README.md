#VRaptor - Java 8 plugin

![Build status](https://secure.travis-ci.org/caelum/vraptor-java8.png)

Java 8 features for VRaptor 4 like:

* Java Parameter names with no external libraries;
* Support for `java.time` classes;
 

For a quick start, you can use this snippet in your maven POM:

```xml
<dependency>
    <groupId>br.com.caelum.vraptor</groupId>
    <artifactId>vraptor-java8</artifactId>
    <version>4.0.0.Final</version> <!--or the latest version-->
</dependency>
```

##How to alow parameter discovery

To allow parameter discovery you need to add the parameter `-parameters` in your compiler.

### Using maven
If you are using Maven you must add this option to your compiler plugin:

```xml
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-compiler-plugin</artifactId>
	<configuration>
		<source>1.8</source>
		<target>1.8</target>
		<compilerArguments>
			<parameters />
		</compilerArguments>
	</configuration>
</plugin>
```

### Using eclipse

If you are using eclipse, check  the box 'Store information about method parameters' at Preferences > Compiler options
