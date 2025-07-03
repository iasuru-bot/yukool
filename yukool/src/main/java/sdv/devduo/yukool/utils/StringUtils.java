package sdv.devduo.yukool.utils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 */
public  class StringUtils {

    /**
     *
     * @param s
     * @param sep
     * @return
     */
    static public List<String> parseList(String s, String sep) {
        if (s == null || s.isBlank()) return List.of();
        return Arrays.stream(s.split(sep))
                .map(String::trim)
                .filter(e -> !e.isBlank())
                .toList();
    }

    /**
     *
     * @param s
     * @return
     */
    static public Float parseFloat(String s) {
        try { return (s == null || s.isBlank()) ? null : Float.parseFloat(s.replace(",", ".")); } catch (Exception e) { return null; }
    }

    /**
     *
     * @param s
     * @return
     */
    static public Boolean parseBoolean(String s) {
        if (s == null) return null;
        String val = s.trim().toLowerCase();
        return val.equals("1") || val.equals("oui") || val.equals("true") || val.equals("yes");
    }

    /**
     *
     * @param s
     * @param charactersSeparateurs
     * @return
     */
    static public String cleanString(String s, List<Character> charactersSeparateurs) {
        if (s == null) return null;

        // Suppression des caractères spéciaux
        s = s.replaceAll("[*_]", "");

        // Suppression du contenu entre parenthèses et des pourcentages
        s = s.replaceAll("\\([^)]*\\)", "").replaceAll("\\s*\\d+[\\d.,]*\\s*%", "");

        // Suppression des préfixes type "Nom: valeur"
        s = s.replaceAll("\\b[^\\d\\W]\\w*\\s*:\\s*", "");

        // Remplacement des séparateurs personnalisés
        if (charactersSeparateurs != null && !charactersSeparateurs.isEmpty()) {
            for (Character sep : charactersSeparateurs) {
                // Échappe les caractères spéciaux regex comme '|'
                String escaped = Pattern.quote(String.valueOf(sep));
                s = s.replaceAll(escaped, ",");
            }
        }

        s = s.replaceAll(",+", ",").replaceAll("^,|,$", "");

        return s.toLowerCase().trim();
    }
}
