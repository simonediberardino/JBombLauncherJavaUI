package data

import utility.Paths.dataFile
import utility.Paths.playerDataPath
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths

object DataInputOutput {
    var playerDataObject: PropertiesDataObject = storedPlayerData
        private set

    fun retrieveData() {
        playerDataObject = storedPlayerData
    }

    fun updateStoredPlayerData(serObj: PropertiesDataObject? = playerDataObject) {
        try {
            // Get the appropriate data folder path based on the OS
            val dataFolderPath = playerDataPath

            // Ensure the directories exist
            val dataPath = Paths.get(dataFolderPath)
            Files.createDirectories(dataPath)

            // Create the data file within the directory
            val dataFile = File(dataPath.toFile(), dataFile)
            dataFile.createNewFile() // Create the data file if it does not exist
            FileOutputStream(
                dataFile,
                false
            ).use { fileOut -> ObjectOutputStream(fileOut).use { objectOut -> objectOut.writeObject(serObj) } }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private val storedPlayerData: PropertiesDataObject
        get() = try {
            val fileIn = FileInputStream(playerDataPath + File.separator + dataFile)
            val objectIn = ObjectInputStream(fileIn)
            val obj = objectIn.readObject() as PropertiesDataObject
            objectIn.close()
            obj
        } catch (ex: Exception) {
            PropertiesDataObject()
        }

    var version: String = ""
        get() {
            return playerDataObject.version
        }
        set(value) {
            playerDataObject.version = value
            updateStoredPlayerData()
            field = value
        }
}