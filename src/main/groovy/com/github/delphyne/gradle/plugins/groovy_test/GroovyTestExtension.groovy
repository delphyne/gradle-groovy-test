package com.github.delphyne.gradle.plugins.groovy_test

import groovy.transform.ToString

/**
 * Configuration for the GroovyTest Plugin
 */
@ToString(includeNames = true)
class GroovyTestExtension {
	/**
	 * Explicit version of Groovy to use for the testCompile dependency.
	 * If not specified, a warning will be emitted and the version will
	 * be set to the verion used by the running Gradle.
	 */
	String version
}
