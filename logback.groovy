appender('CONSOLE', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = /{ "timestamp" : "%d{HH:mm:ss.SSS}", "thread" : "%thread", "level" : "%level", "logger" : "%logger", "message" : %msg }/
    }
}

root(INFO, ["CONSOLE"])
