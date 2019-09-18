package tsga.dsl

class ExpectedBehaviourVariance {

    private Long standardDeviation

    ExpectedBehaviourVariance withStandardDeviation(Long standardDeviation) {
        this.standardDeviation = standardDeviation
        return this
    }

}
