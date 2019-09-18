package tsga.dsl

class ExpectedBehaviour {

    private ExpectedBehaviourValue expectedBehaviourValue
    private BehaviourType behaviourType

    ExpectedBehaviourValue assertThat(BehaviourType behaviourType) {
        this.behaviourType = behaviourType
        this.expectedBehaviourValue = new ExpectedBehaviourValue()
    }
}
