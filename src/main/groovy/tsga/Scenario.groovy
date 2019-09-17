package tsga

class Scenario {
    String name
    List<Chunk> chunks

    Scenario(String name, List<Chunk> chunks) {
        this.name = name
        this.chunks = chunks
    }

    String getName() {
        return name
    }

    List<Chunk> getChunks() {
        return chunks.sort { it.timeSeriesData.timeSeriesDataPoints.first().timeSinceStartOfScenarioMillis }
    }

    @Override
    String toString() {
        return "Scenario{" +
                "name='" + name + '\'' +
                ", chunks=" + chunks +
                '}'
    }
}
