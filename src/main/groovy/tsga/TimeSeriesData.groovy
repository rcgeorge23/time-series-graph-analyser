package tsga

class TimeSeriesData {
    private List<TimeSeriesDataPoint> timeSeriesDataPoints = new ArrayList<>()

    def add(TimeSeriesDataPoint timeSeriesDataPoint) {
        timeSeriesDataPoints.add(timeSeriesDataPoint)
    }

    List<TimeSeriesDataPoint> getTimeSubSeriesData(Long startTimeMillis, Long endTimeMillis) {
        return timeSeriesDataPoints.findAll({ it.timeSinceStartOfScenarioMillis >= startTimeMillis && it.timeSinceStartOfScenarioMillis <= endTimeMillis })
    }

    public List<TimeSeriesDataPoint> getTimeSeriesDataPoints() {
        return timeSeriesDataPoints
    }
}
