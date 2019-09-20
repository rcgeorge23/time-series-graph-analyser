package tsga.output

import tsga.model.Scenario

class CsvPrinter {
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

    static void printGatlingData(List<Scenario> scenarios) {
        println("scenario name,section name,scenario elapsed time millis,response time millis")
        scenarios.each { scenario ->
            scenario.getSections().each { section ->
                section.timeSeriesData.timeSeriesDataPoints.each { timeSeriesDataPoint ->
                    println("${scenario.name},${section.name},${timeSeriesDataPoint.timeSinceStartOfScenarioMillis},${timeSeriesDataPoint.responseTimeMillis}")
                }
            }
        }
    }

}
