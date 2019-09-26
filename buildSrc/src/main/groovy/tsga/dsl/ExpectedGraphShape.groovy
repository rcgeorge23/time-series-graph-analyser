package tsga.dsl

class ExpectedGraphShape {

    private List behaviourPeriods = new ArrayList<>()

    def between(TimeUnit timeUnit, double startTime, double endTime, String sectionName, Closure behaviourClosure) {
        ExpectedBehaviour behaviour = new ExpectedBehaviour()
        behaviourClosure.delegate = behaviour
        behaviourClosure()

        behaviourPeriods.add(new ExpectedBehaviourPeriod(
                name: sectionName,
                expectedBehaviour: behaviour,
                startTimeMillis: timeUnit.getChronoUnit().getDuration().multipliedBy(startTime.toLong()).toMillis(),
                endTimeMillis: timeUnit.getChronoUnit().getDuration().multipliedBy(endTime.toLong()).toMillis()
        ))
    }

    def and(int endMinute) {
        return endMinute
    }

    List<ExpectedBehaviourPeriod> getBehaviourPeriods() {
        return behaviourPeriods
    }
}
