package tsga

import tsga.dsl.ExpectedGraphShape

class ChunkBuilder {

    List<Chunk> buildChunks(Closure expectedGraphShape, TimeSeriesData allTimeSeriesDataForScenario) {
        ExpectedGraphShape shape = new ExpectedGraphShape()
        expectedGraphShape.delegate = shape
        expectedGraphShape()

        List<Chunk> chunks = []

        shape.behaviourPeriods.each { behaviourPeriod ->
            chunks << new Chunk(behaviourPeriod, new TimeSeriesData(timeSeriesDataPoints: allTimeSeriesDataForScenario.getTimeSubSeriesData(behaviourPeriod.getStartTimeMillis(), behaviourPeriod.getEndTimeMillis())))
        }

        return chunks
    }
}
