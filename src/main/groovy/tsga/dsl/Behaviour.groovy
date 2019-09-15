package tsga.dsl

class Behaviour {

    private BehaviourType behaviourType
    private TimeSeriesProperty timeSeriesProperty
    private Double value
    private Long initialResponseTimeMillis
    private Long finalResponseTimeMillis

    Behaviour with(TimeSeriesProperty timeSeriesProperty, double value) {
        this.timeSeriesProperty = timeSeriesProperty
        this.value = value
        return this
    }

    Behaviour rampUp(int initialResponseTime, int finalResponseTime) {
        this.behaviourType = BehaviourType.RAMP_UP
        this.initialResponseTimeMillis = initialResponseTime
        this.finalResponseTimeMillis = finalResponseTime
        return this
    }

    Behaviour rampDown(int initialResponseTime, int finalResponseTime) {
        this.behaviourType = BehaviourType.RAMP_DOWN
        this.initialResponseTimeMillis = initialResponseTime
        this.finalResponseTimeMillis = finalResponseTime
        return this
    }

    Behaviour constant(int responseTime) {
        this.behaviourType = BehaviourType.CONSTANT
        this.initialResponseTimeMillis = responseTime
        this.finalResponseTimeMillis = responseTime
        return this
    }
}
