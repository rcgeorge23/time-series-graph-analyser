package tsga

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics
import tsga.dsl.BehaviourPeriod

class Chunk {
    BehaviourPeriod behaviourPeriod
    TimeSeriesData timeSeriesData
    DescriptiveStatistics descriptiveStatistics

    public Chunk(BehaviourPeriod behaviourPeriod, TimeSeriesData timeSeriesData) {
        this.behaviourPeriod = behaviourPeriod
        this.timeSeriesData = timeSeriesData
        this.descriptiveStatistics = new DescriptiveStatistics(timeSeriesData.timeSeriesDataPoints*.responseTimeMillis*.toDouble() as double[])
    }

    double calculateStandardDeviation() {
        return descriptiveStatistics.standardDeviation
    }

    double calculateVariance() {
        return descriptiveStatistics.variance
    }

    double calculateMean() {
        return descriptiveStatistics.mean
    }

    double percentile(int percentile) {
        return descriptiveStatistics.getPercentile(percentile)
    }

    @Override
    String toString() {
        return "start time millis: ${timeSeriesData.timeSeriesDataPoints.first().timeSinceStartOfScenarioMillis},\t" +
                "end time millis: ${timeSeriesData.timeSeriesDataPoints.last().timeSinceStartOfScenarioMillis},\t" +
                "standard deviation: ${calculateStandardDeviation()},\t" +
                "variance: ${calculateVariance()},\t" +
                "mean: ${calculateMean()},\t" +
                "p50: ${percentile(50)},\t" +
                "p75: ${percentile(75)},\t" +
                "p98: ${percentile(98)}"
    }
}
