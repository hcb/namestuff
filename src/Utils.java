public class Utils {
    /**
     * @param {String} value
     * @return {String}
     */
    public static String toSentenceCase(String value) {
        String modified = value.toLowerCase().substring(1, value.length());
        String capital = value.substring(0, 1).toUpperCase();
        // TODO: How about for names like DeSalvo or McCartney or MacElroy? Or McKayla?
        return capital + modified;
    }
}
