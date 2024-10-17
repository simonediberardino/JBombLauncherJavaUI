package utility

import utility.Utility.isWindows
import java.io.File
import java.util.*

object Paths {
    @JvmStatic
    val dataFile: String get() = "data"

    @JvmStatic
    val playerDataPath: String = let {
        val os = System.getProperty("os.name").lowercase(Locale.getDefault())
        val userHome = System.getProperty("user.home")

        val appname = "JBomb"

        if (os.contains("win")) {
            // Windows: Use AppData\Local
            "${System.getenv("LOCALAPPDATA")}${File.separator}$appname${File.separator}PlayerData"
        } else if (os.contains("mac")) {
            // macOS: Use Library/Application Support
            "$userHome${File.separator}Library${File.separator}Application Support${File.separator}$appname${File.separator}PlayerData"
        } else {
            // Linux/Unix: Use .config
            "$userHome${File.separator}.config${File.separator}$appname${File.separator}PlayerData"
        }
    }

    val installDirectory = if (isWindows()) {
        File(System.getenv("ProgramFiles"), "JBomb")
    } else {
        File("/usr/local/bin/jbomb")
    }

    val jarPath = "/bin/JBomb.jar"
}