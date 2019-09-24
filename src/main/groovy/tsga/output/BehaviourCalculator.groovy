package tsga.output

import tsga.dsl.ExpectedBehaviourOperator
import tsga.model.Section

class BehaviourCalculator {

    static LongRange expectedResponseTimeRange(Section section) {
        switch (section.expectedBehaviourPeriod.expectedBehaviour.expectedBehaviourValue.expectedBehaviourOperator) {
            case ExpectedBehaviourOperator.BETWEEN:
                return new LongRange(
                        section.expectedBehaviourPeriod.expectedBehaviour.expectedBehaviourValue.minimumResponseTimeMillis,
                        section.expectedBehaviourPeriod.expectedBehaviour.expectedBehaviourValue.maximumResponseTimeMillis
                )
            case ExpectedBehaviourOperator.IS:
                def standardDeviation = section.expectedBehaviourPeriod.expectedBehaviour.expectedBehaviourValue.expectedBehaviourVariance.standardDeviation
                return new LongRange(
                        (Long) (section.expectedBehaviourPeriod.expectedBehaviour.expectedBehaviourValue.responseTimeMillis - standardDeviation / 2),
                        (Long) (section.expectedBehaviourPeriod.expectedBehaviour.expectedBehaviourValue.responseTimeMillis + standardDeviation / 2)
                )
        }

    }
}
