package tsga


import static tsga.dsl.BehaviourType.MEAN
import static tsga.dsl.BehaviourType.P50
import static tsga.dsl.TimeUnit.seconds

class DataLoader {

    static def DATA_DIRECTORY = "/home/richard/workspace/time-series-graph-analyser/test_data"

    static def EXPECTED_SHAPES = [
            "aws_HeartbeatV1_EXPIRED": {
                between seconds, 0, 170, {
                    assertThat MEAN is 80 withStandardDeviation 100
                }

                between seconds, 180, 300, {
                    assertThat MEAN is 300 withStandardDeviation 100
                }

                between seconds, 310, 410, {
                    assertThat MEAN is 80 withStandardDeviation 100
                }

                between seconds, 430, 530, {
                    assertThat P50 between 1400, 1600
                }

                between seconds, 550, 600, {
                    assertThat MEAN is 80 withStandardDeviation 100
                }
            }
    ]

    static void main(String[] args) throws IOException {
        List<Scenario> scenarios = GatlingLogParser.buildScenarios(new File(DATA_DIRECTORY).listFiles(), EXPECTED_SHAPES)

        println("scenario name,section start time millis,section end time millis,expected response time millis,expected maximum response time millis,expected minimum response time millis,assert on,with allowable standard deviation,p98,p75,p50,mean response time,response time standard deviation,assertion passed")
        scenarios.each { scenario ->
            scenario.getSections().each { chunk ->
                println("${scenario.name}," +
                        "${chunk.expectedBehaviourPeriod.startTimeMillis}," +
                        "${chunk.expectedBehaviourPeriod.endTimeMillis}," +
                        "${chunk.expectedBehaviourPeriod.expectedBehaviour.expectedBehaviourValue.responseTimeMillis}," +
                        "${chunk.expectedBehaviourPeriod.expectedBehaviour.expectedBehaviourValue.maximumResponseTimeMillis}," +
                        "${chunk.expectedBehaviourPeriod.expectedBehaviour.expectedBehaviourValue.minimumResponseTimeMillis}," +
                        "${chunk.expectedBehaviourPeriod.expectedBehaviour.behaviourType}," +
                        "${chunk.expectedBehaviourPeriod.expectedBehaviour.expectedBehaviourValue.expectedBehaviourVariance.standardDeviation}," +
                        "${chunk.descriptiveStatistics.getPercentile(98).round(2)}," +
                        "${chunk.descriptiveStatistics.getPercentile(75).round(2)}," +
                        "${chunk.descriptiveStatistics.getPercentile(50).round(2)}," +
                        "${chunk.descriptiveStatistics.mean.round(2)}," +
                        "${chunk.descriptiveStatistics.standardDeviation.round(2)}," +
                        "${chunk.passed}")
            }
        }
    }

}
