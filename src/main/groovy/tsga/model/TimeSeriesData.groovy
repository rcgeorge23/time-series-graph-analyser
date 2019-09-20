package tsga.model

class TimeSeriesData {
    private List<TimeSeriesDataPoint> timeSeriesDataPoints = new ArrayList<>()

    def add(TimeSeriesDataPoint timeSeriesDataPoint) {
        timeSeriesDataPoints.add(timeSeriesDataPoint)
    }

    List<TimeSeriesDataPoint> getTimeSubSeriesData(Long startTimeMillis, Long endTimeMillis) {
        timeSeriesDataPoints.sort { p1, p2 ->
            p1.timeSinceStartOfScenarioMillis <=> p2.timeSinceStartOfScenarioMillis
        }

        return timeSeriesDataPoints.findAll({ it.timeSinceStartOfScenarioMillis >= startTimeMillis && it.timeSinceStartOfScenarioMillis <= endTimeMillis })
    }

    List<TimeSeriesDataPoint> getTimeSeriesDataPoints() {
        return timeSeriesDataPoints
    }
}
