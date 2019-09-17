package tsga.dsl

class ExpectedBehaviourPeriod {
    private ExpectedBehaviour expectedBehaviour
    private Long startTimeMillis
    private Long endTimeMillis

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
