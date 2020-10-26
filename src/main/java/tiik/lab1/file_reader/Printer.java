package tiik.lab1.file_reader;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class Printer {
    public static void print(Map<Character, Integer> map) {
        map.forEach((k, v) -> System.out.println(getStringToPrint(k, v)));
    }

    private static String getStringToPrint(Character character, Integer amount) {
        return String.format("'%s':%d", character, amount);
    }
}
