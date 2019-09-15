package tsga.dsl

class BehaviourPeriod {
    private Behaviour behaviour
    private Long startTimeMillis
    private Long endTimeMillis

    Behaviour getBehaviour() {
        return behaviour
    }

    Long getStartTimeMillis() {
        return startTimeMillis
    }

    Long getEndTimeMillis() {
        return endTimeMillis
    }
}
