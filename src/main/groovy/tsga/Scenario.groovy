package tsga

class Scenario {
    String name
    List<Section> sections

    Scenario(String name, List<Section> sections) {
        this.name = name
        this.sections = sections
    }

    String getName() {
        return name
    }

    List<Section> getSections() {
        return sections.sort { it.timeSeriesData.timeSeriesDataPoints.first().timeSinceStartOfScenarioMillis }
    }

    @Override
    String toString() {
        return "Scenario{" +
                "name='" + name + '\'' +
                ", sections=" + sections +
                '}'
    }
}
