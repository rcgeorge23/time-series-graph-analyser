package tsga.dsl

class ExpectedBehaviour {

    private ExpectedBehaviourValue expectedBehaviourValue
    private StatisticalProperty statisticalProperty

    ExpectedBehaviourValue assertThat(StatisticalProperty statisticalProperty) {
        this.statisticalProperty = statisticalProperty
        this.expectedBehaviourValue = new ExpectedBehaviourValue()
    }
}
