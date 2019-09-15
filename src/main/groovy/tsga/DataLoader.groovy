package tsga

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVRecord

import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField

import static tsga.dsl.TimeSeriesProperty.standardDeviation
import static tsga.dsl.TimeUnit.seconds

class DataLoader {

    static def EXPECTED_SHAPE = {
        between seconds, 0, 170, {
            constant 100 with standardDeviation, 100
        }

        between seconds, 180, 300, {
            constant 1500 with standardDeviation, 0.1
        }

        between seconds, 310, 410, {
            constant 100 with standardDeviation, 0.1
        }

        between seconds, 430, 530, {
            constant 1500 with standardDeviation, 0.1
        }

        between seconds, 550, 600, {
            constant 100 with standardDeviation, 0.1
        }
    }

    static void main(String[] args) throws IOException {
        Reader inputStreamReader = new InputStreamReader(DataLoader.class.getResourceAsStream("/umv_response_times.csv"))
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(inputStreamReader)

        TimeSeriesData timeSeriesData = new TimeSeriesData()

        int index = 0
        for (CSVRecord record : records) {
            if (index > 0) {
                timeSeriesData.add(new TimeSeriesDataPoint(
                        timeSinceStartOfScenarioMillis: DateTimeFormatter.ISO_TIME.parse(record.get(0)).getLong(ChronoField.NANO_OF_DAY) / 1000000L,
                        responseTimeMillis: record.get(1) as Long))
            }
            index++
        }

        List<Chunk> chunks = new ChunkBuilder().buildChunks(EXPECTED_SHAPE, timeSeriesData)

        chunks.each { println it }
    }

}
