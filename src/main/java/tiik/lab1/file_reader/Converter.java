package tiik.lab1.file_reader;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Converter {
    public static Map<Character, Integer> countCharacters(String text) {
        Map<Character, Integer> characterCounts = initMap();
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

    private static Map<Character, Integer> initMap() {
        HashMap<Character, Integer> chars = new HashMap<>();
        char[] charactersToAdd = "!'(),-.0123456789:;>?ABCDEFGHIJKLMNOPRSTUWYZ[]abcdefghijklmnopqrstuvwxyz«»éóúąćęłńŚśźŻż—’”„… !'(),-.0123456789:;>?ABCDEFGHIJKLMNOPRSTUWXYZ[]abcdefghijklmnopqrstuvwxyz|«»éóúąćęłńŚśźŻż–—’”„…".toCharArray();

        for (Character character : charactersToAdd) {
            chars.put(character, 0);
        }

        return chars;
    }
}
