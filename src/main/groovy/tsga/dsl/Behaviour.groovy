package tsga.dsl

class Behaviour {

    private BehaviourType behaviourType
    private Double expectedMaxStandardDeviation
    private Long expectedResponseTimeMillis

    Behaviour standardDeviation(double expectedMaxStandardDeviation) {
        this.expectedMaxStandardDeviation = expectedMaxStandardDeviation
        return this
    }

    Behaviour mean(int responseTime) {
        return build(BehaviourType.MEAN, responseTime)
    }

    Behaviour p98(int responseTime) {
        return build(BehaviourType.P98, responseTime)
    }

    Behaviour p75(int responseTime) {
        return build(BehaviourType.P75, responseTime)
    }

    Behaviour p50(int responseTime) {
        return build(BehaviourType.P50, responseTime)
    }

    private Behaviour build(BehaviourType type, double responseTimeMillis) {
        this.behaviourType = type
        this.expectedResponseTimeMillis = responseTimeMillis
        return this
    }
}
