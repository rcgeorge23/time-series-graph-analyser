package tsga.dsl

class ExpectedBehaviourValue {

    Long responseTimeMillis
    Long minimumResponseTimeMillis
    Long maximumResponseTimeMillis
    ExpectedBehaviourVariance expectedBehaviourVariance
    ExpectedBehaviourOperator expectedBehaviourOperator

    ExpectedBehaviourVariance is(long responseTimeMillis) {
        this.expectedBehaviourOperator = ExpectedBehaviourOperator.IS
        this.expectedBehaviourVariance = new ExpectedBehaviourVariance()
        this.responseTimeMillis = responseTimeMillis
        return expectedBehaviourVariance
    }

    ExpectedBehaviourVariance between(long minimumResponseTimeMillis, long maximumResponseTimeMillis) {
        this.expectedBehaviourOperator = ExpectedBehaviourOperator.BETWEEN
        this.expectedBehaviourVariance = new ExpectedBehaviourVariance()
        this.minimumResponseTimeMillis = minimumResponseTimeMillis
        this.maximumResponseTimeMillis = maximumResponseTimeMillis
        return expectedBehaviourVariance
    }
}
