package tsga

class TimeSeriesDataPoint {
    private long timeSinceStartOfScenarioMillis
    private long responseTimeMillis

    public long getResponseTimeMillis() {
        return responseTimeMillis
    }
}
