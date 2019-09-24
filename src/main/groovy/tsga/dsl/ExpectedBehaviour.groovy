package tsga.dsl

class ExpectedBehaviour {

    ExpectedBehaviourValue expectedBehaviourValue
    StatisticalProperty statisticalProperty

    ExpectedBehaviourValue assertThat(StatisticalProperty statisticalProperty) {
        this.statisticalProperty = statisticalProperty
        this.expectedBehaviourValue = new ExpectedBehaviourValue()
    }
}
