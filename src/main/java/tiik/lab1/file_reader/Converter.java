package tiik.lab1.file_reader;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Converter {
    public static Map<Character, Integer> countCharacters(String text) {
        Map<Character, Integer> characterCounts = new HashMap<Character, Integer>();
        for (Character character : text.toCharArray()) {
            if (character.equals('\n') || character.equals(' '))
                continue;


            Integer characterCount = characterCounts.get(character);
            if (characterCount == null) {
                characterCount = 0;
            }

            characterCounts.put(character, characterCount + 1);
        }

        return characterCounts;
    }

    public static Stream<Map.Entry<Character, Integer>> getMapProperlySorted(Map<Character, Integer> map) {
        return map
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
    }

    public static int countFileCharacters(String text) {
        return text.replace(" ", "").replace("\n", "").length();
    }

}
