package tsga.dsl

class ExpectedBehaviourVariance {

    Long standardDeviation

    ExpectedBehaviourVariance withStandardDeviation(Long standardDeviation) {
        this.standardDeviation = standardDeviation
        return this
    }

}
