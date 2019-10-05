package tsga.gatling

import tsga.model.SectionBuilder
import tsga.model.Scenario
import tsga.model.TimeSeriesData
import tsga.model.TimeSeriesDataPoint

import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Stream

class GatlingLogParser {
    static List<Scenario> buildScenarios(File[] gatlingLogFiles, Map<String, Closure> expectedScenarioGraphShapes) {
        List<Scenario> scenarios = []

        Long scenarioStartTimeMillis = testStartTime(gatlingLogFiles)

        Map<String, List<String>> aggregatedLinesForScenarios = [:]

        gatlingLogFiles.each { file ->
            expectedScenarioGraphShapes.keySet().each { scenarioName ->
                Stream<String> lines = Files.lines(Paths.get(file.absolutePath))
                List<String> linesForScenario = lines.findAll {
                    def lineParts = it.split("\t")
                    if (lineParts.length >= 4) {
                        it.split("\t")[3] == scenarioName
                    }
                }

                if (aggregatedLinesForScenarios.get(scenarioName) == null) {
                    aggregatedLinesForScenarios.put(scenarioName, linesForScenario)
                } else {
                    aggregatedLinesForScenarios.get(scenarioName).addAll(linesForScenario)
                }
            }
        }

        TimeSeriesData timeSeriesDataForScenario = new TimeSeriesData()

        aggregatedLinesForScenarios.each { scenarioName, aggregatedLogLines ->
            aggregatedLogLines.each { line ->
                String[] parts = line.split("\t")
                Long startTimestamp = Long.parseLong(parts[4])
                Long endTimestamp = Long.parseLong(parts[5])
                timeSeriesDataForScenario.add(new TimeSeriesDataPoint(
                        actualTimeMillis: startTimestamp,
                        timeSinceStartOfScenarioMillis: startTimestamp - scenarioStartTimeMillis,
                        responseTimeMillis: endTimestamp - startTimestamp)
                )
            }

            scenarios << new Scenario(scenarioName, new SectionBuilder().buildSections(expectedScenarioGraphShapes.get(scenarioName), timeSeriesDataForScenario), timeSeriesDataForScenario)
        }

        return scenarios
    }

    private static long testStartTime(File[] gatlingLogFiles) {
        gatlingLogFiles.collect { logFileStartTime(it) }.sort().first()
    }

    private static Long logFileStartTime(File file) {
        Stream<String> lines = Files.lines(Paths.get(file.absolutePath))
        return Long.parseLong(lines.filter { line -> line.startsWith("RUN") }.findFirst().get().split("\t")[3])
    }
}
