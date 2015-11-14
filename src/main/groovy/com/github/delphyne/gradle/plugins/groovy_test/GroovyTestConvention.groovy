package com.github.delphyne.gradle.plugins.groovy_test

/**
 * Configuration for the GroovyTest Plugin
 */
class GroovyTestConvention {
    /**
     * Explicit version of Groovy to use for the testCompile dependency.
     * If not specified, a warning will be emitted and the version used
     * by the current version of Gradle will be used.
     */
    def version
}
