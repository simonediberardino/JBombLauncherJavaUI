package utility

import utility.Utility.isWindows
import java.io.File
import java.util.*

object Paths {
    @JvmStatic
    val dataFile: String get() = "data"

    @JvmStatic
    val launcherData: String = let {
        val os = System.getProperty("os.name").lowercase(Locale.getDefault())
        val userHome = System.getProperty("user.home")

        val appname = "JBomb"

        if (os.contains("win")) {
            // Windows: Use AppData\Local
            "${System.getenv("LOCALAPPDATA")}${File.separator}$appname${File.separator}LauncherData"
        } else if (os.contains("mac")) {
            // macOS: Use Library/Application Support
            "$userHome${File.separator}Library${File.separator}Application Support${File.separator}$appname${File.separator}LauncherData"
        } else {
            // Linux/Unix: Use .config
            "$userHome${File.separator}.config${File.separator}$appname${File.separator}LauncherData"
        }
    }

    val installDirectory = if (isWindows()) {
        File(System.getenv("%localappdata%"), "JBomb")
    } else {
        File("~/bin/jbomb")
    }

    val jarPath = "/bin/JBomb.jar"

    val images = "assets/images"
    val backgroundPath = "$images/background.jpg"

    @JvmStatic
    val iconPath: String get() = "$images/frame_icon.png"
}