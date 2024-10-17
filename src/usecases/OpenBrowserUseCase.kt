package usecases

import java.awt.Desktop
import java.io.IOException
import java.net.URI
import java.net.URISyntaxException


class OpenBrowserUseCase(private val url: String) : UseCase<Unit> {
    override suspend fun invoke() {
        if (Desktop.isDesktopSupported()) { // Check if Desktop is supported
            try {
                val desktop = Desktop.getDesktop()
                val uri = URI(url) // Convert the string URL to a URI
                desktop.browse(uri) // Open the URL in the default browser
            } catch (_: Exception) { }
        }
    }
}