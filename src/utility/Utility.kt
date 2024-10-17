package utility

import values.Dimensions
import java.awt.Dimension
import java.awt.Toolkit
import java.awt.image.BufferedImage
import java.lang.RuntimeException
import javax.imageio.ImageIO

/**
 * A utility class containing helper methods for the game.
 */
object Utility {
    fun isWindows(): Boolean = System.getProperty("os.name").lowercase().contains("win")

    fun isValueInRange(value: Int, min: Int, max: Int): Boolean {
        return value in min..max
    }

    /**
     * Converts a dimension in pixels to a dimension in screen units, based on the default screen size.
     *
     * @param dim The dimension in pixels to be converted.
     * @return The converted dimension in screen units.
     */

    fun px(dim: Int): Int {
        return px(dim.toDouble()).toInt()
    }


    val screenSize: Dimension
        get() = Toolkit.getDefaultToolkit().screenSize

    fun px(dim: Double): Double {
        return dim
        /*val frame = JBombLauncher.JBombLauncher
        val screenSize = if (frame == null) Toolkit.getDefaultToolkit().screenSize else frame.preferredSize
        return dim * (screenSize.getWidth() / Dimensions.DEFAULT_SCREEN_SIZE.getWidth())*/
    }


    fun loadImage(fileName: String): BufferedImage {
        var fileName = fileName

        // Use ClassLoader to load the image from the JAR file
        fileName = fileName.replace("/src", "")

        val image = Utility::class.java.getResourceAsStream("/$fileName")?.use { inputStream ->
            ImageIO.read(inputStream) ?: throw RuntimeException()
        } ?: throw RuntimeException()

        return image
    }
}