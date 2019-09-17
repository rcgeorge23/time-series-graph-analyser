package tsga

import tsga.dsl.ExpectedGraphShape

class ChunkBuilder {

    List<Section> buildChunks(Closure expectedGraphShape, TimeSeriesData allTimeSeriesDataForScenario) {
        ExpectedGraphShape shape = new ExpectedGraphShape()
        expectedGraphShape.delegate = shape
        expectedGraphShape()

        List<Section> chunks = []

        shape.behaviourPeriods.each { behaviourPeriod ->
            chunks << new Section(behaviourPeriod, new TimeSeriesData(timeSeriesDataPoints: allTimeSeriesDataForScenario.getTimeSubSeriesData(behaviourPeriod.getStartTimeMillis(), behaviourPeriod.getEndTimeMillis())))
        }

        return chunks
    }
}
