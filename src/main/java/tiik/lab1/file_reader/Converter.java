package tiik.lab1.file_reader;

import java.util.HashMap;
import java.util.Map;

public class Converter {
    public static Map<Character, Integer> countCharacters(String text) {
        Map<Character, Integer> characterCounts = new HashMap<Character, Integer>();
        for (Character character : text.toCharArray()) {
            if (character.equals('\n'))
                continue;


            Integer characterCount = characterCounts.get(character);
            if (characterCount == null) {
                characterCount = 0;
            }
            characterCounts.put(character, characterCount + 1);
        }

        return characterCounts;
    }

}
