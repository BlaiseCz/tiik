package tiik.lab1.file_reader;

import java.util.Map;
import java.util.stream.Stream;

public class Lab1Presentator {


    public void present(String filePath) {
        System.out.println("PRESENTING FILE \n" + filePath);
        String file = Reader.getContent(filePath);
        Map<Character, Integer> stringIntegerMap = Converter.countCharacters(file);

        System.out.println("Częstość wystąpień:");
        Printer.print(stringIntegerMap);
        System.out.println("\n\n\n");
    }

    public void present(String...filePaths) {
        Stream.of(filePaths)
              .forEach(this::present);
    }
}
