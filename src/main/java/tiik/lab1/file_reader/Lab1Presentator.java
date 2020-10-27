package tiik.lab1.file_reader;

import lombok.SneakyThrows;

import java.io.FileWriter;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class Lab1Presentator {


    public void present(String filePath) {
        System.out.println("PRESENTING FILE \n" + filePath);
        String file = Reader.getContent(filePath);
        Map<Character, Integer> stringIntegerMap = Converter.countCharacters(file);
        int length = Converter.countFileCharacters(file);

        System.out.println("Entropia:");
        System.out.println(EntropyCalculator.calculate(stringIntegerMap, length));

        System.out.println("Częstość wystąpień:");
        List<CharacterDetails> characterDetailsList = EntropyCalculator.getCharacterDetailsList();
        Printer.print(characterDetailsList);

        String fileName = Path.of(filePath)
                .getFileName()
                .toString()
                .split("\\.")[0];
        saveToCSV(characterDetailsList, fileName);
    }

    public void present(String... filePaths) {
        Stream.of(filePaths)
                .forEach(this::present);
    }

    @SneakyThrows
    private void saveToCSV(List<CharacterDetails> characterDetailsList, String fileName) {
        List<List<String>> rows = new ArrayList<>();
        rows.add(Arrays.asList("Znak", "Ilość wystąpień", "Prawdopodobieństwo", "Entropia"));

        Comparator<CharacterDetails> compareByCharacter = Comparator.comparing(CharacterDetails::getCharacter);
        characterDetailsList.sort(compareByCharacter);

        String resultFileName = String.format("%s_result.csv", fileName);
        FileWriter csvWriter = new FileWriter(resultFileName);

        for (CharacterDetails elem : characterDetailsList) {
            rows.add(Arrays.asList(String.valueOf(elem.getCharacter()), String.valueOf(elem.getOccurances()), String.valueOf(elem.getProbablility()), String.valueOf(elem.getEntropy())));
        }


        for (List<String> rowData : rows) {
            csvWriter.append(String.join(",", rowData));
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();
    }
}
