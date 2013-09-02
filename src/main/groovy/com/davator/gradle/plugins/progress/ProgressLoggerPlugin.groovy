package com.davator.gradle.plugins.progress

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Exec

class ProgressLoggerPlugin implements Plugin<Project> {

    void apply(Project project) {
        Exec.metaClass.progress = { Closure closure ->
            def stream = new ProgressLoggerOutputStream(project)
            def configuration = new ProgressLoggerStreamConfiguration(stream)
            closure.delegate = configuration
            closure()
            closure.owner.standardOutput = stream
        }
    }

}
