package tsga

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics
import tsga.dsl.BehaviourType
import tsga.dsl.ExpectedBehaviourOperator
import tsga.dsl.ExpectedBehaviourPeriod

class Section {
    ExpectedBehaviourPeriod expectedBehaviourPeriod
    TimeSeriesData timeSeriesData
    DescriptiveStatistics descriptiveStatistics
    Boolean passed = false

    Section(ExpectedBehaviourPeriod expectedBehaviourPeriod, TimeSeriesData timeSeriesData) {
        this.expectedBehaviourPeriod = expectedBehaviourPeriod
        this.timeSeriesData = timeSeriesData
        this.descriptiveStatistics = new DescriptiveStatistics(timeSeriesData.timeSeriesDataPoints*.responseTimeMillis*.toDouble() as double[])

        double statToCompare = -1

        switch(expectedBehaviourPeriod.expectedBehaviour.behaviourType) {
            case BehaviourType.MEAN:
                statToCompare = descriptiveStatistics.mean
                break
            case BehaviourType.P50:
                statToCompare = descriptiveStatistics.getPercentile(50)
                break
            case BehaviourType.P75:
                statToCompare = descriptiveStatistics.getPercentile(75)
                break
            case BehaviourType.P98:
                statToCompare = descriptiveStatistics.getPercentile(98)
                break
        }

        switch(expectedBehaviourPeriod.expectedBehaviour.expectedBehaviourValue.expectedBehaviourOperator) {
            case ExpectedBehaviourOperator.IS:
                double maxAllowedValue = (statToCompare + (expectedBehaviourPeriod.expectedBehaviour.expectedBehaviourValue.expectedBehaviourVariance.standardDeviation / 2))
                double minAllowedValue = (statToCompare - (expectedBehaviourPeriod.expectedBehaviour.expectedBehaviourValue.expectedBehaviourVariance.standardDeviation / 2))
                passed = expectedBehaviourPeriod.expectedBehaviour.expectedBehaviourValue.responseTimeMillis >= minAllowedValue &&
                        expectedBehaviourPeriod.expectedBehaviour.expectedBehaviourValue.responseTimeMillis <= maxAllowedValue
                break
            case ExpectedBehaviourOperator.BETWEEN:
                passed = (statToCompare >= expectedBehaviourPeriod.expectedBehaviour.expectedBehaviourValue.minimumResponseTimeMillis &&
                        statToCompare <= expectedBehaviourPeriod.expectedBehaviour.expectedBehaviourValue.maximumResponseTimeMillis)
                break
        }

    }
}
