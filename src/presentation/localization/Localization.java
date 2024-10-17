package presentation.localization;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Localization {
    public static final String LANGUAGE_EN = "en";
    public static final String LANGUAGE_IT = "it";

    // Define constants for keys (variables)
    public static final String WELCOME = "welcome";
    public static final String LAUNCH_GAME = "launch_game";
    public static final String DOWNLOADING_STATUS = "downloading_status";
    public static final String READY_TO_LAUNCH = "ready_to_launch";
    public static final String DOWNLOADED_SUCCESS = "downloaded_success";
    public static final String DOWNLOADING = "downloading";
    public static final String DOWNLOADING_ERROR = "downloading_error";
    public static final String ABOUT = "about";
    public static final String LAUNCHER_TITLE = "launcher_title";

    // Create a map to store translations
    private static final Map<String, Map<String, String>> translations = new HashMap<>();

    static {
        // English translations
        Map<String, String> en = new HashMap<>();
        en.put(WELCOME, "Welcome to JBomb Launcher!");
        en.put(LAUNCH_GAME, "Launch the game");
        en.put(DOWNLOADING_STATUS, "Downloading status");
        en.put(READY_TO_LAUNCH, "JBomb is ready to launch!");
        en.put(DOWNLOADED_SUCCESS, "Update downloaded successfully!");
        en.put(DOWNLOADING, "Downloading the update...");
        en.put(DOWNLOADING_ERROR, "There was an error while downloading the update, retry!");
        en.put(ABOUT, "About");
        en.put(LAUNCHER_TITLE, "JBomb Launcher");

        // Italian translations
        Map<String, String> it = new HashMap<>();
        it.put(WELCOME, "Benvenuto al launcher di JBomb!");
        it.put(LAUNCH_GAME, "Avvia il gioco");
        it.put(DOWNLOADING_STATUS, "Stato del download");
        it.put(READY_TO_LAUNCH, "JBomb è pronto per l'avvio!");
        it.put(DOWNLOADED_SUCCESS, "Aggiornamento scaricato con successo!");
        it.put(DOWNLOADING, "Scaricamento dell'aggiornamento...");
        it.put(DOWNLOADING_ERROR, "Errore durante il download dell'aggiornamento, riprova!");
        it.put(ABOUT, "Informazioni");
        it.put(LAUNCHER_TITLE, "JBomb Launcher");

        // Add translations to the map
        translations.put(LANGUAGE_EN, en);
        translations.put(LANGUAGE_IT, it);
    }

    // Get translation based on the key and language
    public static String getTranslation(String key) {
        return translations.getOrDefault(Locale.getDefault().getLanguage(), translations.get(LANGUAGE_EN)).get(key);
    }
}
