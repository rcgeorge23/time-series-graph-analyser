package tsga

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics
import tsga.dsl.BehaviourPeriod

class Chunk {
    BehaviourPeriod behaviourPeriod
    TimeSeriesData timeSeriesData
    DescriptiveStatistics descriptiveStatistics

    Chunk(BehaviourPeriod behaviourPeriod, TimeSeriesData timeSeriesData) {
        this.behaviourPeriod = behaviourPeriod
        this.timeSeriesData = timeSeriesData
        this.descriptiveStatistics = new DescriptiveStatistics(timeSeriesData.timeSeriesDataPoints*.responseTimeMillis*.toDouble() as double[])
    }
}
