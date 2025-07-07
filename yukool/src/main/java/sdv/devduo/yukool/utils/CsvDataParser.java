package sdv.devduo.yukool.utils;

import lombok.extern.slf4j.Slf4j;
import sdv.devduo.yukool.dto.ProduitRaw;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public final class CsvDataParser {

    public static ProduitRaw parseLine(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 30) return null;
        for (int i = 0; i < parts.length; i++) {
            parts[i] = StringUtils.cleanString(parts[i]);
        }
        return new ProduitRaw(parts[0], parseStringToMarque(parts[1]), parts[2], parts[3], parseStringToIngredients(parts[4]),
                parts[5], parts[6], parts[7], parts[8], parts[9], parts[10], parts[11], parts[12], parts[13], parts[14],
                parts[15], parts[16], parts[17], parts[18], parts[19], parts[20], parts[21], parts[22], parts[23], parts[24],
                parts[25], parts[26], parts[27], parseStringToAllergenes(parts[28]), parseStringToAdditifs(parts[29]));

    }


    public static String parseStringToMarque(String input) {
        if (input == null || input.isBlank()) {
            return "";
        }
        if (input.contains(",")) {
            // Découper en liste
            List<String> parts = List.of(input.split(","));
            // Trier la liste
            List<String> sortedParts = parts.stream()
                    .map(String::trim)
                    .sorted(String.CASE_INSENSITIVE_ORDER)
                    .collect(Collectors.toList());
            // Rejoindre en une chaîne
            return String.join(", ", sortedParts);
        } else {
            return input;
        }
    }


    public static List<String> parseStringToIngredients(String ligne){

        // Suppression des caractères spéciaux
        ligne = ligne.replaceAll("\\([^)]*\\)", "") // Suppression du contenu entre parenthèses
                .replaceAll("\\s*\\d+[\\d.,]*\\s*%", "") // Suppression des pourcentages
                .replaceAll("\\b[^\\d\\W]\\w*\\s*:\\s*", "")// Suppression des préfixes type "Nom: valeur"
                .replaceAll("[,;\\-]", ",")// Remplacement des séparateurs personnalisés
                .replaceAll(",+", ",")
                .replaceAll("^,|,$", "");

        return Arrays.asList(ligne.toLowerCase().trim().split(","));
    }

    public static List<String> parseStringToAllergenes(String ligne){
        // Suppression des caractères spéciaux
        ligne = ligne.replaceAll("\\([^)]*\\)", "") // Suppression du contenu entre parenthèses
                .replaceAll("\\s*\\d+[\\d.,]*\\s*%", "") // Suppression des pourcentages
                .replaceAll("\\b[^\\d\\W]\\w*\\s*:\\s*", "")// Suppression des préfixes type "Nom: valeur"
                .replaceAll("[,;]", ",")// Remplacement des séparateurs personnalisés
                .replaceAll(",+", ",")
                .replaceAll("^,|,$", "");

        return Arrays.asList(ligne.toLowerCase().trim().split(","));

    }

    public static HashMap<String,String> parseStringToAdditifs(String ligne){
        HashMap<String, String> map = new HashMap<>();
        if (ligne == null || ligne.isBlank()) return map;

        List<String> parts = List.of(ligne.split(","));
        for (String part : parts) {
            part = part.trim();
            int index = part.indexOf('-');
            if (index != -1) {
                String code = part.substring(0, index).trim();
                String nom = part.substring(index + 1).trim();
                map.put(code, nom);
            }
        }
        return map;

    }

}
