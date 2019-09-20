package tsga.model

import tsga.dsl.ExpectedGraphShape
import tsga.model.Section
import tsga.model.TimeSeriesData

class SectionBuilder {

    List<Section> buildSections(Closure expectedGraphShape, TimeSeriesData allTimeSeriesDataForScenario) {
        ExpectedGraphShape shape = new ExpectedGraphShape()
        expectedGraphShape.delegate = shape
        expectedGraphShape()

        List<Section> sections = []

        shape.behaviourPeriods.each { behaviourPeriod ->
            sections << new Section(shape.sectionName, behaviourPeriod, new TimeSeriesData(timeSeriesDataPoints: allTimeSeriesDataForScenario.getTimeSubSeriesData(behaviourPeriod.getStartTimeMillis(), behaviourPeriod.getEndTimeMillis())))
        }

        return sections
    }
}
