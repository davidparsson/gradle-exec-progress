package com.davator.gradle.plugins.progress

import org.gradle.api.Plugin
import org.gradle.api.Project

class ProgressLoggerPlugin implements Plugin<Project> {

    void apply(Project project) {
        project.ext.progress = { Closure closure ->
            def stream = new ProgressLoggerOutputStream(project)
            def configuration = new ProgressLoggerStreamConfiguration(stream)
            closure.delegate = configuration
            closure()
            closure.standardOutput = stream
        }
    }

}
