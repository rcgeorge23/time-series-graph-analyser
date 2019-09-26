package tsga.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class TsgaPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.getTasks().create("tsga", Tsga.class, { tsga ->
            tsga.gatlingLogDirectory = "${project.rootDir}/test_data"
            tsga.outputDirectory = project.buildDir
        })
    }
}