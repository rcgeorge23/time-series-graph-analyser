package tsga.model

class Scenario {
    String name
    List<Section> sections
    TimeSeriesData allTimeSeriesData

    Scenario(String name, List<Section> sections, TimeSeriesData allTimeSeriesData) {
        this.name = name
        this.sections = sections
        this.allTimeSeriesData = allTimeSeriesData
    }

    TimeSeriesData getAllTimeSeriesData() {
        return allTimeSeriesData
    }

    String getName() {
        return name
    }

    List<Section> getSections() {
        return sections.sort { it.timeSeriesData.timeSeriesDataPoints.sort { it.timeSinceStartOfScenarioMillis }.first().timeSinceStartOfScenarioMillis }
    }

    @Override
    String toString() {
        return "Scenario{" +
                "name='" + name + '\'' +
                ", sections=" + sections +
                '}'
    }
}
