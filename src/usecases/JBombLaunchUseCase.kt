package usecases

import utility.Paths
import java.io.File
import java.io.IOException
import kotlin.system.exitProcess

class JBombLaunchUseCase : UseCase<Unit> {
    override suspend fun invoke() {
        val directory = "${Paths.installDirectory}/${Paths.jarPath}"
        // Create a ProcessBuilder to run the JAR file with both DPI and UI scaling disabled
        val processBuilder = ProcessBuilder(
            "java",
            "-Dsun.java2d.dpiaware=false", // Disables Java DPI awareness
            "-Dsun.java2d.uiScale=1",      // Disables UI scaling by setting it to 1 (no scaling)
            "-jar",
            directory
        )

        try {
            // Start the process
            processBuilder.start()
            exitProcess(0)
        } catch (e: IOException) {
            println("Error launching JAR file: ${e.message}")
        } catch (e: InterruptedException) {
            println("Process was interrupted: ${e.message}")
        }
    }
}
