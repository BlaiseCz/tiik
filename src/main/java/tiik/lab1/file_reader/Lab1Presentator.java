package tiik.lab1.file_reader;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Lab1Presentator {
    private CsvSaver csvSaver = new CsvSaver();

    public void present(String filePath) {
        System.out.println("\n\n\nPRESENTING FILE \n" + filePath);
        String file = Reader.getContent(filePath);
        Map<Character, Integer> stringIntegerMap = Converter.countCharacters(file);
        int length = Converter.countFileCharacters(file);

        System.out.println("Entropia:");
        System.out.println(EntropyCalculator.calculate(stringIntegerMap, length));

        System.out.println("Częstość wystąpień:");
        List<CharacterDetails> characterDetailsList = EntropyCalculator.getCharacterDetailsList();
        Printer.print(characterDetailsList);

        String fileName = getFileNameFromPath(filePath);
        csvSaver.saveToCSV(characterDetailsList, fileName);
    }

    public void present(String... filePaths) {
        Stream.of(filePaths)
                .forEach(this::present);
    }

    private String getFileNameFromPath(String filePath) {
        return Path.of(filePath)
            .getFileName()
            .toString()
            .split("\\.")[0];
    }
}
