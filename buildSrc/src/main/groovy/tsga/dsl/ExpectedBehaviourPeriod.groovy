package tsga.dsl

class ExpectedBehaviourPeriod {
    String name
    ExpectedBehaviour expectedBehaviour
    Long startTimeMillis
    Long endTimeMillis


    ExpectedBehaviour getExpectedBehaviour() {
        return expectedBehaviour
    }

    Long getStartTimeMillis() {
        return startTimeMillis
    }

    Long getEndTimeMillis() {
        return endTimeMillis
    }
}
