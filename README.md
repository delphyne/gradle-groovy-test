[![Build Status](https://travis-ci.org/delphyne/gradle-groovy-test.svg?branch=master)](https://travis-ci.org/delphyne/gradle-groovy-test)

# groovy-test
A plugin to apply when you want to use Groovy in tests, but don't want it in your main sources.

## Installation

#### Within a standalone build.gradle
```groovy
buildscript {
	repositories {
		maven {
			name 'delphyne'
			url 'https://delphyne.github.io/.m2/'
		}
	}
	dependencies {
		classpath 'com.github.delphyne.gradle.groovy-test:groovy-test-plugin:0.0.1'
	}
}
```

#### With a buildSrc directory
##### buildSrc/build.gradle
```groovy
repositories {
	maven {
		name 'delphyne'
		url 'https://delphyne.github.io/.m2/'
	}
}

dependencies {
	compile 'com.github.delphyne.gradle.groovy-test:groovy-test-plugin:0.0.1'
}
```

##### build.gradle
```groovy
apply plugin: 'groovy-test'
```

##Usage
Install the plugin and add Groovy tests to src/test/groovy.  No additional steps are necessary.  Your tests will automatically be run by gradle via the test task.

## Configuration

The GroovyTestPlugin will function without any explicit configuration, but provides a convention, groovyTest with the following options:

Name    | Type   | Default Value        | Description
--------|--------|----------------------|---------------------------------------------------
version | string | GroovySystem.version | The version of groovy to add as a test dependency. 
