package tsga

import tsga.gatling.GatlingLogParser
import tsga.output.CsvPrinter

import static tsga.dsl.StatisticalProperty.MEAN
import static tsga.dsl.StatisticalProperty.P50
import static tsga.dsl.TimeUnit.seconds

class DataLoader {

    static def DATA_DIRECTORY = "/home/richard/workspace/time-series-graph-analyser/test_data"

    static def EXPECTED_SHAPES = [
            "aws_HeartbeatV1_EXPIRED": {
                between seconds, 0, 170, "initial load", {
                    assertThat MEAN is 80 withStandardDeviation 100
                }

                between seconds, 180, 300, "umv response time 300ms", {
                    assertThat MEAN is 300 withStandardDeviation 100
                }

                between seconds, 310, 410, "umv response time back to 100ms", {
                    assertThat MEAN is 80 withStandardDeviation 100
                }

                between seconds, 430, 530, "umv timeout", {
                    assertThat P50 between 1400, 1600
                }

                between seconds, 550, 600, "umv response time back to 100ms", {
                    assertThat MEAN is 80 withStandardDeviation 100
                }
            }
    ]

    static void main(String[] args) throws IOException {
        def scenarios = GatlingLogParser.buildScenarios(new File(DATA_DIRECTORY).listFiles(), EXPECTED_SHAPES)
        CsvPrinter.printSummary(scenarios)
//        CsvPrinter.printGatlingData(scenarios)


    }

}
