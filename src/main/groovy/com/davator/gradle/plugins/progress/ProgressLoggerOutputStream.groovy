package com.davator.gradle.plugins.progress

import org.gradle.api.Project
import org.gradle.api.logging.Logger
import org.gradle.api.logging.LogLevel
import org.gradle.logging.ProgressLogger
import org.gradle.logging.ProgressLoggerFactory

class ProgressLoggerOutputStream extends OutputStream {
    Map<String, Closure> patterns = new HashMap<String, Closure>()
    ProgressLogger progressLogger
    Logger logger
    StringBuilder line = new StringBuilder()
    Boolean started = false
    LogLevel logLevel = LogLevel.INFO
    int NEW_LINE_BYTE = (int) '\n'

    ProgressLoggerOutputStream(Project project) {
        this.progressLogger = project.services.get(ProgressLoggerFactory).newOperation(getClass())
        this.logger = project.logger
    }

    public void write(int b) {
        if (b != NEW_LINE_BYTE) {
            line.append((char) b)
        } else {
            logMatchingLine();
        }
    }

    public void close() {
        if (started) {
            progressLogger.completed()
            started = false
        }
    }

    public void addPattern(String findPattern) {
        addPattern(findPattern, null)
    }

    public void addPattern(String findPattern, Closure replace) {
        patterns.put(findPattern, replace)
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel
    }

    private void logMatchingLine() {
        def lastLine = consumeLastLine()
        logger.log(logLevel, lastLine)
        patterns.each { findPattern, replace ->
            if (lastLine.matches(findPattern)) {
                if (replace != null) {
                    log(lastLine.replaceFirst(findPattern, replace))
                } else {
                    log(lastLine)
                }
            }
        }
    }

    private String consumeLastLine() {
        String lastLine = line.toString()
        line.setLength(0)
        lastLine
    }

    private void log(String line) {
        def logger = progressLogger
        if (started) {
            logger.progress(line)
        } else {
            logger.setDescription("-")
            logger.started(line)
            started = true
        }
    }

}