package tsga

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics
import tsga.dsl.ExpectedBehaviourPeriod

class Section {
    ExpectedBehaviourPeriod expectedBehaviourPeriod
    TimeSeriesData timeSeriesData
    DescriptiveStatistics descriptiveStatistics

    Section(ExpectedBehaviourPeriod expectedBehaviourPeriod, TimeSeriesData timeSeriesData) {
        this.expectedBehaviourPeriod = expectedBehaviourPeriod
        this.timeSeriesData = timeSeriesData
        this.descriptiveStatistics = new DescriptiveStatistics(timeSeriesData.timeSeriesDataPoints*.responseTimeMillis*.toDouble() as double[])
    }
}
