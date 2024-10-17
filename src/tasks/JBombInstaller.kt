package tasks

import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.net.URL

class JBombInstaller {
    private val repoUrlJBomb = "https://github.com/simonediberardino/BomberMan/releases/latest/download/JBomb.jar"
    private val filenameJBomb = "JBomb.jar"
    private lateinit var installDir: File
    private lateinit var installDirBin: File

    // Check if the OS is Windows
    private fun isWindows(): Boolean = System.getProperty("os.name").lowercase().contains("win")

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

    // Main installation method
    fun install(): Boolean {
        println("======================================================================")
        println("                   JBomb Game Installer")
        println("This prompt will download and install the latest version of JBomb")
        println("and its updater. Ensure you have an active internet connection.")
        println("======================================================================")
        println()

        // Set installation directories based on the operating system
        installDir = if (isWindows()) {
            File(System.getenv("ProgramFiles"), "JBomb")
        } else {
            File("/usr/local/bin/jbomb")
        }

        installDirBin = File(installDir, "bin")

        // Download JBomb jar file
        val jBombFile = File(installDirBin, filenameJBomb)
        return try {
            checkRootPrivileges()
            createDirectories()

            downloadFile(repoUrlJBomb, jBombFile)
            // Create a symbolic link to the JBomb jar file on the desktop
            println("JBomb and its updater have been installed to the latest version.")
            true
        } catch (exception: Exception) {
            false
        }
    }
}
