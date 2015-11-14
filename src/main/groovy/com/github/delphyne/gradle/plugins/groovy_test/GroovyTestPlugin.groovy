package com.github.delphyne.gradle.plugins.groovy_test

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.GroovyPlugin

class GroovyTestPlugin implements Plugin<Project> {

	@Override
	void apply(Project project) {
		/*
		 * Get a handle to our convention object
		 */
		GroovyTestConvention convention = project.extensions.create('groovyTest', GroovyTestConvention)

		/*
		 * Determine if the groovy plugin is already applied
		 */
		GroovyPlugin existingPlugin = project.plugins.findPlugin(GroovyPlugin)
		if (!existingPlugin) {
			project.plugins.apply GroovyPlugin
			project.sourceSets.main.groovy.srcDirs = []
			project.tasks.remove(project.tasks.findByName('compileGroovy'))
		}

		/*
		 * If the user has not provided a default groovy version, warn and use whatever gradle is using
		 */
		if (!convention.version) {
			def gradleGroovyVersion = GroovySystem.version
			project.logger.warn("\n\nGroovy version for ${this.getClass().simpleName} has not been specified.  Defaulting to ${gradleGroovyVersion}")
			convention.version = gradleGroovyVersion
		}

		/*
		 * Apply the dependency to the test classpath
		 */
		project.dependencies {
			testCompile "org.codehaus.groovy:groovy-all:${convention.version}"
		}
	}
}
