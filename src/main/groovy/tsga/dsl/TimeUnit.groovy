package tsga.dsl

import java.time.temporal.ChronoUnit

enum TimeUnit {
    minutes(ChronoUnit.MINUTES), seconds(ChronoUnit.SECONDS)

    private ChronoUnit chronoUnit

    private TimeUnit(ChronoUnit chronoUnit) {
        this.chronoUnit = chronoUnit
    }

    public ChronoUnit getChronoUnit() {
        return chronoUnit
    }
}
