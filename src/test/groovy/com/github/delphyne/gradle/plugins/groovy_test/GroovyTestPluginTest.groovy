package com.github.delphyne.gradle.plugins.groovy_test

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.internal.project.AbstractProject
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.plugins.GroovyPlugin
import org.gradle.testfixtures.ProjectBuilder
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import static org.mockito.Mockito.doReturn
import static org.mockito.Mockito.spy

@Test
class GroovyTestPluginTest {

	Project project
	Plugin plugin

	@BeforeMethod(alwaysRun = true)
	def setup() {
		project = ProjectBuilder.builder().build()
		plugin = new GroovyTestPlugin()
	}

	void testGroovyPluginAlreadyApplied() {
		project.plugins.apply(GroovyPlugin)
		plugin.apply(project)

		assert project.tasks.find { it.name == 'compileGroovy' },
				"If an existing GroovyPlugin has been applied, it's compile task must not be affected."
	}

	void testDefaults() {
		plugin.apply(project)
		((AbstractProject)project).evaluate()

		assert !project.tasks.find { it.name == 'compileGroovy' },
				"The compileGroovy task must be removed."
		assert project.tasks.find { it.name == 'compileTestGroovy' },
				"The compileTestGroovy task must not be removed."
		assert !project.configurations.getByName('compile').dependencies.find { it.name.startsWith 'groovy' },
				"The groovy dependency must not be applied to the compile configuration."
		assert project.configurations.getByName('testCompile').dependencies.find {
			it.name.startsWith('groovy') && it.version == GroovySystem.version
		}, "The groovy dependency must be applied to the testCompile configuration, and defaults to the gradle Groovy version."
	}

	void testApplyWithConventionVersion() {
		String expected = 'NOT_A_REAL_VERSION'
		plugin.apply(project)
		project.groovyTest {
			version = 'NOT_A_REAL_VERSION'
		}
		((AbstractProject)project).evaluate()

		assert project.configurations.getByName('testCompile').dependencies.find {
			it.name.startsWith('groovy') && it.version == expected
		}, "The groovy dependency must be applied to the testCompile configuration and should match the convention version."
	}
}
