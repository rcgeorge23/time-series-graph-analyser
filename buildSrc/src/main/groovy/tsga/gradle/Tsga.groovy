package tsga.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import tsga.DataLoader

class Tsga extends DefaultTask {
    String gatlingLogDirectory
    String outputDirectory
    Map<String, Closure> expectedShapes

    Map<String, Closure> getExpectedShapes() {
        return expectedShapes
    }
    void setExpectedShapes(Map<String, Closure> expectedShapes) {
        this.expectedShapes = expectedShapes
    }

    String getOutputDirectory() {
        return outputDirectory
    }

    void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory
    }

    String getGatlingLogDirectory() { return gatlingLogDirectory }

    void setGatlingLogDirectory(String gatlingLogDirectory) { this.gatlingLogDirectory = gatlingLogDirectory }

    @TaskAction
    void generateReport() {
        DataLoader.generateReport(new File(gatlingLogDirectory), new File(outputDirectory), expectedShapes)
    }
}
