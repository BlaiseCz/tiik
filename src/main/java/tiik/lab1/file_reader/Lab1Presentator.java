package tiik.lab1.file_reader;

import lombok.SneakyThrows;

import java.io.FileWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
        Printer.print(EntropyCalculator.getCharacterDetailsList());

        String fileName = Path.of(filePath)
                       .getFileName()
                       .toString()
                       .split("\\.")[0];
        saveToCSV(stringIntegerMap, fileName);
    }

    public void present(String...filePaths) {
        Stream.of(filePaths)
              .forEach(this::present);
    }

    @SneakyThrows
    private void saveToCSV(Map<Character, Integer> stringIntegerMap, String fileName) {
        List<List<String>> rows = new ArrayList<>();
        rows.add(Arrays.asList("Znak", "Częstość wystąpień"));

        Converter.getMapProperlySorted(stringIntegerMap)
                .forEach(el -> rows.add(Arrays.asList(String.valueOf(el.getKey()), String.valueOf(el.getValue()))));


        String resultFileName = String.format("%s_result.csv", fileName);
        FileWriter csvWriter = new FileWriter(resultFileName);

        for (List<String> rowData : rows) {
            csvWriter.append(String.join(",", rowData));
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();
    }
}
