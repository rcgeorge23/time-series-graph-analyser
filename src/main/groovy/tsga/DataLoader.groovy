package tsga

import org.apache.commons.io.FileUtils
import tsga.gatling.GatlingLogParser
import tsga.output.CsvPrinter

import static org.apache.commons.io.IOUtils.toString
import static tsga.dsl.StatisticalProperty.MEAN
import static tsga.dsl.StatisticalProperty.P50
import static tsga.dsl.TimeUnit.seconds
import static tsga.output.CsvPrinter.*

class DataLoader {

    static final String BUILD_DIRECTORY = "/home/richard/workspace/time-series-graph-analyser/target/"

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
//            ,
//            "aws_ConsolidatedStartStream_GB_EXPIRED": {
//                between seconds, 0, 170, "initial load", {
//                    assertThat MEAN is 80 withStandardDeviation 100
//                }
//
//                between seconds, 180, 300, "umv response time 300ms", {
//                    assertThat MEAN is 300 withStandardDeviation 100
//                }
//
//                between seconds, 310, 410, "umv response time back to 100ms", {
//                    assertThat MEAN is 80 withStandardDeviation 100
//                }
//
//                between seconds, 430, 530, "umv timeout", {
//                    assertThat P50 between 1400, 1600
//                }
//
//                between seconds, 550, 600, "umv response time back to 100ms", {
//                    assertThat MEAN is 80 withStandardDeviation 100
//                }
//            }
    ]

    static void main(String[] args) throws IOException {
        def scenarios = GatlingLogParser.buildScenarios(new File(DATA_DIRECTORY).listFiles(), EXPECTED_SHAPES)
        CsvPrinter.printSummary(scenarios)

        OutputStream fileOutputStream = new FileOutputStream(new File("/tmp/results.csv"))
        try {
            CsvPrinter.printDetailedResults(scenarios, fileOutputStream)

        } finally {
            fileOutputStream.flush()
            fileOutputStream.close()
        }

        String resultsHtml = toString(DataLoader.class.getResourceAsStream("/index.html"), "UTF-8")
        resultsHtml = resultsHtml.replace("{{DATA_SERIES_ACTUAL}}", renderChartJsActualResponseTimeTimeSeries(scenarios))
        resultsHtml = resultsHtml.replace("{{DATA_SERIES_MIN}}", renderChartJsMinExpectedResponseTimeTimeSeries(scenarios))
        resultsHtml = resultsHtml.replace("{{DATA_SERIES_MAX}}", renderChartJsMaxExpectedResponseTimeTimeSeries(scenarios))
        resultsHtml = resultsHtml.replace("{{DATA_SERIES_RSP}}", renderChartJsRelevantStatisticalPropertyResponseTimeTimeSeries(scenarios))
        FileUtils.write(new File("${BUILD_DIRECTORY}/index.html"), resultsHtml, "UTF-8")
        new File("${BUILD_DIRECTORY}/js").mkdirs()
        FileUtils.write(new File("${BUILD_DIRECTORY}/js/chartjs.min.css"), toString(DataLoader.class.getResourceAsStream("/js/chartjs.min.css"), "UTF-8"), "UTF-8")
        FileUtils.write(new File("${BUILD_DIRECTORY}/js/chartjs.min.js"), toString(DataLoader.class.getResourceAsStream("/js/chartjs.min.js"), "UTF-8"), "UTF-8")
        FileUtils.write(new File("${BUILD_DIRECTORY}/js/moment.js"), toString(DataLoader.class.getResourceAsStream("/js/moment.js"), "UTF-8"), "UTF-8")
    }


}
