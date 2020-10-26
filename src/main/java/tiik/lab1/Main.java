package tiik.lab1;

import tiik.lab1.file_reader.Converter;
import tiik.lab1.file_reader.Printer;
import tiik.lab1.file_reader.Reader;

import java.util.Map;

public class Main {
    public static void main(String... args) {
        String path1 = "/Users/krzysztofczarnecki/Desktop/artykuł/informacje.txt";
        String file = Reader.getContent(path1);
        Map<Character, Integer> stringIntegerMap = Converter.countCharacters(file);

        Printer.print(stringIntegerMap);
    }
}
