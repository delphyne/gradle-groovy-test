package com.github.delphyne.gradle.plugins.groovy_test

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.GroovyPlugin

class GroovyTestPlugin implements Plugin<Project> {

	@Override
	void apply(Project project) {
		/*
		 * Register our extension object
		 */
		project.extensions.create('groovyTest', GroovyTestExtension)

		/*
		 * Determine if the groovy plugin is already applied
		 */
		GroovyPlugin existingPlugin = project.plugins.findPlugin(GroovyPlugin)
		if (!existingPlugin) {
			project.plugins.apply GroovyPlugin
			project.sourceSets.main.groovy.srcDirs = []

			/*
			 * Remove compile groovy from the task graph
			 */
			project.tasks.each { Task t ->
				if (t.dependsOn.remove('compileGroovy')) {
					project.logger.debug("Removed compileGroovy from ${t.name}'s dependency.")
				}
			}

			/*
			 * Remove compileGroovy from the available tasks
			 */
			project.tasks.remove(project.tasks.findByName('compileGroovy'))
		}

		/*
		 * We can't use our extension until the project has been evaluated
		 */
		project.afterEvaluate {
			GroovyTestExtension extension = project.extensions.findByType(GroovyTestExtension)

			/*
			 * If the user has not provided a default groovy version, warn and use whatever gradle is using
			 */
			if (!extension.version) {
				def gradleGroovyVersion = GroovySystem.version
				project.logger.warn("\n\nGroovy version for ${this.getClass().simpleName} has not been specified.  Defaulting to ${gradleGroovyVersion}")
				extension.version = gradleGroovyVersion
			}

			/*
			 * Apply the dependency to the test classpath
			 */
			project.dependencies {
				testCompile "org.codehaus.groovy:groovy-all:${extension.version}"
			}
		}
	}
}
