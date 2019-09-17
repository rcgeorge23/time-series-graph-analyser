package tsga

class TimeSeriesDataPoint {
    private long timeSinceStartOfScenarioMillis
    private long responseTimeMillis

    long getResponseTimeMillis() {
        return responseTimeMillis
    }

    long getTimeSinceStartOfScenarioMillis() {
        return timeSinceStartOfScenarioMillis
    }
}
