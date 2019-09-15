package tsga.dsl

class ExpectedGraphShape {

    private List behaviourPeriods = new ArrayList<>()

    def between(TimeUnit timeUnit, double startTime, double endTime, Closure behaviourClosure) {
        Behaviour behaviour = new Behaviour()
        behaviourClosure.delegate = behaviour
        behaviourClosure()

        behaviourPeriods.add(new BehaviourPeriod(
                behaviour: behaviour,
                startTimeMillis: timeUnit.getChronoUnit().getDuration().multipliedBy(startTime.toLong()).toMillis(),
                endTimeMillis: timeUnit.getChronoUnit().getDuration().multipliedBy(endTime.toLong()).toMillis()
        ))
    }

    def and(int endMinute) {
        return endMinute
    }

    public List<BehaviourPeriod> getBehaviourPeriods() {
        return behaviourPeriods
    }
}
