package tasks

import data.DataInputOutput
import kotlinx.coroutines.runBlocking
import model.ExecutionStatus
import org.apache.commons.io.FileUtils
import usecases.GetLatestVersionUseCase
import usecases.UseCase
import utility.Paths
import utility.Utility.isWindows
import java.io.File
import java.io.IOException
import java.net.URL

class JBombInstaller : UseCase<ExecutionStatus> {
    private val repoUrlJBomb = "https://github.com/simonediberardino/BomberMan/releases/latest/download/JBomb.jar"
    private val filenameJBomb = "JBomb.jar"
    private val installDir: File = Paths.installDirectory
    private lateinit var installDirBin: File

    // Check if the OS is Windows

    // Ensure that the script is run with root privileges
    private fun checkRootPrivileges() {
        if (!isWindows()) {
            if (System.getProperty("user.name") != "root") {
                throw SecurityException("This script must be run as root.")
            }
        }
    }

    // Create the installation directories
    private fun createDirectories() {
        if (!installDir.exists()) {
            installDir.mkdirs()
        }
        if (!installDirBin.exists()) {
            installDirBin.mkdirs()
        }
    }

    // Download a file from a given URL
    private fun downloadFile(url: String, destination: File) {
        try {
            println("Downloading ${destination.name}...")
            FileUtils.copyURLToFile(URL(url), destination)
            println("Download of ${destination.name} completed successfully.")
        } catch (e: IOException) {
            println("Download of ${destination.name} failed: ${e.message}")
            throw e
        }
    }

    override suspend fun invoke(): ExecutionStatus {
        println("======================================================================")
        println("                   JBomb Game Installer")
        println("This prompt will download and install the latest version of JBomb")
        println("and its updater. Ensure you have an active internet connection.")
        println("======================================================================")
        println()

        // Set installation directories based on the operating system
        installDirBin = File(Paths.installDirectory, "bin")

        // Download JBomb jar file
        val jBombFile = File(installDirBin, filenameJBomb)
        return try {
            checkRootPrivileges()
            createDirectories()
            downloadFile(repoUrlJBomb, jBombFile)
            updateLatestVersion()
            ExecutionStatus.DOWNLOADED
        } catch (exception: Exception) {
            println(exception.message)
            ExecutionStatus.DOWNLOADING_ERROR
        }
    }

    private suspend fun updateLatestVersion() {
        DataInputOutput.version = GetLatestVersionUseCase().invoke()
    }
}
