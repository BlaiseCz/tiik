package tiik.lab1.file_reader;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class Printer {
    public static void print(Map<Character, Integer> map) {
        Converter.getMapProperlySorted(map)
                .forEach(el -> System.out.println(getStringToPrint(el.getKey(), el.getValue())));
    }

    private static String getStringToPrint(Character character, Integer amount) {
        return String.format("'%s':%d", character, amount);
    }

    public static void print(List<CharacterDetails> list) {
        for(CharacterDetails elem : list) {
            System.out.println(elem.getCharacter() + " n: " + elem.getOccurances() + " p: " + elem.getProbablility() + " e: " + elem.getEntropy());
        }
    }
}
