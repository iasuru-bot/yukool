package sdv.devduo.yukool.utils;


/**
 * Classe utilitaire pour aider à gerer les strings
 */
public  class StringUtils {


    /**
     * transforme un string en float
     * @param s string a parser
     * @return retourne un float ou null si echec
     */
    static public Float parseFloat(String s) {
        try { return (s == null || s.isBlank()) ? null : Float.parseFloat(s.replace(",", ".")); } catch (Exception e) { return null; }
    }

    /**
     * fonction qui permet de vérifier si la valeur est vrai ou faux en fonction du string entrée
     * @param s String a vérifier
     * @return vrai ou faux en fonction de l'entrée
     */
    static public Boolean parseBoolean(String s) {
        if (s == null) return null;
        String val = s.trim().toLowerCase();
        return val.equals("1") || val.equals("oui") || val.equals("true") || val.equals("yes");
    }

    /**
     * Permet de nettoyer une string en enlevant les caractères spéciaux
     * @param s String a nettoyer
     * @return Une String nettoyer en minuscule
     */
    static public String cleanString(String s) {
        if (s == null) return null;
        // Suppression des caractères spéciaux
        s = s.replaceAll("[*_]", "");

        return s.toLowerCase().trim();
    }
}
