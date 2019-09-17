package tsga


import static tsga.dsl.TimeUnit.seconds

class DataLoader {

    static def DATA_DIRECTORY = "/home/richard/workspace/time-series-graph-analyser/test_data"

    static def EXPECTED_SHAPES = [
            "aws_HeartbeatV1_EXPIRED": {
                between seconds, 0, 170, {
                    mean 80 standardDeviation 100
                }

                between seconds, 180, 300, {
                    mean 300 standardDeviation 100
                }

                between seconds, 310, 410, {
                    mean 80 standardDeviation 100
                }

                between seconds, 430, 530, {
                    p50 1500 standardDeviation 100
                }

                between seconds, 550, 600, {
                    mean 80 standardDeviation 100
                }
            }
    ]

    static void main(String[] args) throws IOException {
        List<Scenario> scenarios = new GatlingLogParser().buildScenarios(new File(DATA_DIRECTORY).listFiles(), EXPECTED_SHAPES)

        println("scenario name, section start time millis,section end time millis,expected response time millis,expected standard deviation,assert on,p98,p75,p50,mean response time,response time standard deviation")
        scenarios.each { scenario ->
            scenario.getSections().each { chunk ->
                println("${scenario.name}," +
                        "${chunk.expectedBehaviourPeriod.startTimeMillis}," +
                        "${chunk.expectedBehaviourPeriod.endTimeMillis}," +
                        "${chunk.expectedBehaviourPeriod.expectedBehaviour.expectedResponseTimeMillis}," +
                        "${chunk.expectedBehaviourPeriod.expectedBehaviour.expectedMaxStandardDeviation}," +
                        "${chunk.expectedBehaviourPeriod.expectedBehaviour.behaviourType}," +
                        "${chunk.descriptiveStatistics.getPercentile(98).round(2)}," +
                        "${chunk.descriptiveStatistics.getPercentile(75).round(2)}," +
                        "${chunk.descriptiveStatistics.getPercentile(50).round(2)}," +
                        "${chunk.descriptiveStatistics.mean.round(2)}," +
                        "${chunk.descriptiveStatistics.standardDeviation.round(2)}")
            }
        }
    }

}
