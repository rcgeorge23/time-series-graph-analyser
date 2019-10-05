package tsga

import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import tsga.gatling.GatlingLogParser
import tsga.output.CsvPrinter

import static tsga.output.CsvPrinter.*

class ReportGenerator {

    static void generateReport(File inputDirectory, File outputDirectory, Map<String, Closure> expectedShapes) throws IOException {
        def scenarios = GatlingLogParser.buildScenarios(inputDirectory.listFiles(), expectedShapes)
        CsvPrinter.printSummary(scenarios)
        outputDirectory.mkdirs()

        OutputStream fileOutputStream = new FileOutputStream(new File(outputDirectory, "detailed_results.csv"))
        try {
            CsvPrinter.printDetailedResults(scenarios, fileOutputStream)
        } finally {
            fileOutputStream.flush()
            fileOutputStream.close()
        }

        String resultsHtml = IOUtils.toString(ReportGenerator.class.getResourceAsStream("/index.html"), "UTF-8")
        resultsHtml = resultsHtml.replace("{{DATA_SERIES_ACTUAL}}", renderChartJsActualResponseTimeTimeSeries(scenarios))
        resultsHtml = resultsHtml.replace("{{DATA_SERIES_MIN}}", renderChartJsMinExpectedResponseTimeTimeSeries(scenarios))
        resultsHtml = resultsHtml.replace("{{DATA_SERIES_MAX}}", renderChartJsMaxExpectedResponseTimeTimeSeries(scenarios))
        resultsHtml = resultsHtml.replace("{{DATA_SERIES_RSP}}", renderChartJsRelevantStatisticalPropertyResponseTimeTimeSeries(scenarios))
        FileUtils.write(new File("${outputDirectory}/index.html"), resultsHtml, "UTF-8")
        new File("${outputDirectory}/js").mkdirs()
        FileUtils.write(new File("${outputDirectory}/js/chartjs.min.css"), IOUtils.toString(ReportGenerator.class.getResourceAsStream("/js/chartjs.min.css"), "UTF-8"), "UTF-8")
        FileUtils.write(new File("${outputDirectory}/js/chartjs.min.js"), IOUtils.toString(ReportGenerator.class.getResourceAsStream("/js/chartjs.min.js"), "UTF-8"), "UTF-8")
        FileUtils.write(new File("${outputDirectory}/js/moment.js"), IOUtils.toString(ReportGenerator.class.getResourceAsStream("/js/moment.js"), "UTF-8"), "UTF-8")
    }


}
