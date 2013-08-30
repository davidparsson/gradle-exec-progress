package com.davator.gradle.plugins.progress

import org.gradle.api.logging.LogLevel

class ProgressLoggerStreamConfiguration {

    private ProgressLoggerOutputStream stream

    ProgressLoggerStreamConfiguration(ProgressLoggerOutputStream stream) {
        this.stream = stream
    }

    public void pattern(String findPattern) {
        stream.addPattern(findPattern)
    }

    public void pattern(String findPattern, Closure replace) {
        stream.addPattern(findPattern, replace)
    }

    public void logLevel(LogLevel logLevel) {
        stream.logLevel = logLevel
    }

}
