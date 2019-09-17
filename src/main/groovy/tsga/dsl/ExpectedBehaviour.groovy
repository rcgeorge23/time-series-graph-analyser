package tsga.dsl

class ExpectedBehaviour {

    private BehaviourType behaviourType
    private Double expectedMaxStandardDeviation
    private Long expectedResponseTimeMillis

    ExpectedBehaviour standardDeviation(double expectedMaxStandardDeviation) {
        this.expectedMaxStandardDeviation = expectedMaxStandardDeviation
        return this
    }

    ExpectedBehaviour mean(int responseTime) {
        return build(BehaviourType.MEAN, responseTime)
    }

    ExpectedBehaviour p98(int responseTime) {
        return build(BehaviourType.P98, responseTime)
    }

    ExpectedBehaviour p75(int responseTime) {
        return build(BehaviourType.P75, responseTime)
    }

    ExpectedBehaviour p50(int responseTime) {
        return build(BehaviourType.P50, responseTime)
    }

    private ExpectedBehaviour build(BehaviourType type, double responseTimeMillis) {
        this.behaviourType = type
        this.expectedResponseTimeMillis = responseTimeMillis
        return this
    }
}
