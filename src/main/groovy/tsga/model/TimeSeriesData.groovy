package tsga.model

class TimeSeriesData {
    private List<TimeSeriesDataPoint> timeSeriesDataPoints = new ArrayList<>()

    def add(TimeSeriesDataPoint timeSeriesDataPoint) {
        timeSeriesDataPoints.add(timeSeriesDataPoint)
    }

    List<TimeSeriesDataPoint> getTimeSubSeriesData(Long startTimeMillis, Long endTimeMillis) {
        timeSeriesDataPoints.sort(new Comparator<TimeSeriesDataPoint>() {
            @Override
            int compare(TimeSeriesDataPoint timeSeriesDataPoint1, TimeSeriesDataPoint timeSeriesDataPoint2) {
                return timeSeriesDataPoint1.timeSinceStartOfScenarioMillis <=> timeSeriesDataPoint2.timeSinceStartOfScenarioMillis
            }
        })

        return timeSeriesDataPoints.findAll({ it.timeSinceStartOfScenarioMillis >= startTimeMillis && it.timeSinceStartOfScenarioMillis <= endTimeMillis })
    }

    List<TimeSeriesDataPoint> getTimeSeriesDataPoints() {
        return timeSeriesDataPoints
    }
}
