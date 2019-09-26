package tsga.output

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.io.IOUtils
import tsga.model.Scenario
import tsga.output.chartjs.DataPoint

class CsvPrinter {
    static int DOWNSAMPLE_FACTOR = 10

    static void printSummary(List<Scenario> scenarios) {
        println("scenario name,section start time millis,section end time millis,expected response time millis,expected maximum response time millis,expected minimum response time millis,assert on,with allowable standard deviation,p98,p75,p50,mean response time,response time standard deviation,assertion passed")
        scenarios.each { scenario ->
            scenario.getSections().each { section ->
                println("${scenario.name}," +
                        "${section.expectedBehaviourPeriod.startTimeMillis}," +
                        "${section.expectedBehaviourPeriod.endTimeMillis}," +
                        "${section.expectedBehaviourPeriod.expectedBehaviour.expectedBehaviourValue.responseTimeMillis ?: ''}," +
                        "${section.expectedBehaviourPeriod.expectedBehaviour.expectedBehaviourValue.maximumResponseTimeMillis ?: ''}," +
                        "${section.expectedBehaviourPeriod.expectedBehaviour.expectedBehaviourValue.minimumResponseTimeMillis ?: ''}," +
                        "${section.expectedBehaviourPeriod.expectedBehaviour.statisticalProperty}," +
                        "${section.expectedBehaviourPeriod.expectedBehaviour.expectedBehaviourValue.expectedBehaviourVariance.standardDeviation ?: ''}," +
                        "${section.descriptiveStatistics.getPercentile(98).round(2)}," +
                        "${section.descriptiveStatistics.getPercentile(75).round(2)}," +
                        "${section.descriptiveStatistics.getPercentile(50).round(2)}," +
                        "${section.descriptiveStatistics.mean.round(2)}," +
                        "${section.descriptiveStatistics.standardDeviation.round(2)}," +
                        "${section.passed}")
            }
        }
    }

    static void printDetailedResults(List<Scenario> scenarios, OutputStream outputStream) {
        IOUtils.write("scenario name,section name,scenario elapsed time millis,response time millis,p98,p75,p50,mean response time,actual standard deviation,expected standard deviation,statistical property,statistical property value,expected min response time millis,expected max response time millis,assertion passed\n", outputStream, "UTF-8")
        scenarios.each { scenario ->
            scenario.getSections().each { section ->
                section.timeSeriesData.timeSeriesDataPoints.each { timeSeriesDataPoint ->
                    LongRange expectedResponseTimeRange = BehaviourCalculator.expectedResponseTimeRange(section)

                    IOUtils.write(
                            "${scenario.name}," +
                                    "${section.name}," +
                                    "${timeSeriesDataPoint.timeSinceStartOfScenarioMillis}," +
                                    "${timeSeriesDataPoint.responseTimeMillis}," +
                                    "${section.descriptiveStatistics.getPercentile(98).round(2)}," +
                                    "${section.descriptiveStatistics.getPercentile(75).round(2)}," +
                                    "${section.descriptiveStatistics.getPercentile(50).round(2)}," +
                                    "${section.descriptiveStatistics.mean.round(2)}," +
                                    "${section.descriptiveStatistics.standardDeviation.round(2)}," +
                                    "${section.expectedBehaviourPeriod.expectedBehaviour.expectedBehaviourValue.expectedBehaviourVariance.standardDeviation ?: ''}," +
                                    "${section.expectedBehaviourPeriod.expectedBehaviour.statisticalProperty}," +
                                    "${section.statToCompare}," +
                                    "${expectedResponseTimeRange?.from ?: ''}," +
                                    "${expectedResponseTimeRange?.to ?: ''}," +
                                    "${section.passed}\n",
                            outputStream,
                            "UTF-8"
                    )
                }
            }
        }
    }

    static String renderChartJsActualResponseTimeTimeSeries(List<Scenario> scenarios) {
        List<DataPoint> dataPoints = []
        scenarios.each { scenario ->
            scenario.getSections().each { section ->
                section.timeSeriesData.timeSeriesDataPoints.eachWithIndex { timeSeriesDataPoint, index ->
                    if (index % DOWNSAMPLE_FACTOR == 0) dataPoints.add(new DataPoint(t: timeSeriesDataPoint.actualTimeMillis, y: timeSeriesDataPoint.responseTimeMillis))
                }
            }
        }
        return new ObjectMapper().writeValueAsString(dataPoints)
    }

    static String renderChartJsMinExpectedResponseTimeTimeSeries(List<Scenario> scenarios) {
        List<DataPoint> dataPoints = []
        scenarios.each { scenario ->
            scenario.getSections().each { section ->
                LongRange expectedResponseTimeRange = BehaviourCalculator.expectedResponseTimeRange(section)
                section.timeSeriesData.timeSeriesDataPoints.eachWithIndex { timeSeriesDataPoint, index ->
                    if (index % DOWNSAMPLE_FACTOR == 0) dataPoints.add(new DataPoint(t: timeSeriesDataPoint.actualTimeMillis, y: expectedResponseTimeRange?.from ?: ''))
                }
            }
        }
        return new ObjectMapper().writeValueAsString(dataPoints)
    }

    static String renderChartJsMaxExpectedResponseTimeTimeSeries(List<Scenario> scenarios) {
        List<DataPoint> dataPoints = []
        scenarios.each { scenario ->
            scenario.getSections().each { section ->
                LongRange expectedResponseTimeRange = BehaviourCalculator.expectedResponseTimeRange(section)
                section.timeSeriesData.timeSeriesDataPoints.eachWithIndex { timeSeriesDataPoint, index ->
                    if (index % DOWNSAMPLE_FACTOR == 0) dataPoints.add(new DataPoint(t: timeSeriesDataPoint.actualTimeMillis, y: expectedResponseTimeRange?.to ?: ''))
                }
            }
        }
        return new ObjectMapper().writeValueAsString(dataPoints)
    }

    static String renderChartJsRelevantStatisticalPropertyResponseTimeTimeSeries(List<Scenario> scenarios) {
        List<DataPoint> dataPoints = []
        scenarios.each { scenario ->
            scenario.getSections().each { section ->
                section.timeSeriesData.timeSeriesDataPoints.eachWithIndex { timeSeriesDataPoint, index ->
                    if (index % DOWNSAMPLE_FACTOR == 0) dataPoints.add(new DataPoint(t: timeSeriesDataPoint.actualTimeMillis, y: section.statToCompare ?: ''))
                }
            }
        }
        return new ObjectMapper().writeValueAsString(dataPoints)
    }

}
